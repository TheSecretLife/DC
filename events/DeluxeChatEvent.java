package me.clip.deluxechat.events;

import java.util.Set;
import me.clip.deluxechat.objects.DeluxeFormat;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeluxeChatEvent
  extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  
  private final Player player;
  
  private Set<Player> recipients;
  
  private DeluxeFormat rawFormat;
  
  private String chatMessage;
  
  private boolean cancelled;
  
  public DeluxeChatEvent(Player paramPlayer, Set<Player> paramSet, DeluxeFormat paramDeluxeFormat, String paramString)
  {
    player = paramPlayer;
    recipients = paramSet;
    chatMessage = paramString;
    rawFormat = paramDeluxeFormat;
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public DeluxeFormat getDeluxeFormat() {
    return rawFormat;
  }
  
  public void setDeluxeFormat(DeluxeFormat paramDeluxeFormat) {
    rawFormat = paramDeluxeFormat;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  
  public Set<Player> getRecipients() {
    return recipients;
  }
  
  public void setRecipients(Set<Player> paramSet) {
    recipients = paramSet;
  }
  
  public String getChatMessage() {
    return chatMessage;
  }
  
  public void setChatMessage(String paramString) {
    chatMessage = paramString;
  }
  
  public boolean isCancelled()
  {
    return cancelled;
  }
  
  public void setCancelled(boolean paramBoolean)
  {
    cancelled = paramBoolean;
  }
}
