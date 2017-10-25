package me.clip.deluxechat;

import java.util.Iterator;
import java.util.Map;
import me.clip.deluxechat.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeluxeUtil
{
  public DeluxeUtil() {}
  
  public static void sms(CommandSender paramCommandSender, String paramString)
  {
    paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', paramString));
  }
  
  public static void sms(Player paramPlayer, String paramString) {
    paramPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', paramString));
  }
  
  public static boolean containsInvalidChars(String paramString) {
    for (int i : paramString.toCharArray()) {
      int m = i;
      if (((m > 128) && (m < 167)) || (m > 167)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isFormat(ChatColor paramChatColor)
  {
    return paramChatColor.isFormat();
  }
  
  public static String convertToJson(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    
    if (!paramString.contains("§")) {
      return new FancyMessage(paramString).toJSONString();
    }
    
    FancyMessage localFancyMessage = null;
    
    int i = 1;
    
    String[] arrayOfString1 = paramString.split("§");
    
    Object localObject1 = null;
    
    Object localObject2 = null;
    
    for (String str1 : arrayOfString1)
    {
      if (!str1.isEmpty())
      {


        ChatColor localChatColor = ChatColor.getByChar(str1.charAt(0));
        
        String str2 = str1.substring(1);
        
        if ((str2.isEmpty()) && 
          (localChatColor != null) && (!localChatColor.equals(ChatColor.RESET))) {
          if (isFormat(localChatColor)) {
            localObject2 = localChatColor;
          } else {
            localObject1 = localChatColor;

          }
          

        }
        else if (localChatColor == null)
        {
          if (i != 0) {
            i = 0;
            localFancyMessage = new FancyMessage(str2);
          }
          else {
            localFancyMessage.then(str2);
          }
          
          if (localObject1 != null) {
            localFancyMessage.color(localObject1);
            localObject1 = null;
          }
          
          if (localObject2 != null) {
            localFancyMessage.style(new ChatColor[] { localObject2 });
            localObject2 = null;

          }
          

        }
        else if (i != 0)
        {
          i = 0;
          
          localFancyMessage = new FancyMessage(str2);
          
          if (localObject1 != null) {
            localFancyMessage.color(localObject1);
            localObject1 = null;
          }
          
          if (localObject2 != null) {
            localFancyMessage.style(new ChatColor[] { localObject2 });
            localObject2 = null;
          }
          
          if (!localChatColor.equals(ChatColor.RESET)) {
            if (isFormat(localChatColor)) {
              localFancyMessage.style(new ChatColor[] { localChatColor });
            } else {
              localFancyMessage.color(localChatColor);
            }
          }
        }
        else
        {
          localFancyMessage.then(str2);
          
          if (localObject1 != null) {
            localFancyMessage.color(localObject1);
            localObject1 = null;
          }
          
          if (localObject2 != null) {
            localFancyMessage.style(new ChatColor[] { localObject2 });
            localObject2 = null;
          }
          
          if (!localChatColor.equals(ChatColor.RESET)) {
            if (isFormat(localChatColor)) {
              localFancyMessage.style(new ChatColor[] { localChatColor });
            } else {
              localFancyMessage.color(localChatColor);
            }
          }
        }
      }
    }
    


    if (localFancyMessage == null) {
      return new FancyMessage(paramString).toJSONString();
    }
    return localFancyMessage.toJSONString();
  }
  

  public static String getLastColor(String paramString)
  {
    paramString = ChatColor.translateAlternateColorCodes('&', paramString);
    
    String str = "";
    
    if ((paramString == null) || (paramString.isEmpty()))
    {
      return str;
    }
    
    if ((paramString.length() >= 2) && (paramString.charAt(paramString.length() - 2) == '§'))
    {
      char c1 = paramString.charAt(paramString.length() - 1);
      
      ChatColor localChatColor1 = ChatColor.getByChar(c1);
      
      if (localChatColor1 == null)
      {
        return str;
      }
      
      str = str + localChatColor1;
      
      if ((paramString.length() >= 4) && (paramString.charAt(paramString.length() - 4) == '§'))
      {
        char c2 = paramString.charAt(paramString.length() - 3);
        
        ChatColor localChatColor2 = ChatColor.getByChar(c2);
        
        if (localChatColor2 == null)
        {
          return str;
        }
        
        str = localChatColor2 + str;
        
        if ((paramString.length() >= 6) && (paramString.charAt(paramString.length() - 6) == '§'))
        {
          char c3 = paramString.charAt(paramString.length() - 5);
          
          ChatColor localChatColor3 = ChatColor.getByChar(c3);
          
          if (localChatColor3 == null)
          {
            return str;
          }
          
          str = localChatColor3 + str;
        }
      }
    }
    

    return str;
  }
  
  public static String setMsgColor(String paramString1, String paramString2) {
    if (paramString1.contains(" ")) {
      paramString1 = ChatColor.translateAlternateColorCodes('&', paramString1);
      String[] arrayOfString1 = paramString1.split(" ");
      String str1 = "";
      String str2 = paramString2;
      for (String str3 : arrayOfString1) {
        if ((str3.length() >= 2) && (str3.charAt(0) == '§')) {
          str2 = str3.charAt(0) + str3.charAt(1);
          if ((str3.length() >= 4) && (str3.charAt(2) == '§')) {
            str2 = str2 + str3.charAt(2) + str3.charAt(3);
          }
          if ((str3.length() >= 6) && (str3.charAt(4) == '§')) {
            str2 = str2 + str3.charAt(4) + str3.charAt(5);
          }
        }
        
        str1 = str1 + str2 + str3 + " ";
      }
      paramString1 = str1;
    }
    else {
      return paramString2 + paramString1;
    }
    return paramString1;
  }
  
  public static String checkColor(Player paramPlayer, String paramString, boolean paramBoolean)
  {
    paramString = paramString.replaceAll("[§]", "&");
    String str;
    if (paramBoolean)
    {
      if (!paramPlayer.hasPermission("deluxechat.pm.color")) {
        str = "(&+)([0-9a-fA-F])";
        paramString = paramString.replaceAll(str, "");
      }
      
      if (!paramPlayer.hasPermission("deluxechat.pm.formatting")) {
        str = "(&+)([k-orK-OR])";
        paramString = paramString.replaceAll(str, "");
      }
    }
    else
    {
      if (!paramPlayer.hasPermission("deluxechat.color")) {
        str = "(&+)([0-9a-fA-F])";
        paramString = paramString.replaceAll(str, "");
      }
      
      if (!paramPlayer.hasPermission("deluxechat.formatting")) {
        str = "(&+)([k-orK-OR])";
        paramString = paramString.replaceAll(str, "");
      }
    }
    

    return paramString;
  }
  
  public static String removeBlacklisted(String paramString) {
    if ((!DeluxeChat.useBlacklist) || 
      (DeluxeChat.blacklist == null) || 
      (DeluxeChat.blacklist.isEmpty()))
      return paramString;
    Iterator localIterator;
    String str1;
    String str2; if (!DeluxeChat.blacklistIgnoreCase) {
      for (localIterator = DeluxeChat.blacklist.keySet().iterator(); localIterator.hasNext();) { str1 = (String)localIterator.next();
        
        str2 = (String)DeluxeChat.blacklist.get(str1);
        
        if (str2.equalsIgnoreCase(str1)) {
          paramString = paramString.replace(str1, "");
        }
        else {
          paramString = paramString.replace(str1, str2);
        }
        
      }
      
    } else {
      for (localIterator = DeluxeChat.blacklist.keySet().iterator(); localIterator.hasNext();) { str1 = (String)localIterator.next();
        if (paramString.indexOf(" ") == -1) {
          if (paramString.equalsIgnoreCase(str1)) {
            str2 = (String)DeluxeChat.blacklist.get(str1);
            
            if (str2.equalsIgnoreCase(str1)) {
              paramString = paramString.replace(paramString, "");
              break;
            }
            paramString = paramString.replace(paramString, str2);
            
            break;
          }
        } else {
          for (str2 : paramString.split(" ")) {
            if (str2.equalsIgnoreCase(str1)) {
              String str3 = (String)DeluxeChat.blacklist.get(str1);
              
              if (str3.equalsIgnoreCase(str1)) {
                paramString = paramString.replace(str2, "");
              }
              else {
                paramString = paramString.replace(str2, str3);
              }
            }
          }
        }
      }
    }
    
    return paramString;
  }
}
