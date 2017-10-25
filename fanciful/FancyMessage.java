package me.clip.deluxechat.fanciful;

import com.google.common.collect.BiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.Statistic.Type;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;























public class FancyMessage
  implements JsonRepresentedObject, Cloneable, Iterable<MessagePart>, ConfigurationSerializable
{
  private List<MessagePart> messageParts;
  private String jsonString;
  private boolean dirty;
  private String lastColor;
  private String chatColor;
  
  static
  {
    ConfigurationSerialization.registerClass(FancyMessage.class);
  }
  






  public FancyMessage clone()
  {
    FancyMessage localFancyMessage = (FancyMessage)super.clone();
    messageParts = new ArrayList(messageParts.size());
    for (int i = 0; i < messageParts.size(); i++) {
      messageParts.add(i, ((MessagePart)messageParts.get(i)).clone());
    }
    dirty = false;
    jsonString = null;
    return localFancyMessage;
  }
  



  public FancyMessage(String paramString)
  {
    this(TextualComponent.rawText(paramString));
  }
  
  public FancyMessage(TextualComponent paramTextualComponent) {
    messageParts = new ArrayList();
    messageParts.add(new MessagePart(paramTextualComponent));
    jsonString = null;
    dirty = false;
    lastColor = "";
  }
  
  public String getLastColor() {
    if (lastColor == null) {
      return "";
    }
    return lastColor;
  }
  
  public void setLastColor(String paramString) {
    if (paramString != null) {
      lastColor = paramString;
    }
  }
  


  public FancyMessage()
  {
    this(null);
  }
  





  public FancyMessage text(String paramString)
  {
    MessagePart localMessagePart = latest();
    if (localMessagePart.hasText()) {
      throw new IllegalStateException("text for this message part is already set");
    }
    text = TextualComponent.rawText(paramString);
    dirty = true;
    return this;
  }
  
  public FancyMessage text(TextualComponent paramTextualComponent) {
    MessagePart localMessagePart = latest();
    if (localMessagePart.hasText()) {
      throw new IllegalStateException("text for this message part is already set");
    }
    text = paramTextualComponent;
    dirty = true;
    return this;
  }
  





  public FancyMessage color(ChatColor paramChatColor)
  {
    if (!paramChatColor.isColor()) {
      throw new IllegalArgumentException(paramChatColor.name() + " is not a color");
    }
    latestcolor = paramChatColor;
    dirty = true;
    return this;
  }
  





  public FancyMessage style(ChatColor... paramVarArgs)
  {
    for (ChatColor localChatColor : paramVarArgs) {
      if (!localChatColor.isFormat()) {
        throw new IllegalArgumentException(localChatColor.name() + " is not a style");
      }
    }
    lateststyles.addAll(Arrays.asList(paramVarArgs));
    dirty = true;
    return this;
  }
  




  public FancyMessage file(String paramString)
  {
    onClick("open_file", paramString);
    return this;
  }
  




  public FancyMessage link(String paramString)
  {
    onClick("open_url", paramString);
    return this;
  }
  





  public FancyMessage suggest(String paramString)
  {
    onClick("suggest_command", paramString);
    return this;
  }
  





  public FancyMessage command(String paramString)
  {
    onClick("run_command", paramString);
    return this;
  }
  





  public FancyMessage achievementTooltip(String paramString)
  {
    onHover("show_achievement", new JsonString("achievement." + paramString));
    return this;
  }
  




  public FancyMessage achievementTooltip(Achievement paramAchievement)
  {
    try
    {
      Object localObject = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSAchievement", new Class[] { Achievement.class }).invoke(null, new Object[] { paramAchievement });
      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Achievement"), "name").get(localObject));
    } catch (IllegalAccessException localIllegalAccessException) {
      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", localIllegalAccessException);
      return this;
    } catch (IllegalArgumentException localIllegalArgumentException) {
      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", localIllegalArgumentException);
      return this;
    } catch (InvocationTargetException localInvocationTargetException) {
      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", localInvocationTargetException); }
    return this;
  }
  







  public FancyMessage statisticTooltip(Statistic paramStatistic)
  {
    Statistic.Type localType = paramStatistic.getType();
    if (localType != Statistic.Type.UNTYPED) {
      throw new IllegalArgumentException("That statistic requires an additional " + localType + " parameter!");
    }
    try {
      Object localObject = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSStatistic", new Class[] { Statistic.class }).invoke(null, new Object[] { paramStatistic });
      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(localObject));
    } catch (IllegalAccessException localIllegalAccessException) {
      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", localIllegalAccessException);
      return this;
    } catch (IllegalArgumentException localIllegalArgumentException) {
      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", localIllegalArgumentException);
      return this;
    } catch (InvocationTargetException localInvocationTargetException) {
      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", localInvocationTargetException); }
    return this;
  }
  








  public FancyMessage statisticTooltip(Statistic paramStatistic, Material paramMaterial)
  {
    Statistic.Type localType = paramStatistic.getType();
    if (localType == Statistic.Type.UNTYPED) {
      throw new IllegalArgumentException("That statistic needs no additional parameter!");
    }
    if (((localType == Statistic.Type.BLOCK) && (paramMaterial.isBlock())) || (localType == Statistic.Type.ENTITY)) {
      throw new IllegalArgumentException("Wrong parameter type for that statistic - needs " + localType + "!");
    }
    try {
      Object localObject = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getMaterialStatistic", new Class[] { Statistic.class, Material.class }).invoke(null, new Object[] { paramStatistic, paramMaterial });
      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(localObject));
    } catch (IllegalAccessException localIllegalAccessException) {
      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", localIllegalAccessException);
      return this;
    } catch (IllegalArgumentException localIllegalArgumentException) {
      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", localIllegalArgumentException);
      return this;
    } catch (InvocationTargetException localInvocationTargetException) {
      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", localInvocationTargetException); }
    return this;
  }
  








  public FancyMessage statisticTooltip(Statistic paramStatistic, EntityType paramEntityType)
  {
    Statistic.Type localType = paramStatistic.getType();
    if (localType == Statistic.Type.UNTYPED) {
      throw new IllegalArgumentException("That statistic needs no additional parameter!");
    }
    if (localType != Statistic.Type.ENTITY) {
      throw new IllegalArgumentException("Wrong parameter type for that statistic - needs " + localType + "!");
    }
    try {
      Object localObject = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getEntityStatistic", new Class[] { Statistic.class, EntityType.class }).invoke(null, new Object[] { paramStatistic, paramEntityType });
      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(localObject));
    } catch (IllegalAccessException localIllegalAccessException) {
      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", localIllegalAccessException);
      return this;
    } catch (IllegalArgumentException localIllegalArgumentException) {
      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", localIllegalArgumentException);
      return this;
    } catch (InvocationTargetException localInvocationTargetException) {
      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", localInvocationTargetException); }
    return this;
  }
  






  public FancyMessage itemTooltip(String paramString)
  {
    onHover("show_item", new JsonString(paramString));
    return this;
  }
  




  public FancyMessage itemTooltip(ItemStack paramItemStack)
  {
    try
    {
      Object localObject = Reflection.getMethod(Reflection.getOBCClass("inventory.CraftItemStack"), "asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { paramItemStack });
      return itemTooltip(Reflection.getMethod(Reflection.getNMSClass("ItemStack"), "save", new Class[] { Reflection.getNMSClass("NBTTagCompound") }).invoke(localObject, new Object[] { Reflection.getNMSClass("NBTTagCompound").newInstance() }).toString());
    } catch (Exception localException) {
      localException.printStackTrace(); }
    return this;
  }
  






  public FancyMessage tooltip(String paramString)
  {
    onHover("show_text", new JsonString(paramString));
    return this;
  }
  





  public FancyMessage tooltip(Iterable<String> paramIterable)
  {
    tooltip((String[])ArrayWrapper.toArray(paramIterable, String.class));
    return this;
  }
  





  public FancyMessage tooltip(String... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramVarArgs.length; i++) {
      localStringBuilder.append(paramVarArgs[i]);
      if (i != paramVarArgs.length - 1) {
        localStringBuilder.append('\n');
      }
    }
    tooltip(localStringBuilder.toString());
    return this;
  }
  





  public FancyMessage formattedTooltip(FancyMessage paramFancyMessage)
  {
    for (MessagePart localMessagePart : messageParts) {
      if ((clickActionData != null) && (clickActionName != null))
        throw new IllegalArgumentException("The tooltip text cannot have click data.");
      if ((hoverActionData != null) && (hoverActionName != null)) {
        throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
      }
    }
    onHover("show_text", paramFancyMessage);
    return this;
  }
  





  public FancyMessage formattedTooltip(FancyMessage... paramVarArgs)
  {
    if (paramVarArgs.length < 1) {
      onHover(null, null);
      return this;
    }
    
    FancyMessage localFancyMessage = new FancyMessage();
    messageParts.clear();
    
    for (int i = 0; i < paramVarArgs.length; i++) {
      try {
        for (MessagePart localMessagePart : paramVarArgs[i]) {
          if ((clickActionData != null) && (clickActionName != null))
            throw new IllegalArgumentException("The tooltip text cannot have click data.");
          if ((hoverActionData != null) && (hoverActionName != null)) {
            throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
          }
          if (localMessagePart.hasText()) {
            messageParts.add(localMessagePart.clone());
          }
        }
        if (i != paramVarArgs.length - 1) {
          messageParts.add(new MessagePart(TextualComponent.rawText("\n")));
        }
      } catch (CloneNotSupportedException localCloneNotSupportedException) {
        Bukkit.getLogger().log(Level.WARNING, "Failed to clone object", localCloneNotSupportedException);
        return this;
      }
    }
    return formattedTooltip(messageParts.isEmpty() ? null : localFancyMessage);
  }
  





  public FancyMessage formattedTooltip(Iterable<FancyMessage> paramIterable)
  {
    return formattedTooltip((FancyMessage[])ArrayWrapper.toArray(paramIterable, FancyMessage.class));
  }
  





  public FancyMessage then(String paramString)
  {
    return then(TextualComponent.rawText(paramString));
  }
  





  public FancyMessage then(TextualComponent paramTextualComponent)
  {
    if (!latest().hasText()) {
      throw new IllegalStateException("previous message part has no text");
    }
    messageParts.add(new MessagePart(paramTextualComponent));
    dirty = true;
    return this;
  }
  




  public FancyMessage then()
  {
    if (!latest().hasText()) {
      throw new IllegalStateException("previous message part has no text");
    }
    messageParts.add(new MessagePart());
    dirty = true;
    return this;
  }
  
  public void writeJson(JsonWriter paramJsonWriter)
  {
    if (messageParts.size() == 1) {
      latest().writeJson(paramJsonWriter);
    } else {
      paramJsonWriter.beginObject().name("text").value("").name("extra").beginArray();
      for (MessagePart localMessagePart : this) {
        localMessagePart.writeJson(paramJsonWriter);
      }
      paramJsonWriter.endArray().endObject();
    }
  }
  




  public String toJSONString()
  {
    if ((!dirty) && (jsonString != null)) {
      return jsonString;
    }
    StringWriter localStringWriter = new StringWriter();
    JsonWriter localJsonWriter = new JsonWriter(localStringWriter);
    try {
      writeJson(localJsonWriter);
      localJsonWriter.close();
    } catch (IOException localIOException) {
      throw new RuntimeException("invalid message");
    }
    jsonString = localStringWriter.toString();
    dirty = false;
    return jsonString;
  }
  
















  public String toOldMessageFormat()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (MessagePart localMessagePart : this) {
      localStringBuilder.append(color == null ? "" : color);
      for (ChatColor localChatColor : styles) {
        localStringBuilder.append(localChatColor);
      }
      localStringBuilder.append(text);
    }
    return localStringBuilder.toString();
  }
  
  private MessagePart latest() {
    return (MessagePart)messageParts.get(messageParts.size() - 1);
  }
  
  private void onClick(String paramString1, String paramString2) {
    MessagePart localMessagePart = latest();
    clickActionName = paramString1;
    clickActionData = paramString2;
    dirty = true;
  }
  
  private void onHover(String paramString, JsonRepresentedObject paramJsonRepresentedObject) {
    MessagePart localMessagePart = latest();
    hoverActionName = paramString;
    hoverActionData = paramJsonRepresentedObject;
    dirty = true;
  }
  
  public Map<String, Object> serialize()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("messageParts", messageParts);
    
    return localHashMap;
  }
  






  public static FancyMessage deserialize(Map<String, Object> paramMap)
  {
    FancyMessage localFancyMessage = new FancyMessage();
    messageParts = ((List)paramMap.get("messageParts"));
    jsonString = (paramMap.containsKey("JSON") ? paramMap.get("JSON").toString() : null);
    dirty = (!paramMap.containsKey("JSON"));
    return localFancyMessage;
  }
  



  public Iterator<MessagePart> iterator()
  {
    return messageParts.iterator();
  }
  
  private static JsonParser _stringParser = new JsonParser();
  





  public static FancyMessage deserialize(String paramString)
  {
    JsonObject localJsonObject1 = _stringParser.parse(paramString).getAsJsonObject();
    JsonArray localJsonArray = localJsonObject1.getAsJsonArray("extra");
    FancyMessage localFancyMessage = new FancyMessage();
    messageParts.clear();
    for (JsonElement localJsonElement : localJsonArray) {
      MessagePart localMessagePart = new MessagePart();
      JsonObject localJsonObject2 = localJsonElement.getAsJsonObject();
      for (Map.Entry localEntry1 : localJsonObject2.entrySet()) {
        Object localObject;
        if (TextualComponent.isTextKey((String)localEntry1.getKey()))
        {
          localObject = new HashMap();
          ((Map)localObject).put("key", localEntry1.getKey());
          if (((JsonElement)localEntry1.getValue()).isJsonPrimitive())
          {
            ((Map)localObject).put("value", ((JsonElement)localEntry1.getValue()).getAsString());
          }
          else {
            for (Map.Entry localEntry2 : ((JsonElement)localEntry1.getValue()).getAsJsonObject().entrySet()) {
              ((Map)localObject).put("value." + (String)localEntry2.getKey(), ((JsonElement)localEntry2.getValue()).getAsString());
            }
          }
          text = TextualComponent.deserialize((Map)localObject);
        } else if (MessagePart.stylesToNames.inverse().containsKey(localEntry1.getKey())) {
          if (((JsonElement)localEntry1.getValue()).getAsBoolean()) {
            styles.add((ChatColor)MessagePart.stylesToNames.inverse().get(localEntry1.getKey()));
          }
        } else if (((String)localEntry1.getKey()).equals("color")) {
          color = ChatColor.valueOf(((JsonElement)localEntry1.getValue()).getAsString().toUpperCase());
        } else if (((String)localEntry1.getKey()).equals("clickEvent")) {
          localObject = ((JsonElement)localEntry1.getValue()).getAsJsonObject();
          clickActionName = ((JsonObject)localObject).get("action").getAsString();
          clickActionData = ((JsonObject)localObject).get("value").getAsString();
        } else if (((String)localEntry1.getKey()).equals("hoverEvent")) {
          localObject = ((JsonElement)localEntry1.getValue()).getAsJsonObject();
          hoverActionName = ((JsonObject)localObject).get("action").getAsString();
          if (((JsonObject)localObject).get("value").isJsonPrimitive())
          {
            hoverActionData = new JsonString(((JsonObject)localObject).get("value").getAsString());

          }
          else
          {
            hoverActionData = deserialize(((JsonObject)localObject).get("value").toString());
          }
        }
      }
      messageParts.add(localMessagePart);
    }
    return localFancyMessage;
  }
  
  public String getChatColor() {
    if (chatColor == null) {
      return "";
    }
    return chatColor;
  }
  
  public void setChatColor(String paramString) {
    chatColor = paramString;
  }
}
