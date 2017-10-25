package me.clip.deluxechat.compatibility;

import java.util.Set;
import me.clip.deluxechat.fanciful.FancyMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract interface CompatibilityManager
{
  public abstract void sendDirectChat(Player paramPlayer1, String paramString1, String paramString2, Player paramPlayer2, Player paramPlayer3);
  
  public abstract void sendDeluxeChat(Player paramPlayer, String paramString1, String paramString2, Set<Player> paramSet);
  
  public abstract void sendFancyMessage(CommandSender paramCommandSender, FancyMessage paramFancyMessage);
  
  public abstract void sendBungeeChat(String paramString1, String paramString2, boolean paramBoolean);
  
  public abstract void sendPrivateMessage(Player paramPlayer, String paramString1, String paramString2);
  
  public abstract String convertMsg(Player paramPlayer, String paramString);
  
  public abstract String convertPm(Player paramPlayer, String paramString);
}
