package me.clip.deluxechat.placeholders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.deluxechat.DeluxeChat;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlaceholderHandler
{
  private static final Pattern RECIPIENT_PLACEHOLDER_PATTERN = Pattern.compile("[%](recipient_)([a-zA-Z0-9_.-@-]+)[%]");
  
  public PlaceholderHandler(DeluxeChat paramDeluxeChat) {}
  
  @Deprecated
  public static void unregisterInternalPlaceholderHooks() {}
  
  @Deprecated
  public static void unregisterInternalRecipientPlaceholderHooks() {}
  
  @Deprecated
  public static Set<String> getExternalPlaceholderPlugins() { return null; }
  
  @Deprecated
  public static Set<String> getExternalRecipientPlaceholderPlugins() {
    return null;
  }
  
  @Deprecated
  public static boolean registerPlaceholderHook(Plugin paramPlugin, DeluxePlaceholderHook paramDeluxePlaceholderHook) { return false; }
  
  @Deprecated
  public static boolean registerPlaceholderHook(String paramString, DeluxePlaceholderHook paramDeluxePlaceholderHook) {
    return false;
  }
  
  @Deprecated
  public static boolean registerPlaceholderHook(Plugin paramPlugin, DeluxePlaceholderHook paramDeluxePlaceholderHook, boolean paramBoolean) { return false; }
  
  @Deprecated
  public static boolean registerPlaceholderHook(String paramString, DeluxePlaceholderHook paramDeluxePlaceholderHook, boolean paramBoolean) {
    return false;
  }
  
  @Deprecated
  public static boolean registerRecipientPlaceholderHook(Plugin paramPlugin, DeluxeRecipientPlaceholderHook paramDeluxeRecipientPlaceholderHook) { return false; }
  
  @Deprecated
  public static boolean registerRecipientPlaceholderHook(String paramString, DeluxeRecipientPlaceholderHook paramDeluxeRecipientPlaceholderHook) {
    return false;
  }
  
  @Deprecated
  public static boolean registerRecipientPlaceholderHook(Plugin paramPlugin, DeluxeRecipientPlaceholderHook paramDeluxeRecipientPlaceholderHook, boolean paramBoolean) { return false; }
  
  @Deprecated
  public static boolean registerRecipientPlaceholderHook(String paramString, DeluxeRecipientPlaceholderHook paramDeluxeRecipientPlaceholderHook, boolean paramBoolean) {
    return false;
  }
  
  @Deprecated
  public static boolean unregisterPlaceholderHook(Plugin paramPlugin) { return false; }
  
  @Deprecated
  public static boolean unregisterPlaceholderHook(String paramString) {
    return false;
  }
  
  @Deprecated
  public static boolean unregisterRecipientPlaceholderHook(Plugin paramPlugin) { return false; }
  
  @Deprecated
  public static boolean unregisterRecipientPlaceholderHook(String paramString) {
    return false;
  }
  
  @Deprecated
  public static Set<String> getRegisteredPlaceholderPlugins() { return null; }
  

  @Deprecated
  public static Set<String> getRegisteredRecipientPlaceholderPlugins() { return null; }
  
  @Deprecated
  public static void unregisterAllPlaceholderHooks() {}
  
  @Deprecated
  public static Map<String, DeluxePlaceholderHook> getPlaceholders() { return null; }
  
  @Deprecated
  public static Map<String, DeluxeRecipientPlaceholderHook> getRecipientPlaceholders() {
    return null;
  }
  
  public static List<String> setPlaceholders(Player paramPlayer, List<String> paramList) { return PlaceholderAPI.setPlaceholders(paramPlayer, paramList); }
  
  public static String setPlaceholders(Player paramPlayer, String paramString)
  {
    return PlaceholderAPI.setPlaceholders(paramPlayer, paramString);
  }
  
  public static List<String> setRecipientPlaceholders(Player paramPlayer, List<String> paramList) {
    if (paramList == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    for (String str : paramList) {
      localArrayList.add(ChatColor.translateAlternateColorCodes('&', setRecipPlaceholders(paramPlayer, str)));
    }
    return localArrayList;
  }
  
  public static String setRecipPlaceholders(Player paramPlayer, String paramString)
  {
    if ((paramString == null) || (paramPlayer == null)) {
      return paramString;
    }
    
    Matcher localMatcher = RECIPIENT_PLACEHOLDER_PATTERN.matcher(paramString);
    Iterator localIterator;
    for (; localMatcher.find(); 
        
































        localIterator.hasNext())
    {
      String str1 = localMatcher.group(2);
      
      StringBuilder localStringBuilder1 = new StringBuilder();
      
      char[] arrayOfChar = str1.toCharArray();
      


      for (int i = 0; i < arrayOfChar.length; i++)
      {
        if (arrayOfChar[i] == '_') {
          break;
        }
        
        localStringBuilder1.append(arrayOfChar[i]);
      }
      

      String str2 = localStringBuilder1.toString();
      
      StringBuilder localStringBuilder2 = new StringBuilder();
      
      for (int j = i + 1; j < arrayOfChar.length; j++) {
        localStringBuilder2.append(arrayOfChar[j]);
      }
      
      String str3 = localStringBuilder2.toString();
      
      if (str3.isEmpty()) {
        str3 = str2;
      }
      
      localIterator = PlaceholderAPI.getRegisteredPlaceholderPlugins().iterator(); continue;String str4 = (String)localIterator.next();
      
      if (str2.equalsIgnoreCase(str4))
      {
        String str5 = ((PlaceholderHook)PlaceholderAPI.getPlaceholders().get(str4)).onPlaceholderRequest(paramPlayer, str3);
        
        if (str5 != null) {
          paramString = paramString.replaceAll("%recipient_" + str1 + "%", Matcher.quoteReplacement(str5));
        }
      }
    }
    

    return paramString;
  }
}
