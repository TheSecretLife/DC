package me.clip.deluxechat.placeholders;

import org.bukkit.entity.Player;

@Deprecated
public abstract class DeluxeRecipientPlaceholderHook
{
  public DeluxeRecipientPlaceholderHook() {}
  
  public abstract String onRecipientPlaceholderRequest(Player paramPlayer1, Player paramPlayer2, String paramString);
}
