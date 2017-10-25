package me.clip.deluxechat.hooks;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.clip.deluxechat.DeluxeChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;


public class EssentialsHook
  implements PluginHook
{
  DeluxeChat plugin;
  private static Essentials essentials = null;
  
  public EssentialsHook(DeluxeChat paramDeluxeChat) {
    plugin = paramDeluxeChat;
  }
  
  public boolean isIgnored(Player paramPlayer1, Player paramPlayer2)
  {
    if (essentials == null) {
      return false;
    }
    
    User localUser1 = essentials.getUser(paramPlayer1);
    
    if (localUser1 == null) {
      return false;
    }
    
    User localUser2 = essentials.getUser(paramPlayer2);
    
    if (localUser2 == null) {
      return false;
    }
    
    return localUser1.isIgnoredPlayer(localUser2);
  }
  
  public boolean isVanished(Player paramPlayer1, Player paramPlayer2) {
    if (essentials == null) {
      return false;
    }
    User localUser = essentials.getUser(paramPlayer1);
    
    if (localUser == null) {
      return false;
    }
    
    return localUser.isHidden(paramPlayer2);
  }
  
  public boolean isMuted(Player paramPlayer) {
    if (essentials == null) {
      return false;
    }
    User localUser = essentials.getUser(paramPlayer);
    
    if (localUser == null) {
      return false;
    }
    
    return localUser.isMuted();
  }
  
  public boolean hook()
  {
    essentials = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
    return essentials != null;
  }
}
