package me.clip.deluxechat.fanciful;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.stream.JsonWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

























public abstract class TextualComponent
  implements Cloneable
{
  static
  {
    ConfigurationSerialization.registerClass(ArbitraryTextTypeComponent.class);
    ConfigurationSerialization.registerClass(ComplexTextTypeComponent.class);
  }
  
  public TextualComponent() {}
  
  public String toString() { return getReadableString(); }
  




  public abstract String getKey();
  




  public abstract String getReadableString();
  



  public abstract TextualComponent clone();
  



  public abstract void writeJson(JsonWriter paramJsonWriter);
  



  static TextualComponent deserialize(Map<String, Object> paramMap)
  {
    if ((paramMap.containsKey("key")) && (paramMap.size() == 2) && (paramMap.containsKey("value")))
    {
      return ArbitraryTextTypeComponent.deserialize(paramMap); }
    if ((paramMap.size() >= 2) && (paramMap.containsKey("key")) && (!paramMap.containsKey("value")))
    {
      return ComplexTextTypeComponent.deserialize(paramMap);
    }
    
    return null;
  }
  
  static boolean isTextKey(String paramString) {
    return (paramString.equals("translate")) || (paramString.equals("text")) || (paramString.equals("score")) || (paramString.equals("selector"));
  }
  
  private static final class ArbitraryTextTypeComponent extends TextualComponent implements ConfigurationSerializable
  {
    private String _key;
    private String _value;
    
    public ArbitraryTextTypeComponent(String paramString1, String paramString2)
    {
      setKey(paramString1);
      setValue(paramString2);
    }
    
    public String getKey()
    {
      return _key;
    }
    
    public void setKey(String paramString) {
      Preconditions.checkArgument((paramString != null) && (!paramString.isEmpty()), "The key must be specified.");
      _key = paramString;
    }
    
    public String getValue() {
      return _value;
    }
    
    public void setValue(String paramString) {
      Preconditions.checkArgument(paramString != null, "The value must be specified.");
      _value = paramString;
    }
    




    public TextualComponent clone()
    {
      return new ArbitraryTextTypeComponent(getKey(), getValue());
    }
    
    public void writeJson(JsonWriter paramJsonWriter)
    {
      paramJsonWriter.name(getKey()).value(getValue());
    }
    
    public Map<String, Object> serialize()
    {
      new HashMap() {};
    }
    


    public static ArbitraryTextTypeComponent deserialize(Map<String, Object> paramMap)
    {
      return new ArbitraryTextTypeComponent(paramMap.get("key").toString(), paramMap.get("value").toString());
    }
    
    public String getReadableString()
    {
      return getValue();
    }
  }
  
  private static final class ComplexTextTypeComponent extends TextualComponent implements ConfigurationSerializable
  {
    private String _key;
    private Map<String, String> _value;
    
    public ComplexTextTypeComponent(String paramString, Map<String, String> paramMap)
    {
      setKey(paramString);
      setValue(paramMap);
    }
    
    public String getKey()
    {
      return _key;
    }
    
    public void setKey(String paramString) {
      Preconditions.checkArgument((paramString != null) && (!paramString.isEmpty()), "The key must be specified.");
      _key = paramString;
    }
    
    public Map<String, String> getValue() {
      return _value;
    }
    
    public void setValue(Map<String, String> paramMap) {
      Preconditions.checkArgument(paramMap != null, "The value must be specified.");
      _value = paramMap;
    }
    




    public TextualComponent clone()
    {
      return new ComplexTextTypeComponent(getKey(), getValue());
    }
    
    public void writeJson(JsonWriter paramJsonWriter)
    {
      paramJsonWriter.name(getKey());
      paramJsonWriter.beginObject();
      for (Map.Entry localEntry : _value.entrySet()) {
        paramJsonWriter.name((String)localEntry.getKey()).value((String)localEntry.getValue());
      }
      paramJsonWriter.endObject();
    }
    
    public Map<String, Object> serialize()
    {
      new HashMap() {};
    }
    




    public static ComplexTextTypeComponent deserialize(Map<String, Object> paramMap)
    {
      String str = null;
      HashMap localHashMap = new HashMap();
      for (Map.Entry localEntry : paramMap.entrySet()) {
        if (((String)localEntry.getKey()).equals("key")) {
          str = (String)localEntry.getValue();
        } else if (((String)localEntry.getKey()).startsWith("value.")) {
          localHashMap.put(((String)localEntry.getKey()).substring(6), localEntry.getValue().toString());
        }
      }
      return new ComplexTextTypeComponent(str, localHashMap);
    }
    
    public String getReadableString()
    {
      return getKey();
    }
  }
  





  public static TextualComponent rawText(String paramString)
  {
    return new ArbitraryTextTypeComponent("text", paramString);
  }
  









  public static TextualComponent localizedText(String paramString)
  {
    return new ArbitraryTextTypeComponent("translate", paramString);
  }
  
  private static void throwUnsupportedSnapshot() {
    throw new UnsupportedOperationException("This feature is only supported in snapshot releases.");
  }
  








  public static TextualComponent objectiveScore(String paramString)
  {
    return objectiveScore("*", paramString);
  }
  










  public static TextualComponent objectiveScore(String paramString1, String paramString2)
  {
    throwUnsupportedSnapshot();
    
    return new ComplexTextTypeComponent("score", ImmutableMap.builder()
      .put("name", paramString1)
      .put("objective", paramString2)
      .build());
  }
  








  public static TextualComponent selector(String paramString)
  {
    throwUnsupportedSnapshot();
    
    return new ArbitraryTextTypeComponent("selector", paramString);
  }
}
