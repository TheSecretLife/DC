package me.clip.deluxechat.hooks;

import org.bukkit.entity.Player;
import org.kitteh.vanish.staticaccess.VanishNoPacket;

public class VanishNoPacketHook
  implements PluginHook
{
  private boolean hooked;
  
  public VanishNoPacketHook() {}
  
  public boolean hook()
  {
    return true;
  }
  
  public boolean canSee(Player paramPlayer1, Player paramPlayer2) {
    if (!hooked) {
      return false;
    }
    try {
      return VanishNoPacket.canSee(paramPlayer1, paramPlayer2);
    } catch (Exception localException) {}
    return false;
  }
  
  public boolean isHooked()
  {
    return hooked;
  }
}
