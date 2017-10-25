package me.clip.deluxechat.compatibility;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import me.clip.deluxechat.events.ChatToPlayerEvent;
import me.clip.deluxechat.events.DeluxeChatJSONEvent;
import me.clip.deluxechat.fanciful.FancyMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class Spigot_1_9_R1_Chat implements CompatibilityManager
{
  public Spigot_1_9_R1_Chat() {}
  
  public String convertMsg(Player paramPlayer, String paramString)
  {
    paramString = org.bukkit.ChatColor.translateAlternateColorCodes('&', paramString);
    
    if (paramPlayer.hasPermission("deluxechat.url")) {
      BaseComponent[] arrayOfBaseComponent = TextComponent.fromLegacyText(paramString);
      
      String str = net.md_5.bungee.chat.ComponentSerializer.toString(arrayOfBaseComponent);
      return str;
    }
    
    return me.clip.deluxechat.DeluxeUtil.convertToJson(paramString);
  }
  

  public String convertPm(Player paramPlayer, String paramString)
  {
    paramString = org.bukkit.ChatColor.translateAlternateColorCodes('&', paramString);
    
    if (paramPlayer.hasPermission("deluxechat.pm.url")) {
      BaseComponent[] arrayOfBaseComponent = TextComponent.fromLegacyText(paramString);
      
      String str = net.md_5.bungee.chat.ComponentSerializer.toString(arrayOfBaseComponent);
      return str;
    }
    
    return me.clip.deluxechat.DeluxeUtil.convertToJson(paramString);
  }
  

  public void sendPrivateMessage(Player paramPlayer, String paramString1, String paramString2)
  {
    IChatBaseComponent localIChatBaseComponent1 = IChatBaseComponent.ChatSerializer.a(paramString1);
    
    IChatBaseComponent localIChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a(paramString2);
    
    PacketPlayOutChat localPacketPlayOutChat = new PacketPlayOutChat(localIChatBaseComponent1.addSibling(localIChatBaseComponent2));
    
    getHandleplayerConnection.sendPacket(localPacketPlayOutChat);
  }
  

  public void sendDirectChat(Player paramPlayer1, String paramString1, String paramString2, Player paramPlayer2, Player paramPlayer3)
  {
    ChatToPlayerEvent localChatToPlayerEvent = new ChatToPlayerEvent(paramPlayer2, paramPlayer3, paramString1, paramString2, false);
    
    Bukkit.getPluginManager().callEvent(localChatToPlayerEvent);
    
    if ((localChatToPlayerEvent.getJSONFormat() == null) || 
      (localChatToPlayerEvent.getChatMessage() == null) || 
      (localChatToPlayerEvent.getJSONFormat().isEmpty()) || 
      (localChatToPlayerEvent.getChatMessage().isEmpty())) {
      return;
    }
    
    IChatBaseComponent localIChatBaseComponent1 = IChatBaseComponent.ChatSerializer.a(localChatToPlayerEvent.getJSONFormat());
    IChatBaseComponent localIChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a(localChatToPlayerEvent.getChatMessage());
    
    PacketPlayOutChat localPacketPlayOutChat = new PacketPlayOutChat(localIChatBaseComponent1.addSibling(localIChatBaseComponent2));
    
    getHandleplayerConnection.sendPacket(localPacketPlayOutChat);
  }
  

  public void sendDeluxeChat(Player paramPlayer, String paramString1, String paramString2, Set<Player> paramSet)
  {
    if (paramSet == null) {
      return;
    }
    
    DeluxeChatJSONEvent localDeluxeChatJSONEvent = new DeluxeChatJSONEvent(paramPlayer, paramString1, paramString2, paramString2);
    
    Bukkit.getPluginManager().callEvent(localDeluxeChatJSONEvent);
    
    if ((localDeluxeChatJSONEvent.getJSONFormat() == null) || 
      (localDeluxeChatJSONEvent.getJSONChatMessage() == null) || 
      (localDeluxeChatJSONEvent.getJSONFormat().isEmpty())) {
      return;
    }
    
    IChatBaseComponent localIChatBaseComponent1 = IChatBaseComponent.ChatSerializer.a(localDeluxeChatJSONEvent.getJSONFormat());
    IChatBaseComponent localIChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a(localDeluxeChatJSONEvent.getJSONChatMessage());
    PacketPlayOutChat localPacketPlayOutChat = new PacketPlayOutChat(localIChatBaseComponent1.addSibling(localIChatBaseComponent2));
    
    for (Player localPlayer : paramSet) {
      getHandleplayerConnection.sendPacket(localPacketPlayOutChat);
    }
  }
  

  public void sendFancyMessage(CommandSender paramCommandSender, FancyMessage paramFancyMessage)
  {
    if (!(paramCommandSender instanceof Player)) {
      paramCommandSender.sendMessage(paramFancyMessage.toOldMessageFormat());
      return;
    }
    
    Player localPlayer = (Player)paramCommandSender;
    
    try
    {
      IChatBaseComponent localIChatBaseComponent = IChatBaseComponent.ChatSerializer.a(paramFancyMessage.toJSONString());
      
      PacketPlayOutChat localPacketPlayOutChat = new PacketPlayOutChat(localIChatBaseComponent);
      
      getHandleplayerConnection.sendPacket(localPacketPlayOutChat);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {
      Bukkit.getLogger().log(java.util.logging.Level.WARNING, "Argument could not be passed.", localIllegalArgumentException); } }
  
  public void sendBungeeChat(String paramString1, String paramString2, boolean paramBoolean) { Iterator localIterator;
    Player localPlayer;
    IChatBaseComponent localIChatBaseComponent1;
    IChatBaseComponent localIChatBaseComponent2;
    PacketPlayOutChat localPacketPlayOutChat;
    if (paramBoolean)
    {
      for (localIterator = Bukkit.getServer().getOnlinePlayers().iterator(); localIterator.hasNext();) { localPlayer = (Player)localIterator.next();
        
        localIChatBaseComponent1 = IChatBaseComponent.ChatSerializer.a(paramString1);
        localIChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a(paramString2);
        localPacketPlayOutChat = new PacketPlayOutChat(localIChatBaseComponent1.addSibling(localIChatBaseComponent2));
        
        getHandleplayerConnection.sendPacket(localPacketPlayOutChat);
      }
      
    }
    else {
      for (localIterator = Bukkit.getServer().getOnlinePlayers().iterator(); localIterator.hasNext();) { localPlayer = (Player)localIterator.next();
        
        if (!me.clip.deluxechat.DeluxeChat.isLocal(localPlayer.getUniqueId().toString()))
        {
          localIChatBaseComponent1 = IChatBaseComponent.ChatSerializer.a(paramString1);
          localIChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a(paramString2);
          localPacketPlayOutChat = new PacketPlayOutChat(localIChatBaseComponent1.addSibling(localIChatBaseComponent2));
          
          getHandleplayerConnection.sendPacket(localPacketPlayOutChat);
        }
      }
    }
  }
}
