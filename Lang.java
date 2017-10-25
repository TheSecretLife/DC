package me.clip.deluxechat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Lang
{
  NO_PERMISSION("no_permission", "&cYou don't have permission to do that!"), 
  MSG_INCORRECT_USAGE("msg_incorrect_usage", "&cIncorrect usage! &7/msg <player> <message>"), 
  MSG_RECIPIENT_NOT_ONLINE("msg_recipient_not_online", "&c{0} &cis not online!"), 
  MSG_RECIPIENT_IGNORING_SENDER("msg_recipient_ignoring_sender", "&c{0} &cdoes not wish to speak to you!"), 
  MSG_RECIPIENT_IS_SENDER("msg_recipient_is_sender", "&cYou can't message yourself!"), 
  REPLY_INCORRECT_USAGE("reply_incorrect_usage", "&cIncorrect usage! &7/r <message>"), 
  REPLY_NO_RECIPIENT("reply_no_recipient", "&cYou don't have a recipient to reply to!"), 
  SOCIALSPY_TOGGLE_ON("socialspy_toggle_on", "&aSocialspy toggled on!"), 
  SOCIALSPY_TOGGLE_OFF("socialspy_toggle_off", "&7Socialspy toggled off!"), 
  BUNGEE_GLOBAL_TOGGLE_ON("bungee_global_toggle_on", "&aChat set to global"), 
  BUNGEE_GLOBAL_TOGGLE_OFF("bungee_global_toggle_off", "&aChat set to local"), 
  URL_INCORRECT_USAGE("url_incorrect_usage", "Hover for url command usage info!"), 
  URL_INCORRECT_USAGE_TOOLTIP_1("url_incorrect_usage_tooltip_1", "&7/url <link>"), 
  URL_INCORRECT_USAGE_TOOLTIP_2("url_incorrect_usage_tooltip_2", "&7/url <link> <message>"), 
  CHAT_ILLEGAL_CHARACTERS("chat_illegal_characters", "You can't use special characters in chat!");
  

  private String path;
  private String def;
  private static FileConfiguration LANG;
  
  private Lang(String paramString1, String paramString2)
  {
    path = paramString1;
    def = paramString2;
  }
  
  public static void setFile(FileConfiguration paramFileConfiguration) {
    LANG = paramFileConfiguration;
  }
  
  public String getDefault() {
    return def;
  }
  
  public String getPath() {
    return path;
  }
  
  public String getConfigValue(String[] paramArrayOfString) {
    String str = ChatColor.translateAlternateColorCodes('&', 
      LANG.getString(path, def));
    
    if (paramArrayOfString == null) {
      return str;
    }
    if (paramArrayOfString.length == 0) {
      return str;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      str = str.replace("{" + i + "}", paramArrayOfString[i]);
    }
    

    return str;
  }
}
