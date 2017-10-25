package me.clip.deluxechat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements org.bukkit.command.CommandExecutor
{
  DeluxeChat plugin;
  
  public ToggleCommand(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  

  public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString)
  {
    if (!(paramCommandSender instanceof Player)) {
      return true;
    }
    
    if (!DeluxeChat.useBungee()) {
      return true;
    }
    


    Player localPlayer = (Player)paramCommandSender;
    
    if (!localPlayer.hasPermission("deluxechat.bungee.toggle")) {
      DeluxeUtil.sms(paramCommandSender, Lang.NO_PERMISSION.getConfigValue(new String[] {
        "deluxechat.bungee.toggle" }));
      
      return true;
    }
    
    String str = localPlayer.getUniqueId().toString();
    
    if (DeluxeChat.isLocal(str)) {
      plugin.setGlobal(str);
      DeluxeUtil.sms(paramCommandSender, Lang.BUNGEE_GLOBAL_TOGGLE_ON.getConfigValue(null));
    }
    else {
      plugin.setLocal(str);
      DeluxeUtil.sms(paramCommandSender, Lang.BUNGEE_GLOBAL_TOGGLE_OFF.getConfigValue(null));
    }
    
    return true;
  }
}
