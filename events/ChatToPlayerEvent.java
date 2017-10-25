package me.clip.deluxechat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatToPlayerEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  
  private final Player player;
  
  private Player recipient;
  
  private String jsonFormat;
  
  private String chatMessage;
  
  private boolean bungeeMessage;
  
  public ChatToPlayerEvent(Player paramPlayer1, Player paramPlayer2, String paramString1, String paramString2, boolean paramBoolean)
  {
    player = paramPlayer1;
    recipient = paramPlayer2;
    chatMessage = paramString2;
    jsonFormat = paramString1;
    bungeeMessage = paramBoolean;
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
  
  public Player getRecipient() {
    return recipient;
  }
  
  public String getChatMessage() {
    return chatMessage;
  }
  
  public void setChatMessage(String paramString) {
    chatMessage = paramString;
  }
  
  public boolean isBungeeMessage() {
    return bungeeMessage;
  }
}
