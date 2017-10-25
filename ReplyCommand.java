package me.clip.deluxechat;

import java.util.Collection;
import java.util.Iterator;
import me.clip.deluxechat.compatibility.CompatibilityManager;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.hooks.EssentialsHook;
import me.clip.deluxechat.hooks.VanishNoPacketHook;
import me.clip.deluxechat.placeholders.PlaceholderHandler;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements org.bukkit.command.CommandExecutor
{
  DeluxeChat plugin;
  
  public ReplyCommand(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  

  public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString)
  {
    if (!(paramCommandSender instanceof Player))
    {
      return true;
    }
    
    Player localPlayer1 = (Player)paramCommandSender;
    
    if (!localPlayer1.hasPermission("deluxechat.pm")) {
      DeluxeUtil.sms(localPlayer1, Lang.NO_PERMISSION.getConfigValue(new String[] {
        "deluxechat.pm" }));
      
      return true;
    }
    
    if (DeluxeChat.getPmRecipient(localPlayer1.getName()) == null) {
      DeluxeUtil.sms(localPlayer1, Lang.REPLY_NO_RECIPIENT.getConfigValue(null));
      return true;
    }
    
    if (paramArrayOfString.length < 1) {
      DeluxeUtil.sms(localPlayer1, Lang.REPLY_INCORRECT_USAGE.getConfigValue(null));
      
      return true;
    }
    
    if (plugin.isMuted(localPlayer1)) {
      return true;
    }
    
    String str1 = DeluxeChat.getPmRecipient(localPlayer1.getName());
    
    String str2 = org.apache.commons.lang.StringUtils.join(java.util.Arrays.copyOfRange(paramArrayOfString, 0, paramArrayOfString.length), " ");
    
    str2 = DeluxeUtil.checkColor(localPlayer1, str2, true);
    
    if (str2.isEmpty())
      return true;
    FancyMessage localFancyMessage;
    Object localObject1;
    String str3; Object localObject2; Iterator localIterator; if (DeluxeChat.bungeePMEnabled())
    {
      localPlayer2 = Bukkit.getPlayer(str1);
      
      if (localPlayer2 != null)
      {
        if ((plugin.vanishNoPacket != null) && 
          (!plugin.vanishNoPacket.canSee(localPlayer1, localPlayer2)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
            str1 }));
          
          return true;
        }
        

        if (plugin.essentials != null) {
          if ((plugin.essentials.isVanished(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
            DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE
              .getConfigValue(new String[] { str1 }));
            DeluxeChat.removeFromPM(localPlayer1.getName());
            return true;
          }
          
          if ((plugin.essentials.isIgnored(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.ignore.bypass")))
          {
            DeluxeUtil.sms(localPlayer1, 
              Lang.MSG_RECIPIENT_IGNORING_SENDER
              .getConfigValue(new String[] { str1 }));
            DeluxeChat.removeFromPM(localPlayer1.getName());
            return true;
          }
        }
        
        DeluxeChat.setInPm(localPlayer1.getName(), localPlayer2.getName());
        DeluxeChat.setInPm(localPlayer2.getName(), localPlayer1.getName());
        
        localFancyMessage = plugin.getBungeePrivateMessageFormat(localPlayer1, DeluxeChat.getToSenderPMFormat());
        
        if (localFancyMessage != null)
        {
          localObject1 = localFancyMessage.toJSONString();
          
          localObject1 = PlaceholderAPI.setPlaceholders(localPlayer1, (String)localObject1);
          
          localObject1 = ((String)localObject1).replace("%recipient%", localPlayer2.getName());
          
          str3 = plugin.getChat().convertPm(localPlayer1, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str2);
          
          plugin.getChat().sendPrivateMessage(localPlayer1, ChatColor.translateAlternateColorCodes('&', (String)localObject1), str3);
        }
        
        localObject1 = plugin.getBungeePrivateMessageFormat(localPlayer1, DeluxeChat.getToRecipientPMFormat());
        
        if (localObject1 != null)
        {
          str3 = ((FancyMessage)localObject1).toJSONString();
          
          str3 = PlaceholderAPI.setPlaceholders(localPlayer1, str3);
          
          str3 = str3.replace("%recipient%", localPlayer2.getName());
          
          localObject2 = plugin.getChat().convertPm(localPlayer1, ((FancyMessage)localObject1).getLastColor() + ((FancyMessage)localObject1).getChatColor() + str2);
          
          plugin.getChat().sendPrivateMessage(localPlayer2, ChatColor.translateAlternateColorCodes('&', str3), (String)localObject2);
        }
        
        str3 = DeluxeChat.getSocialSpyFormat();
        
        if ((str3 == null) || (str3.isEmpty())) {
          return true;
        }
        
        str3 = str3.replace("%player%", localPlayer1.getName()).replace("%recipient%", localPlayer2.getName());
        
        for (localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) { localObject2 = (Player)localIterator.next();
          
          if (DeluxeChat.inSocialSpy((Player)localObject2))
          {
            if ((!((Player)localObject2).getName().equals(localPlayer1.getName())) && (!((Player)localObject2).getName().equals(localPlayer2.getName())))
            {

              DeluxeUtil.sms((Player)localObject2, str3 + str2);
            }
          }
        }
        


        return true;
      }
      
      localFancyMessage = plugin.getBungeePrivateMessageFormat(localPlayer1, DeluxeChat.getToRecipientPMFormat());
      
      if (localFancyMessage == null) {
        return true;
      }
      
      localObject1 = localFancyMessage.toJSONString().replace("%player%", localPlayer1.getName());
      
      localObject1 = PlaceholderAPI.setPlaceholders(localPlayer1, (String)localObject1);
      
      str3 = plugin.getChat().convertPm(localPlayer1, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str2);
      
      DeluxeChat.forwardPm(localPlayer1, me.clip.deluxechat.messaging.PrivateMessageType.MESSAGE_SEND, str1, ChatColor.translateAlternateColorCodes('&', (String)localObject1), str3, str2);
      

      return true;
    }
    


    Player localPlayer2 = Bukkit.getPlayer(str1);
    
    if (localPlayer2 != null)
    {
      if (localPlayer2.getName().equals(localPlayer1.getName())) {
        DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_IS_SENDER.getConfigValue(null));
        return true;
      }
      


      if ((plugin.vanishNoPacket != null) && 
        (!plugin.vanishNoPacket.canSee(localPlayer1, localPlayer2)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
        DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
          localPlayer2.getName() }));
        
        DeluxeChat.removeFromPM(localPlayer1.getName());
        return true;
      }
      
      if (plugin.essentials != null)
      {
        if ((plugin.essentials.isVanished(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
            localPlayer2.getName() }));
          
          DeluxeChat.removeFromPM(localPlayer1.getName());
          return true;
        }
        
        if ((plugin.essentials.isIgnored(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.ignore.bypass"))) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_IGNORING_SENDER.getConfigValue(new String[] {
            localPlayer2.getName() }));
          
          DeluxeChat.removeFromPM(localPlayer1.getName());
          return true;
        }
      }
      


      DeluxeChat.setInPm(localPlayer1.getName(), localPlayer2.getName());
      DeluxeChat.setInPm(localPlayer2.getName(), localPlayer1.getName());
      
      localFancyMessage = plugin.getPrivateMessageFormat(DeluxeChat.getToSenderPMFormat());
      
      if (localFancyMessage != null)
      {
        localObject1 = localFancyMessage.toJSONString();
        
        localObject1 = PlaceholderHandler.setRecipPlaceholders(localPlayer2, (String)localObject1);
        
        localObject1 = PlaceholderAPI.setPlaceholders(localPlayer1, (String)localObject1);
        

        str3 = plugin.getChat().convertPm(localPlayer1, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str2);
        
        plugin.getChat().sendPrivateMessage(localPlayer1, ChatColor.translateAlternateColorCodes('&', (String)localObject1), str3);
      }
      
      localObject1 = plugin.getPrivateMessageFormat(DeluxeChat.getToRecipientPMFormat());
      
      if (localObject1 != null)
      {
        str3 = ((FancyMessage)localObject1).toJSONString();
        
        str3 = PlaceholderHandler.setRecipPlaceholders(localPlayer2, str3);
        
        str3 = PlaceholderAPI.setPlaceholders(localPlayer1, str3);
        
        localObject2 = plugin.getChat().convertPm(localPlayer1, ((FancyMessage)localObject1).getLastColor() + ((FancyMessage)localObject1).getChatColor() + str2);
        
        plugin.getChat().sendPrivateMessage(localPlayer2, ChatColor.translateAlternateColorCodes('&', str3), (String)localObject2);
      }
      
      str3 = DeluxeChat.getSocialSpyFormat();
      
      if ((str3 == null) || (str3.isEmpty())) {
        return true;
      }
      
      str3 = str3.replace("%player%", localPlayer1.getName()).replace("%recipient%", localPlayer2.getName());
      
      for (localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) { localObject2 = (Player)localIterator.next();
        
        if (DeluxeChat.inSocialSpy((Player)localObject2))
        {
          if ((!((Player)localObject2).getName().equals(localPlayer1.getName())) && (!((Player)localObject2).getName().equals(localPlayer2.getName())))
          {

            DeluxeUtil.sms((Player)localObject2, str3 + str2);
          }
        }
      }
    } else {
      DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
        str1 }));
      
      DeluxeChat.removeFromPM(localPlayer1.getName());
    }
    return true;
  }
}
