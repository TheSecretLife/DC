package me.clip.deluxechat.listeners;

import com.palmergames.bukkit.TownyChat.TownyChatFormatter;
import com.palmergames.bukkit.TownyChat.channels.Channel;
import com.palmergames.bukkit.TownyChat.config.ChatSettings;
import com.palmergames.bukkit.TownyChat.events.AsyncChatHookEvent;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.permissions.TownyPermissionSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

public class TownyChatListener implements org.bukkit.event.Listener
{
  private DeluxeChat plugin;
  private static Map<String, ChatPlayer> players;
  
  public TownyChatListener(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
    players = new HashMap();
  }
  
  public boolean addPlaceholders()
  {
    boolean bool = me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook("TownyChat", 
      new PlaceholderHook()
      {
        public String onPlaceholderRequest(Player paramAnonymousPlayer, String paramAnonymousString)
        {
          String str1;
          switch ((str1 = paramAnonymousString).hashCode()) {case -1229311701:  if (str1.equals("message_color")) {} break; case -81305529:  if (str1.equals("channel_name")) break;  case 274477662:  if ((goto 112) && (str1.equals("channel_tag")))
            {

              return TownyChatListener.this.getChatPlayer(paramAnonymousPlayer).getTag();
              
              return TownyChatListener.this.getChatPlayer(paramAnonymousPlayer).getChannel();
              
              return TownyChatListener.this.getChatPlayer(paramAnonymousPlayer).getColor();
            }
            break;
          }
          try {
            Resident localResident = TownyUniverse.getDataSource().getResident(paramAnonymousPlayer.getName());
            String str2;
            switch ((str2 = paramAnonymousString).hashCode()) {case -1930788937:  if (str2.equals("channeltag")) {} break; case -1852993317:  if (str2.equals("surname")) {} break; case -1570224236:  if (str2.equals("towntagoverride")) {} break; case -1200578183:  if (str2.equals("townyprefix")) {} break; case -1132629048:  if (str2.equals("towntag")) {} break; case -1052618937:  if (str2.equals("nation")) {} break; case -1014409485:  if (str2.equals("nationtag")) {} break; case -751594989:  if (str2.equals("townytag")) {} break; case -743553124:  if (str2.equals("townycolor")) {} break; case -648438781:  if (str2.equals("townynamepostfix")) {} break; case -318557822:  if (str2.equals("permprefix")) {} break; case -229870015:  if (str2.equals("permsuffix")) {} break; case 3566226:  if (str2.equals("town")) {} break; case 54397546:  if (str2.equals("townformatted")) {} break; case 73387967:  if (str2.equals("nationtagoverride")) {} break; case 98629247:  if (str2.equals("group")) {} break; case 110371416:  if (str2.equals("title")) {} break; case 113318802:  if (str2.equals("world")) break; break; case 405596895:  if (str2.equals("townytagoverride")) {} break; case 869860469:  if (str2.equals("townyformatted")) {} break; case 951254084:  if (str2.equals("townynameprefix")) {} break; case 1364237678:  if (str2.equals("townypostfix")) {} break; case 1598005589:  if (!str2.equals("nationformatted"))
              {
                break label1003;
                return String.format(ChatSettings.getWorldTag(), new Object[] { paramAnonymousPlayer.getWorld().getName() });
                
                return localResident.hasTown() ? localResident.getTown().getName() : "";
                
                return TownyChatFormatter.formatTownTag(localResident, Boolean.valueOf(false), Boolean.valueOf(true));
                
                return TownyChatFormatter.formatTownTag(localResident, Boolean.valueOf(false), Boolean.valueOf(false));
                
                return TownyChatFormatter.formatTownTag(localResident, Boolean.valueOf(true), Boolean.valueOf(false));
                
                return localResident.hasNation() ? localResident.getTown().getNation().getName() : "";
              } else {
                return TownyChatFormatter.formatNationTag(localResident, Boolean.valueOf(false), Boolean.valueOf(true));
                
                return TownyChatFormatter.formatNationTag(localResident, Boolean.valueOf(false), Boolean.valueOf(false));
                
                return TownyChatFormatter.formatNationTag(localResident, Boolean.valueOf(true), Boolean.valueOf(false));
                
                return TownyChatFormatter.formatTownyTag(localResident, Boolean.valueOf(false), Boolean.valueOf(false));
                
                return TownyChatFormatter.formatTownyTag(localResident, Boolean.valueOf(false), Boolean.valueOf(true));
                
                return TownyChatFormatter.formatTownyTag(localResident, Boolean.valueOf(true), Boolean.valueOf(false));
                
                return localResident.hasTitle() ? localResident.getTitle() : "";
                
                return localResident.hasSurname() ? localResident.getSurname() : "";
                
                return TownyFormatter.getNamePrefix(localResident);
                
                return TownyFormatter.getNamePostfix(localResident);
                
                return localResident.hasTitle() ? localResident.getTitle() : TownyFormatter.getNamePrefix(localResident);
                
                return localResident.hasSurname() ? localResident.getSurname() : TownyFormatter.getNamePostfix(localResident);
                
                return localResident.isKing() ? ChatSettings.getKingColour() : localResident.isMayor() ? ChatSettings.getMayorColour() : ChatSettings.getResidentColour();
                
                return TownyUniverse.getPermissionSource().getPlayerGroup(paramAnonymousPlayer);
                
                return TownyUniverse.getPermissionSource().getPrefixSuffix(localResident, "prefix");
                
                return TownyUniverse.getPermissionSource().getPrefixSuffix(localResident, "suffix");
                
                return TownyChatFormatter.formatTownyTag(localResident, Boolean.valueOf(false), Boolean.valueOf(false));
              }
              break; }
          } catch (NotRegisteredException localNotRegisteredException) {
            return "";
          }
          label1003:
          return null;
        }
        

      });
    return bool;
  }
  
