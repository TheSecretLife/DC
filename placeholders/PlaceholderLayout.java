package me.clip.deluxechat.placeholders;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderLayout
{
  private static Map<String, PlaceholderLayout> layouts = new HashMap();
  
  private String identifier;
  
  private String hasValue;
  private String noValue;
  
  public PlaceholderLayout(String paramString1, String paramString2, String paramString3)
  {
    identifier = paramString1;
    hasValue = paramString2;
    noValue = paramString3;
  }
  
  public String getIdentifier() {
    return identifier;
  }
  
  public String getHasValue() {
    return hasValue;
  }
  
  public String getNoValue() {
    return noValue;
  }
  
  public void load() {
    if (layouts == null) {
      layouts = new HashMap();
    }
    
    layouts.put(identifier, this);
  }
  
  public static void load(PlaceholderLayout paramPlaceholderLayout) {
    if (layouts == null) {
      layouts = new HashMap();
    }
    
    layouts.put(paramPlaceholderLayout.getIdentifier(), paramPlaceholderLayout);
  }
  
  public boolean unload() {
    if ((layouts == null) || (layouts.isEmpty())) {
      return false;
    }
    return layouts.remove(identifier) != null;
  }
  
  public static boolean unload(PlaceholderLayout paramPlaceholderLayout) {
    if ((layouts == null) || (layouts.isEmpty())) {
      return false;
    }
    return layouts.remove(paramPlaceholderLayout.getIdentifier()) != null;
  }
  
  public static void unloadAll() {
    layouts = null;
  }
  
  public static PlaceholderLayout getLayout(String paramString) {
    if ((layouts == null) || (layouts.isEmpty())) {
      return null;
    }
    if ((layouts.containsKey(paramString)) && (layouts.get(paramString) != null)) {
      return (PlaceholderLayout)layouts.get(paramString);
    }
    return null;
  }
}
