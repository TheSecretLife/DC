package me.clip.deluxechat.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.clip.deluxechat.DeluxeChat;
import me.clip.deluxechat.DeluxeUtil;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.updater.SpigotUpdater;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;

public class PlayerJoinListener implements org.bukkit.event.Listener
{
  DeluxeChat plugin;
  
  public PlayerJoinListener(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
    DeluxeChat.disableSocialSpy(paramPlayerQuitEvent.getPlayer());
    DeluxeChat.removeFromPM(paramPlayerQuitEvent.getPlayer().getName());
    plugin.setGlobal(paramPlayerQuitEvent.getPlayer().getUniqueId().toString());
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent paramPlayerJoinEvent)
  {
    Player localPlayer = paramPlayerJoinEvent.getPlayer();
    
    if (localPlayer.hasPermission("deluxechat.socialspy.onjoin")) {
      DeluxeChat.enableSocialSpy(localPlayer);
    }
    
    if (DeluxeChat.useBungee())
    {
      if (DeluxeChat.globalOnJoin()) {
        plugin.setGlobal(localPlayer.getUniqueId().toString());
      } else {
        plugin.setLocal(localPlayer.getUniqueId().toString());
      }
    }
    
    if (!localPlayer.isOp()) {
      return;
    }
    
    if (plugin.getUpdater() == null) {
      return;
    }
    
    if (SpigotUpdater.updateAvailable())
    {
      DeluxeUtil.sms(localPlayer, "&8&m-----------------------------------------------------");
      
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ChatColor.translateAlternateColorCodes('&', "&aUpdate released: &f" + SpigotUpdater.getHighest()));
      localArrayList.add(ChatColor.translateAlternateColorCodes('&', "&cYou are running: &f" + plugin.getDescription().getVersion()));
      FancyMessage localFancyMessage = new FancyMessage("An update for DeluxeChat has been found! ")
        .color(ChatColor.GREEN)
        .tooltip(localArrayList)
        .link("http://www.spigotmc.org/resources/deluxechat.1277/")
        .then("Click to download!").color(ChatColor.WHITE)
        .link("http://www.spigotmc.org/resources/deluxechat.1277/");
      
      plugin.getChat().sendFancyMessage(localPlayer, localFancyMessage);
      
      DeluxeUtil.sms(localPlayer, "&8&m-----------------------------------------------------");
    }
  }
}
