package me.clip.deluxechat.listeners;

import me.clip.deluxechat.events.ChatToPlayerEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatToPlayerListener
  implements Listener
{
  public ChatToPlayerListener() {}
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onChhat(ChatToPlayerEvent paramChatToPlayerEvent)
  {
    paramChatToPlayerEvent.setJSONFormat(PlaceholderAPI.setRelationalPlaceholders(paramChatToPlayerEvent.getPlayer(), paramChatToPlayerEvent.getRecipient(), paramChatToPlayerEvent.getJSONFormat()));
  }
}
