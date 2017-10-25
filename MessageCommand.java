package me.clip.deluxechat;

import java.util.Collection;
import java.util.Iterator;
import me.clip.deluxechat.compatibility.CompatibilityManager;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.hooks.EssentialsHook;
import me.clip.deluxechat.hooks.VanishNoPacketHook;
import me.clip.deluxechat.messaging.PrivateMessageType;
import me.clip.deluxechat.placeholders.PlaceholderHandler;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor
{
  DeluxeChat plugin;
  
  public MessageCommand(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  

  public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString)
  {
    if (!(paramCommandSender instanceof Player)) {
      paramCommandSender.sendMessage("This command is not supported in console yet!");
      return true;
    }
    
    Player localPlayer1 = (Player)paramCommandSender;
    
    if (!localPlayer1.hasPermission("deluxechat.pm")) {
      DeluxeUtil.sms(localPlayer1, Lang.NO_PERMISSION.getConfigValue(new String[] {
        "deluxechat.pm" }));
      

      return true;
    }
    
    if (paramArrayOfString.length < 2) {
      DeluxeUtil.sms(localPlayer1, Lang.MSG_INCORRECT_USAGE.getConfigValue(null));
      return true;
    }
    
    if (plugin.isMuted(localPlayer1)) {
      return true;
    }
    
    String str1 = org.apache.commons.lang.StringUtils.join(java.util.Arrays.copyOfRange(paramArrayOfString, 1, paramArrayOfString.length), " ");
    
    str1 = DeluxeUtil.checkColor(localPlayer1, str1, true);
    
    if (str1.isEmpty())
      return true;
    FancyMessage localFancyMessage;
    Object localObject1;
    String str2; Object localObject2; Iterator localIterator; if (DeluxeChat.bungeePMEnabled())
    {
      localPlayer2 = Bukkit.getPlayer(paramArrayOfString[0]);
      
      if (localPlayer2 != null)
      {
        if (localPlayer2.getName().equals(localPlayer1.getName())) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_IS_SENDER.getConfigValue(null));
          return true;
        }
        
        if ((plugin.vanishNoPacket != null) && 
          (!plugin.vanishNoPacket.canSee(localPlayer1, localPlayer2)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
            paramArrayOfString[0] }));
          
          return true;
        }
        

        if (plugin.essentials != null)
        {
          if ((plugin.essentials.isVanished(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
            DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE
              .getConfigValue(new String[] { paramArrayOfString[0] }));
            return true;
          }
          
          if ((plugin.essentials.isIgnored(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.ignore.bypass"))) {
            DeluxeUtil.sms(localPlayer1, 
              Lang.MSG_RECIPIENT_IGNORING_SENDER
              .getConfigValue(new String[] {localPlayer2
              .getName() }));
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
          
          str2 = plugin.getChat().convertPm(localPlayer1, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str1);
          
          plugin.getChat().sendPrivateMessage(localPlayer1, ChatColor.translateAlternateColorCodes('&', (String)localObject1), str2);
        }
        
        localObject1 = plugin.getBungeePrivateMessageFormat(localPlayer1, DeluxeChat.getToRecipientPMFormat());
        
        if (localObject1 != null)
        {
          str2 = ((FancyMessage)localObject1).toJSONString();
          
          str2 = PlaceholderAPI.setPlaceholders(localPlayer1, str2);
          
          str2 = str2.replace("%recipient%", localPlayer2.getName());
          
          localObject2 = plugin.getChat().convertPm(localPlayer1, ((FancyMessage)localObject1).getLastColor() + ((FancyMessage)localObject1).getChatColor() + str1);
          
          plugin.getChat().sendPrivateMessage(localPlayer2, ChatColor.translateAlternateColorCodes('&', str2), (String)localObject2);
        }
        
        str2 = DeluxeChat.getSocialSpyFormat();
        
        if ((str2 == null) || (str2.isEmpty())) {
          return true;
        }
        
        str2 = str2.replace("%player%", localPlayer1.getName()).replace("%recipient%", localPlayer2.getName());
        
        for (localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) { localObject2 = (Player)localIterator.next();
          
          if (DeluxeChat.inSocialSpy((Player)localObject2))
          {
            if ((!((Player)localObject2).getName().equals(localPlayer1.getName())) && (!((Player)localObject2).getName().equals(localPlayer2.getName())))
            {

              DeluxeUtil.sms((Player)localObject2, str2 + str1);
            }
          }
        }
        


        return true;
      }
      
      localFancyMessage = plugin.getBungeePrivateMessageFormat(localPlayer1, DeluxeChat.getToRecipientPMFormat());
      
      if (localFancyMessage == null) {
        return true;
      }
      
      localObject1 = localFancyMessage.toJSONString();
      
      localObject1 = PlaceholderAPI.setPlaceholders(localPlayer1, (String)localObject1);
      
      str2 = plugin.getChat().convertPm(localPlayer1, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str1);
      
      DeluxeChat.forwardPm(localPlayer1, PrivateMessageType.MESSAGE_SEND, paramArrayOfString[0], ChatColor.translateAlternateColorCodes('&', (String)localObject1), str2, str1);
      

      return true;
    }
    


    Player localPlayer2 = Bukkit.getPlayer(paramArrayOfString[0]);
    
    if (localPlayer2 != null)
    {
      if (localPlayer2.getName().equals(localPlayer1.getName())) {
        DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_IS_SENDER.getConfigValue(null));
        return true;
      }
      
      if ((plugin.vanishNoPacket != null) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass")) && 
        (!plugin.vanishNoPacket.canSee(localPlayer1, localPlayer2))) {
        DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
          paramArrayOfString[0] }));
        
        return true;
      }
      

      if (plugin.essentials != null)
      {
        if ((plugin.essentials.isVanished(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.vanish.bypass"))) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
            paramArrayOfString[0] }));
          
          return true;
        }
        
        if ((plugin.essentials.isIgnored(localPlayer2, localPlayer1)) && (!localPlayer1.hasPermission("deluxechat.ignore.bypass"))) {
          DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_IGNORING_SENDER.getConfigValue(new String[] {
            localPlayer2.getName() }));
          
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
        
        str2 = plugin.getChat().convertPm(localPlayer1, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str1);
        
        plugin.getChat().sendPrivateMessage(localPlayer1, ChatColor.translateAlternateColorCodes('&', (String)localObject1), str2);
      }
      
      localObject1 = plugin.getPrivateMessageFormat(DeluxeChat.getToRecipientPMFormat());
      
      if (localObject1 != null)
      {
        str2 = ((FancyMessage)localObject1).toJSONString();
        
        str2 = PlaceholderHandler.setRecipPlaceholders(localPlayer2, str2);
        
        str2 = PlaceholderAPI.setPlaceholders(localPlayer1, str2);
        
        localObject2 = plugin.getChat().convertPm(localPlayer1, ((FancyMessage)localObject1).getLastColor() + ((FancyMessage)localObject1).getChatColor() + str1);
        
        plugin.getChat().sendPrivateMessage(localPlayer2, ChatColor.translateAlternateColorCodes('&', str2), (String)localObject2);
      }
      
      str2 = DeluxeChat.getSocialSpyFormat();
      
      if ((str2 == null) || (str2.isEmpty())) {
        return true;
      }
      
      str2 = str2.replace("%player%", localPlayer1.getName()).replace("%recipient%", localPlayer2.getName());
      
      for (localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) { localObject2 = (Player)localIterator.next();
        
        if (DeluxeChat.inSocialSpy((Player)localObject2))
        {
          if ((!((Player)localObject2).getName().equals(localPlayer1.getName())) && (!((Player)localObject2).getName().equals(localPlayer2.getName())))
          {

            DeluxeUtil.sms((Player)localObject2, str2 + str1);
          }
        }
      }
    } else {
      DeluxeUtil.sms(localPlayer1, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
        paramArrayOfString[0] }));
    }
    
    return true;
  }
}
