package me.clip.deluxechat.fanciful;

import com.google.gson.stream.JsonWriter;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;




























@Immutable
final class JsonString
  implements JsonRepresentedObject, ConfigurationSerializable
{
  private String _value;
  
  public JsonString(String paramString)
  {
    _value = paramString;
  }
  
  public void writeJson(JsonWriter paramJsonWriter) {
    paramJsonWriter.value(getValue());
  }
  
  public String getValue() {
    return _value;
  }
  
  public Map<String, Object> serialize() {
    HashMap localHashMap = new HashMap();
    localHashMap.put("stringValue", _value);
    return localHashMap;
  }
  
  public static JsonString deserialize(Map<String, Object> paramMap) {
    return new JsonString(paramMap.get("stringValue").toString());
  }
  
  public String toString()
  {
    return _value;
  }
}
