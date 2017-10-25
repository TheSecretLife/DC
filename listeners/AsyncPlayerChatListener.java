package me.clip.deluxechat.listeners;

import java.util.Iterator;
import java.util.Set;
import me.clip.deluxechat.DeluxeChat;
import me.clip.deluxechat.DeluxeUtil;
import me.clip.deluxechat.Lang;
import me.clip.deluxechat.compatibility.CompatibilityManager;
import me.clip.deluxechat.events.ChatToPlayerEvent;
import me.clip.deluxechat.events.DeluxeChatEvent;
import me.clip.deluxechat.events.DeluxeChatJSONEvent;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.objects.DeluxeFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;

public class AsyncPlayerChatListener implements org.bukkit.event.Listener
{
  private DeluxeChat plugin;
  
  public AsyncPlayerChatListener(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onChhat(AsyncPlayerChatEvent paramAsyncPlayerChatEvent)
  {
    Player localPlayer = paramAsyncPlayerChatEvent.getPlayer();
    
    if (plugin.isMuted(localPlayer)) {
      paramAsyncPlayerChatEvent.setCancelled(true);
      return;
    }
    
    String str = paramAsyncPlayerChatEvent.getMessage();
    
    if ((DeluxeUtil.containsInvalidChars(str)) && 
      (!localPlayer.hasPermission("deluxechat.utf"))) {
      DeluxeUtil.sms(localPlayer, Lang.CHAT_ILLEGAL_CHARACTERS.getConfigValue(null));
      paramAsyncPlayerChatEvent.setCancelled(true);
      return;
    }
    
    if (!localPlayer.hasPermission("deluxechat.filter.bypass")) {
      str = DeluxeUtil.removeBlacklisted(str);
    }
    
    str = DeluxeUtil.checkColor(localPlayer, str, false);
    
    paramAsyncPlayerChatEvent.setMessage(str);
  }
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onChat(AsyncPlayerChatEvent paramAsyncPlayerChatEvent)
  {
    if ((DeluxeChat.getFormats() == null) || (DeluxeChat.getFormats().isEmpty())) {
      return;
    }
    Player localPlayer = paramAsyncPlayerChatEvent.getPlayer();
    
    String str1 = paramAsyncPlayerChatEvent.getMessage();
    
    str1 = org.bukkit.ChatColor.translateAlternateColorCodes('&', str1);
    
    DeluxeFormat localDeluxeFormat = DeluxeFormat.newInstance(plugin.getPlayerFormat(localPlayer));
    
    DeluxeChatEvent localDeluxeChatEvent = new DeluxeChatEvent(localPlayer, paramAsyncPlayerChatEvent.getRecipients(), localDeluxeFormat, str1);
    
    Bukkit.getPluginManager().callEvent(localDeluxeChatEvent);
    
    if (localDeluxeChatEvent.isCancelled()) {
      paramAsyncPlayerChatEvent.setCancelled(true);
      return;
    }
    
    localDeluxeFormat = localDeluxeChatEvent.getDeluxeFormat();
    
    FancyMessage localFancyMessage = plugin.getFancyChatFormat(localPlayer, localDeluxeFormat);
    
    if (localFancyMessage == null) {
      plugin.getLog().severe("There was an error getting the chat format for player" + localPlayer.getName());
      return;
    }
    
    Set localSet = localDeluxeChatEvent.getRecipients();
    
    if (localSet == null) {
      localFancyMessage = null;
      paramAsyncPlayerChatEvent.setCancelled(true);
      return;
    }
    
    String str2 = localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + localDeluxeChatEvent.getChatMessage();
    
    boolean bool1 = DeluxeChat.useRelationPlaceholders();
    
    String str3 = plugin.getChat().convertMsg(localPlayer, str2);
    Object localObject;
    if (bool1)
    {
      DeluxeChatJSONEvent localDeluxeChatJSONEvent = new DeluxeChatJSONEvent(localPlayer, localFancyMessage.toJSONString(), str3, str2);
      
      Bukkit.getPluginManager().callEvent(localDeluxeChatJSONEvent);
      
      if ((localDeluxeChatJSONEvent.getJSONFormat() == null) || 
        (localDeluxeChatJSONEvent.getJSONChatMessage() == null) || 
        (localDeluxeChatJSONEvent.getJSONFormat().isEmpty())) {
        return;
      }
      
      for (Iterator localIterator = localSet.iterator(); localIterator.hasNext();) { localObject = (Player)localIterator.next();
        
        plugin.getChat().sendDirectChat(localPlayer, localDeluxeChatJSONEvent.getJSONFormat(), localDeluxeChatJSONEvent.getJSONChatMessage(), localPlayer, (Player)localObject);
      }
    }
    else {
      plugin.getChat().sendDeluxeChat(localPlayer, localFancyMessage.toJSONString(), str3, localSet);
    }
    
    localSet.clear();
    paramAsyncPlayerChatEvent.getRecipients().clear();
    
    if (DeluxeChat.useBungee())
    {
      if ((!DeluxeChat.isLocal(localPlayer.getUniqueId().toString())) && (localPlayer.hasPermission("deluxechat.bungee.chat")))
      {
        boolean bool2 = localPlayer.hasPermission("deluxechat.bungee.override");
        
        if (bool1)
        {
          localObject = new ChatToPlayerEvent(localPlayer, localPlayer, localFancyMessage.toJSONString(), str3, true);
          
          Bukkit.getPluginManager().callEvent((org.bukkit.event.Event)localObject);
          
          if ((((ChatToPlayerEvent)localObject).getJSONFormat() == null) || 
            (((ChatToPlayerEvent)localObject).getChatMessage() == null) || 
            (((ChatToPlayerEvent)localObject).getJSONFormat().isEmpty()) || 
            (((ChatToPlayerEvent)localObject).getChatMessage().isEmpty())) {
            return;
          }
          
          DeluxeChat.forwardString(localPlayer, ((ChatToPlayerEvent)localObject).getJSONFormat(), ((ChatToPlayerEvent)localObject).getChatMessage(), bool2);
        }
        else {
          DeluxeChat.forwardString(localPlayer, localFancyMessage.toJSONString(), str3, bool2);
        }
      }
    }
  }
}