  public void clear() {
    players = null;
  }
  
  public class ChatPlayer
  {
    private String player;
    private String channel;
    private String tag;
    private String color;
    
    public ChatPlayer(String paramString1, String paramString2, String paramString3, String paramString4) {
      setPlayer(paramString1);
      if (paramString2 != null) {
        setChannel(paramString2);
      } else {
        setChannel("");
      }
      if (paramString3 != null) {
        setTag(paramString3);
      } else {
        setTag("");
      }
      if (paramString4 != null) {
        setColor(paramString4);
      } else {
        setColor("");
      }
    }
    
    public String getPlayer() {
      return player;
    }
    
    public void setPlayer(String paramString) {
      player = paramString;
    }
    
    public String getChannel() {
      if (channel == null) {
        return "";
      }
      return channel;
    }
    
    public void setChannel(String paramString) {
      channel = paramString;
    }
    
    public String getTag() {
      if (tag == null) {
        return "";
      }
      return tag;
    }
    
    public void setTag(String paramString) {
      tag = paramString;
    }
    
    public String getColor() {
      if (color == null) {
        return "";
      }
      return color;
    }
    
    public void setColor(String paramString) {
      color = paramString;
    }
  }
  
  private void updatePlayer(String paramString1, String paramString2, String paramString3, String paramString4) {
    if (players == null) {
      players = new HashMap();
    }
    if ((players.containsKey(paramString1)) && (players.get(paramString1) != null)) {
      ((ChatPlayer)players.get(paramString1)).setChannel(paramString2);
      ((ChatPlayer)players.get(paramString1)).setTag(paramString3);
      ((ChatPlayer)players.get(paramString1)).setColor(paramString4);
    } else {
      ChatPlayer localChatPlayer = new ChatPlayer(paramString1, paramString2, paramString3, paramString4);
      players.put(paramString1, localChatPlayer);
    }
  }
  
  private ChatPlayer getChatPlayer(Player paramPlayer)
  {
    return (players != null) && (players.containsKey(paramPlayer.getName())) && (players.get(paramPlayer.getName()) != null) ? 
      (ChatPlayer)players.get(paramPlayer.getName()) : new ChatPlayer(paramPlayer.getName(), "", "", "");
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent paramPlayerQuitEvent)
  {
    if ((players == null) || (players.isEmpty())) {
      return;
    }
    
    if (players.containsKey(paramPlayerQuitEvent.getPlayer().getName())) {
      players.remove(paramPlayerQuitEvent.getPlayer().getName());
    }
  }
  


