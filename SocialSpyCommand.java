package me.clip.deluxechat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialSpyCommand
  implements CommandExecutor
{
  DeluxeChat plugin;
  
  public SocialSpyCommand(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  

  public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString)
  {
    if (!(paramCommandSender instanceof Player)) {
      paramCommandSender.sendMessage("This command is not supported in console yet!");
      return true;
    }
    
    Player localPlayer = (Player)paramCommandSender;
    
    if (DeluxeChat.inSocialSpy(localPlayer))
    {
      DeluxeChat.disableSocialSpy(localPlayer);
      DeluxeUtil.sms(localPlayer, Lang.SOCIALSPY_TOGGLE_OFF.getConfigValue(null));
    }
    else
    {
      if (!localPlayer.hasPermission("deluxechat.socialspy")) {
        DeluxeUtil.sms(localPlayer, Lang.NO_PERMISSION.getConfigValue(new String[] {
          "deluxechat.socialspy" }));
        

        return true;
      }
      
      DeluxeChat.enableSocialSpy(localPlayer);
      DeluxeUtil.sms(localPlayer, Lang.SOCIALSPY_TOGGLE_ON.getConfigValue(null));
    }
    
    return true;
  }
}
