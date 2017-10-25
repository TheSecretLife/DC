package me.clip.deluxechat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeluxeChatJSONEvent extends Event implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  
  private final Player player;
  
  private String jsonFormat;
  
  private String jsonChatMessage;
  
  private String rawChatMessage;
  
  private boolean cancelled;
  
  public DeluxeChatJSONEvent(Player paramPlayer, String paramString1, String paramString2, String paramString3)
  {
    player = paramPlayer;
    jsonChatMessage = paramString2;
    rawChatMessage = paramString3;
    jsonFormat = paramString1;
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public String getJSONFormat() {
    return jsonFormat;
  }
  
  public void setJSONFormat(String paramString) {
    jsonFormat = paramString;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  
  public String getJSONChatMessage() {
    return jsonChatMessage;
  }
  
  public void setJSONChatMessage(String paramString) {
    jsonChatMessage = paramString;
  }
  
  public boolean isCancelled()
  {
    return cancelled;
  }
  
  public void setCancelled(boolean paramBoolean)
  {
    cancelled = paramBoolean;
  }
  
  public String getRawChatMessage() {
    return rawChatMessage;
  }
}
