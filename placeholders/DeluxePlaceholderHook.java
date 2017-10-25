package me.clip.deluxechat.placeholders;

import org.bukkit.entity.Player;

@Deprecated
public abstract class DeluxePlaceholderHook
{
  public DeluxePlaceholderHook() {}
  
  public abstract String onPlaceholderRequest(Player paramPlayer, String paramString);
}