  @EventHandler(priority=EventPriority.HIGHEST)
  public void onChat(AsyncChatHookEvent paramAsyncChatHookEvent)
  {
    if (paramAsyncChatHookEvent.isCancelled()) {
      return;
    }
    
    if ((DeluxeChat.getFormats() == null) || (DeluxeChat.getFormats().isEmpty())) {
      return;
    }
    
    Player localPlayer = paramAsyncChatHookEvent.getPlayer();
    
    if (plugin.isMuted(localPlayer)) {
      paramAsyncChatHookEvent.setCancelled(true);
      return;
    }
    
    String str1 = paramAsyncChatHookEvent.getMessage();
    
    if ((DeluxeUtil.containsInvalidChars(str1)) && 
      (!localPlayer.hasPermission("deluxechat.utf"))) {
      DeluxeUtil.sms(localPlayer, Lang.CHAT_ILLEGAL_CHARACTERS.getConfigValue(null));
      paramAsyncChatHookEvent.setCancelled(true);
      return;
    }
    
    if (!localPlayer.hasPermission("deluxechat.filter.bypass")) {
      str1 = DeluxeUtil.removeBlacklisted(str1);
    }
    
    str1 = DeluxeUtil.checkColor(localPlayer, str1, false);
    
    String str2 = "";
    
    String str3 = "";
    
    String str4 = "";
    
    if (paramAsyncChatHookEvent.getChannel() != null) {
      str2 = paramAsyncChatHookEvent.getChannel().getChannelTag();
      str3 = paramAsyncChatHookEvent.getChannel().getName();
      str4 = paramAsyncChatHookEvent.getChannel().getMessageColour();
    }
    
    updatePlayer(paramAsyncChatHookEvent.getPlayer().getName(), str3, str2, str4);
    
    str1 = org.bukkit.ChatColor.translateAlternateColorCodes('&', str1);
    
    DeluxeFormat localDeluxeFormat = DeluxeFormat.newInstance(plugin.getPlayerFormat(localPlayer));
    
    DeluxeChatEvent localDeluxeChatEvent = new DeluxeChatEvent(localPlayer, paramAsyncChatHookEvent.getRecipients(), localDeluxeFormat, str1);
    
    Bukkit.getPluginManager().callEvent(localDeluxeChatEvent);
    
    if (localDeluxeChatEvent.isCancelled()) {
      paramAsyncChatHookEvent.setCancelled(true);
      return;
    }
    
    localDeluxeFormat = localDeluxeChatEvent.getDeluxeFormat();
    
    FancyMessage localFancyMessage = plugin.getFancyChatFormat(localPlayer, localDeluxeFormat);
    
    if (localFancyMessage == null) {
      plugin.getLog().severe("There was an error getting the chat format for player " + localPlayer.getName());
      return;
    }
    
    Set localSet = localDeluxeChatEvent.getRecipients();
    
    if (localSet == null) {
      localFancyMessage = null;
      paramAsyncChatHookEvent.setCancelled(true);
      return;
    }
    
    String str5 = localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + localDeluxeChatEvent.getChatMessage();
    
    String str6 = plugin.getChat().convertMsg(localPlayer, str5);
    Object localObject;
    if (DeluxeChat.useRelationPlaceholders())
    {
      DeluxeChatJSONEvent localDeluxeChatJSONEvent = new DeluxeChatJSONEvent(localPlayer, localFancyMessage.toJSONString(), str6, str5);
      
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
    else
    {
      plugin.getChat().sendDeluxeChat(localPlayer, localFancyMessage.toJSONString(), str6, localSet);
    }
    
    localSet.clear();
    paramAsyncChatHookEvent.getRecipients().clear();
    
    Bukkit.getConsoleSender().sendMessage("[CHAT] " + localPlayer.getName() + ": " + str5);
    
    if (DeluxeChat.useBungee())
    {
      if ((!DeluxeChat.isLocal(localPlayer.getUniqueId().toString())) && (localPlayer.hasPermission("deluxechat.bungee.chat")))
      {
        if (paramAsyncChatHookEvent.getChannel().getType() != com.palmergames.bukkit.TownyChat.channels.channelTypes.GLOBAL) {
          return;
        }
        
        boolean bool = localPlayer.hasPermission("deluxechat.bungee.override");
        
        if (DeluxeChat.useRelationPlaceholders())
        {
          localObject = new ChatToPlayerEvent(localPlayer, localPlayer, localFancyMessage.toJSONString(), str6, true);
          
          Bukkit.getPluginManager().callEvent((org.bukkit.event.Event)localObject);
          
          if ((((ChatToPlayerEvent)localObject).getJSONFormat() == null) || 
            (((ChatToPlayerEvent)localObject).getChatMessage() == null) || 
            (((ChatToPlayerEvent)localObject).getJSONFormat().isEmpty()) || 
            (((ChatToPlayerEvent)localObject).getChatMessage().isEmpty())) {
            return;
          }
          
          DeluxeChat.forwardString(localPlayer, ((ChatToPlayerEvent)localObject).getJSONFormat(), ((ChatToPlayerEvent)localObject).getChatMessage(), bool);
        }
        else {
          DeluxeChat.forwardString(localPlayer, localFancyMessage.toJSONString(), str6, bool);
        }
      }
    }
  }
}
