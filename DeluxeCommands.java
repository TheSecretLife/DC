package me.clip.deluxechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.clip.deluxechat.compatibility.CompatibilityManager;
import me.clip.deluxechat.events.DeluxeChatJSONEvent;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.objects.DeluxeFormat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

public class DeluxeCommands implements CommandExecutor
{
  DeluxeChat plugin;
  
  public DeluxeCommands(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  

  public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString)
  {
    if (!(paramCommandSender instanceof Player)) {
      if (paramArrayOfString.length == 0)
      {
        DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
        DeluxeUtil.sms(paramCommandSender, "DeluxeChat &7Version: &f" + plugin.getDescription().getVersion());
        DeluxeUtil.sms(paramCommandSender, "&7Created by: &cbxtended_clip");
        DeluxeUtil.sms(paramCommandSender, "&c/dchat reload &7- &fReload DeluxeChat");
        DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
        return true;
      }
      if (paramArrayOfString.length > 0)
      {
        if (paramArrayOfString[0].equalsIgnoreCase("reload"))
        {
          plugin.c.rc();
          
          DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
          DeluxeUtil.sms(paramCommandSender, "&fDeluxeChat &asuccessfully reloaded!");
          DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
          return true;
        }
        

        DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
        DeluxeUtil.sms(paramCommandSender, "&cIncorrect command usage! Use &7/dchat");
        DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
        return true;
      }
      
      return true;
    }
    
    Player localPlayer = (Player)paramCommandSender;
    
    if (!localPlayer.hasPermission("deluxechat.admin")) {
      DeluxeUtil.sms(paramCommandSender, "&cYou don't have permission to do that!");
      return true; }
    Object localObject1;
    Object localObject2;
    Object localObject3; Object localObject4; Object localObject5; Object localObject6; if (paramArrayOfString.length == 0) {
      DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
      localObject1 = new ArrayList();
      ((List)localObject1).add(ChatColor.translateAlternateColorCodes('&', "&7Version: &f" + plugin.getDescription().getVersion()));
      ((List)localObject1).add(ChatColor.translateAlternateColorCodes('&', "&7Created by: &cbxtended_clip"));
      localObject2 = new FancyMessage("DeluxeChat")
        .color(ChatColor.AQUA)
        .tooltip((Iterable)localObject1)
        .then(" help menu").color(ChatColor.WHITE);
      
      plugin.getChat().sendFancyMessage(paramCommandSender, (FancyMessage)localObject2);
      
      localObject3 = new ArrayList();
      ((List)localObject3).add(ChatColor.translateAlternateColorCodes('&', "&bClick to reload!"));
      
      localObject4 = new FancyMessage("/dchat reload (true/false)")
        .color(ChatColor.AQUA)
        .tooltip((Iterable)localObject3)
        .suggest("/dchat reload true")
        .then(" - Reload the config.yml and optionally update your placeholder hooks").color(ChatColor.WHITE);
      
      plugin.getChat().sendFancyMessage(paramCommandSender, (FancyMessage)localObject4);
      
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ChatColor.translateAlternateColorCodes('&', "&bClick to test a format!"));
      
      localObject5 = new FancyMessage("/dchat test")
        .color(ChatColor.AQUA)
        .tooltip(localArrayList)
        .suggest("/dchat test <format> <message>")
        .then(" - test a loaded format!").color(ChatColor.WHITE);
      
      plugin.getChat().sendFancyMessage(paramCommandSender, (FancyMessage)localObject5);
      
      localObject6 = new ArrayList();
      ((List)localObject6).add(ChatColor.translateAlternateColorCodes('&', "&bClick to list loaded format names!"));
      
      FancyMessage localFancyMessage = new FancyMessage("/dchat list")
        .color(ChatColor.AQUA)
        .tooltip((Iterable)localObject6)
        .suggest("/dchat list")
        .then(" - list loaded format names!").color(ChatColor.WHITE);
      
      plugin.getChat().sendFancyMessage(paramCommandSender, localFancyMessage);
      
      DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
      
      return true;
    }
    if (paramArrayOfString.length > 0) {
      if (paramArrayOfString[0].equalsIgnoreCase("reload"))
      {
        plugin.c.rc();
        
        DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
        localObject1 = new ArrayList();
        ((List)localObject1).add(ChatColor.translateAlternateColorCodes('&', "&f" + DeluxeChat.getFormats().size() + " &bformats successfully loaded!"));
        localObject2 = new FancyMessage("Configuration")
          .color(ChatColor.WHITE)
          .tooltip((Iterable)localObject1)
          .then(" successfully reloaded!").color(ChatColor.GREEN);
        
        plugin.getChat().sendFancyMessage(paramCommandSender, (FancyMessage)localObject2);
        
        if (DeluxeChat.useBlacklist) {
          localObject3 = new ArrayList();
          ((List)localObject3).add(ChatColor.translateAlternateColorCodes('&', "&f" + DeluxeChat.blacklist.size() + " &7words/characters are now being filtered!"));
          
          localObject4 = new FancyMessage("Chat filter")
            .color(ChatColor.WHITE)
            .tooltip((Iterable)localObject3)
            .then(" is enabled!").color(ChatColor.GREEN);
          
          plugin.getChat().sendFancyMessage(paramCommandSender, (FancyMessage)localObject4);
        }
        DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
        return true; }
      if (paramArrayOfString[0].equalsIgnoreCase("list"))
      {
        localObject1 = DeluxeChat.getFormats();
        
        if ((localObject1 == null) || (((Map)localObject1).isEmpty())) {
          DeluxeUtil.sms(paramCommandSender, "&cThere are no formats loaded!");
          return true;
        }
        
        localObject2 = new ArrayList();
        
        localObject3 = ((Map)localObject1).values().iterator();
        
        while (((Iterator)localObject3).hasNext()) {
          localObject4 = ((DeluxeFormat)((Iterator)localObject3).next()).getIdentifier();
          ((List)localObject2).add(localObject4);
        }
        
        DeluxeUtil.sms(paramCommandSender, "&7Loaded formats &e(&f" + ((List)localObject2).size() + "&e)&7: " + localObject2.toString());
        return true;
      }
      if (paramArrayOfString[0].equalsIgnoreCase("test"))
      {
        if (paramArrayOfString.length < 3) {
          DeluxeUtil.sms(paramCommandSender, "&cIncorrect usage! &7/dchat test <formatname> <message>");
          return true;
        }
        
        localObject1 = paramArrayOfString[1];
        
        localObject2 = plugin.getTestFormat((String)localObject1);
        
        if (localObject2 == null) {
          DeluxeUtil.sms(paramCommandSender, "&cThe format &f" + (String)localObject1 + " &cis not a loaded format!");
          return true;
        }
        
        localObject3 = plugin.getFancyChatFormat(localPlayer, (DeluxeFormat)localObject2);
        
        if (localObject3 == null) {
          DeluxeUtil.sms(paramCommandSender, "&cThere was an error getting the chat format: &f" + (String)localObject1);
          return true;
        }
        
        localObject4 = ((FancyMessage)localObject3).getLastColor() + ((FancyMessage)localObject3).getChatColor() + StringUtils.join(Arrays.copyOfRange(paramArrayOfString, 2, paramArrayOfString.length), " ");
        
        localObject4 = ChatColor.translateAlternateColorCodes('&', (String)localObject4);
        
        boolean bool = DeluxeChat.useRelationPlaceholders();
        
        localObject5 = plugin.getChat().convertMsg(localPlayer, (String)localObject4);
        
        if (bool)
        {
          localObject6 = new DeluxeChatJSONEvent(localPlayer, ((FancyMessage)localObject3).toJSONString(), (String)localObject5, (String)localObject4);
          
          org.bukkit.Bukkit.getPluginManager().callEvent((Event)localObject6);
          
          if ((((DeluxeChatJSONEvent)localObject6).getJSONFormat() == null) || 
            (((DeluxeChatJSONEvent)localObject6).getJSONChatMessage() == null) || 
            (((DeluxeChatJSONEvent)localObject6).getJSONFormat().isEmpty())) {
            DeluxeUtil.sms(paramCommandSender, "&cThere was an error getting message information from the DeluxeChatJSONEvent!");
            return true;
          }
          
          plugin.getChat().sendDirectChat(localPlayer, ((DeluxeChatJSONEvent)localObject6).getJSONFormat(), ((DeluxeChatJSONEvent)localObject6).getJSONChatMessage(), localPlayer, localPlayer);
        }
        else
        {
          localObject6 = new HashSet();
          ((Set)localObject6).add(localPlayer);
          
          plugin.getChat().sendDeluxeChat(localPlayer, ((FancyMessage)localObject3).toJSONString(), (String)localObject5, (Set)localObject6);
        }
        
        return true;
      }
      
      DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
      localObject1 = new ArrayList();
      ((List)localObject1).add(ChatColor.translateAlternateColorCodes('&', "&cIncorrect command argument!"));
      ((List)localObject1).add(ChatColor.translateAlternateColorCodes('&', "&fUse &b/dchat"));
      localObject2 = new FancyMessage("Incorrect usage!")
        .color(ChatColor.RED)
        .tooltip((Iterable)localObject1)
        .suggest("/dchat");
      
      plugin.getChat().sendFancyMessage(paramCommandSender, (FancyMessage)localObject2);
      
      DeluxeUtil.sms(paramCommandSender, "&8&m-----------------------------------------------------");
      return true;
    }
    

    return true;
  }
}
