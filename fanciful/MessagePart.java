package me.clip.deluxechat.fanciful;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

























final class MessagePart
  implements JsonRepresentedObject, ConfigurationSerializable, Cloneable
{
  ChatColor color = ChatColor.WHITE;
  ArrayList<ChatColor> styles = new ArrayList();
  String clickActionName = null; String clickActionData = null;
  String hoverActionName = null;
  JsonRepresentedObject hoverActionData = null;
  TextualComponent text = null;
  static final BiMap<ChatColor, String> stylesToNames;
  
  MessagePart(TextualComponent paramTextualComponent) { text = paramTextualComponent; }
  
  MessagePart()
  {
    text = null;
  }
  
  boolean hasText() {
    return text != null;
  }
  

  public MessagePart clone()
  {
    MessagePart localMessagePart = (MessagePart)super.clone();
    styles = ((ArrayList)styles.clone());
    if ((hoverActionData instanceof JsonString)) {
      hoverActionData = new JsonString(((JsonString)hoverActionData).getValue());
    } else if ((hoverActionData instanceof FancyMessage)) {
      hoverActionData = ((FancyMessage)hoverActionData).clone();
    }
    return localMessagePart;
  }
  























  public void writeJson(JsonWriter paramJsonWriter)
  {
    try
    {
      paramJsonWriter.beginObject();
      text.writeJson(paramJsonWriter);
      paramJsonWriter.name("color").value(color.name().toLowerCase());
      for (ChatColor localChatColor : styles) {
        paramJsonWriter.name((String)stylesToNames.get(localChatColor)).value(true);
      }
      if ((clickActionName != null) && (clickActionData != null))
      {



        paramJsonWriter.name("clickEvent").beginObject().name("action").value(clickActionName).name("value").value(clickActionData).endObject();
      }
      if ((hoverActionName != null) && (hoverActionData != null))
      {


        paramJsonWriter.name("hoverEvent").beginObject().name("action").value(hoverActionName).name("value");
        hoverActionData.writeJson(paramJsonWriter);
        paramJsonWriter.endObject();
      }
      paramJsonWriter.endObject();
    } catch (IOException localIOException) {
      Bukkit.getLogger().log(Level.WARNING, "A problem occured during writing of JSON string", localIOException);
    }
  }
  
  public Map<String, Object> serialize() {
    HashMap localHashMap = new HashMap();
    localHashMap.put("text", text);
    localHashMap.put("styles", styles);
    localHashMap.put("color", Character.valueOf(color.getChar()));
    localHashMap.put("hoverActionName", hoverActionName);
    localHashMap.put("hoverActionData", hoverActionData);
    localHashMap.put("clickActionName", clickActionName);
    localHashMap.put("clickActionData", clickActionData);
    return localHashMap;
  }
  
  public static MessagePart deserialize(Map<String, Object> paramMap)
  {
    MessagePart localMessagePart = new MessagePart((TextualComponent)paramMap.get("text"));
    styles = ((ArrayList)paramMap.get("styles"));
    color = ChatColor.getByChar(paramMap.get("color").toString());
    hoverActionName = paramMap.get("hoverActionName").toString();
    hoverActionData = ((JsonRepresentedObject)paramMap.get("hoverActionData"));
    clickActionName = paramMap.get("clickActionName").toString();
    clickActionData = paramMap.get("clickActionData").toString();
    return localMessagePart;
  }
  
  static
  {
    ImmutableBiMap.Builder localBuilder = ImmutableBiMap.builder();
    for (ChatColor localChatColor : ChatColor.values())
      if (localChatColor.isFormat())
      {
        String str;
        

        switch (localChatColor) {
        case RED: 
          str = "obfuscated"; break;
        case UNDERLINE: 
          str = "underlined"; break;
        case RESET: case STRIKETHROUGH: default: 
          str = localChatColor.name().toLowerCase();
        }
        
        localBuilder.put(localChatColor, str);
      }
    stylesToNames = localBuilder.build();
    























































    ConfigurationSerialization.registerClass(MessagePart.class);
  }
}
