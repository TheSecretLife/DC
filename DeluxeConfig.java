package me.clip.deluxechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import me.clip.deluxechat.hooks.VaultHook;
import me.clip.deluxechat.objects.DeluxeFormat;
import me.clip.deluxechat.objects.DeluxePrivateMessageFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

public class DeluxeConfig
{
  DeluxeChat plugin;
  
  public DeluxeConfig(DeluxeChat paramDeluxeChat)
  {
    plugin = paramDeluxeChat;
  }
  
  protected void loadConfigFile() {
    FileConfiguration localFileConfiguration = plugin.getConfig();
    localFileConfiguration.options().header("DeluxeChat version " + plugin.getDescription().getVersion() + " config file" + 
      "\nCreated by extended:clip" + 
      "\nlist as many format sections you want under formats:" + 
      "\neach format section requires a default template that should be modified and" + 
      "\nalso requires a unique number for the priority higher number = lower priority" + 
      "\nex: guest-100, owner-1" + 
      "\n  " + 
      "YOU MUST KEEP A default TEMPLATE! It is used when players have no other template assigned!" + 
      "\n  " + 
      "\nexample format template:" + 
      "\nformats: " + 
      "\n  default:" + 
      "\n    priority: 2147483647" + 
      "\n    channel: ''" + 
      "\n    prefix: '&8[&7Guest&8] '" + 
      "\n    name_color: '&7'" + 
      "\n    name: '%player%'" + 
      "\n    suffix: '&7> '" + 
      "\n    chat_color: '&f'" + 
      "\n    channel_tooltip:" + 
      "\n    - '&7%player% &bis a Guest'" + 
      "\n    prefix_tooltip:" + 
      "\n    - '&7%player% &bis a Guest'" + 
      "\n    name_tooltip:" + 
      "\n    - ''" + 
      "\n    suffix_tooltip:" + 
      "\n    - ''" + 
      "\n    channel_click_command: '" + 
      "\n    prefix_click_command: '/ranks" + 
      "\n    name_click_command: '/msg %player% " + 
      "\n    suffix_click_command: '" + 
      "\n  Member:" + 
      "\n    priority: 100" + 
      "\n    channel: ''" + 
      "\n    prefix: '&8[&aMember&8] '" + 
      "\n    name_color: '&7'" + 
      "\n    name: '%player%'" + 
      "\n    suffix: '&7> '" + 
      "\n    chat_color: '&f'" + 
      "\n    channel_tooltip:" + 
      "\n    - '&7%player% &bis a Member'" + 
      "\n    prefix_tooltip:" + 
      "\n    - '&7%player% &bis a Member'" + 
      "\n    name_tooltip:" + 
      "\n    - ''" + 
      "\n    suffix_tooltip:" + 
      "\n    - ''" + 
      "\n    channel_click_command: '" + 
      "\n    prefix_click_command: '/ranks" + 
      "\n    name_click_command: '/msg %player% " + 
      "\n    suffix_click_command: '");
    
    localFileConfiguration.addDefault("check_updates", Boolean.valueOf(true));
    localFileConfiguration.addDefault("bungeecord.enabled", Boolean.valueOf(false));
    localFileConfiguration.addDefault("bungeecord.servername", "&8[&cServer&8]");
    localFileConfiguration.addDefault("bungeecord.join_global", Boolean.valueOf(true));
    localFileConfiguration.addDefault("relation_placeholders_enabled", Boolean.valueOf(true));
    localFileConfiguration.addDefault("timestamp_format", "MM/dd/yy HH:mm:ss");
    localFileConfiguration.addDefault("boolean.true", "&atrue");
    localFileConfiguration.addDefault("boolean.false", "&cfalse");
    localFileConfiguration.addDefault("ops_use_group_format", Boolean.valueOf(false));
    if (localFileConfiguration.contains("hooks")) {
      localFileConfiguration.set("hooks", null);
    }
    localFileConfiguration.addDefault("chat_filter.enabled", Boolean.valueOf(false));
    localFileConfiguration.addDefault("chat_filter.ignore_case", Boolean.valueOf(true));
    localFileConfiguration.addDefault("chat_filter.list", Arrays.asList(new String[] { ".; ", "fuck;fuck" }));
    if (localFileConfiguration.contains("chat_url.tooltip")) {
      localFileConfiguration.set("chat_url.tooltip", null);
    }
    localFileConfiguration.addDefault("private_message.enabled", Boolean.valueOf(true));
    localFileConfiguration.addDefault("private_message.bungeecord", Boolean.valueOf(false));
    localFileConfiguration.addDefault("private_message_formats.to_sender.format", "&7you &e-> &7%recipient% &7:");
    localFileConfiguration.addDefault("private_message_formats.to_sender.tooltip", Arrays.asList(new String[] { "%player_server%" }));
    localFileConfiguration.addDefault("private_message_formats.to_sender.click_command", "/r ");
    localFileConfiguration.addDefault("private_message_formats.to_sender.chat_color", "&f");
    localFileConfiguration.addDefault("private_message_formats.to_recipient.format", "&7%player% &e-> &7you &7:");
    localFileConfiguration.addDefault("private_message_formats.to_recipient.tooltip", Arrays.asList(new String[] { "%player_server%" }));
    localFileConfiguration.addDefault("private_message_formats.to_recipient.click_command", "/r ");
    localFileConfiguration.addDefault("private_message_formats.to_recipient.chat_color", "&f");
    localFileConfiguration.addDefault("private_message_formats.social_spy", "&8[&cspy&8] &f%player% &e-> &f%recipient%&7:");
    
    localFileConfiguration.addDefault("formats.default.priority", Integer.valueOf(Integer.MAX_VALUE));
    localFileConfiguration.addDefault("formats.default.channel", "");
    localFileConfiguration.addDefault("formats.default.prefix", "&7[%vault_groupprefix%&7] ");
    localFileConfiguration.addDefault("formats.default.name_color", "&b");
    localFileConfiguration.addDefault("formats.default.name", "%player%");
    localFileConfiguration.addDefault("formats.default.suffix", "&7> ");
    localFileConfiguration.addDefault("formats.default.chat_color", "&f");
    localFileConfiguration.addDefault("formats.default.channel_tooltip", Arrays.asList(new String[] { "" }));
    localFileConfiguration.addDefault("formats.default.prefix_tooltip", Arrays.asList(new String[] { "%player%", "&bRank: %vault_group%" }));
    localFileConfiguration.addDefault("formats.default.name_tooltip", Arrays.asList(new String[] { "" }));
    localFileConfiguration.addDefault("formats.default.suffix_tooltip", Arrays.asList(new String[] { "" }));
    localFileConfiguration.addDefault("formats.default.channel_click_command", "/ranks");
    localFileConfiguration.addDefault("formats.default.prefix_click_command", "/ranks");
    localFileConfiguration.addDefault("formats.default.name_click_command", "/msg %player% ");
    localFileConfiguration.addDefault("formats.default.suffix_click_command", "");
    
    localFileConfiguration.options().copyDefaults(true);
    plugin.saveConfig();
  }
  
  protected void loadPMFormats() {
    FileConfiguration localFileConfiguration = plugin.getConfig();
    DeluxePrivateMessageFormat localDeluxePrivateMessageFormat1 = new DeluxePrivateMessageFormat(localFileConfiguration.getString("private_message_formats.to_sender.format"), 
      localFileConfiguration.getStringList("private_message_formats.to_sender.tooltip"), 
      localFileConfiguration.getString("private_message_formats.to_sender.click_command"), 
      localFileConfiguration.getString("private_message_formats.to_sender.chat_color"));
    DeluxePrivateMessageFormat localDeluxePrivateMessageFormat2 = new DeluxePrivateMessageFormat(localFileConfiguration.getString("private_message_formats.to_recipient.format"), 
      localFileConfiguration.getStringList("private_message_formats.to_recipient.tooltip"), 
      localFileConfiguration.getString("private_message_formats.to_recipient.click_command"), 
      localFileConfiguration.getString("private_message_formats.to_recipient.chat_color"));
    
    DeluxeChat.toSenderPmFormat = localDeluxePrivateMessageFormat1;
    DeluxeChat.toRecipientPmFormat = localDeluxePrivateMessageFormat2;
  }
  
  protected boolean blacklistIgnoreCase() {
    return plugin.getConfig().getBoolean("chat_filter.ignore_case");
  }
  
  protected boolean bungeePMEnabled() {
    return plugin.getConfig().getBoolean("private_message.bungeecord");
  }
  
  protected String socialSpyFormat() {
    return plugin.getConfig().getString("private_message_formats.social_spy");
  }
  
  protected String booleanTrue() {
    return plugin.getConfig().getString("boolean.true");
  }
  
  protected String booleanFalse() {
    return plugin.getConfig().getString("boolean.false");
  }
  
  protected String timestampFormat() {
    return plugin.getConfig().getString("timestamp_format");
  }
  
  protected boolean useRelationPlaceholders() {
    return plugin.getConfig().getBoolean("relation_placeholders_enabled");
  }
  
  protected boolean joinGlobal() {
    return plugin.getConfig().getBoolean("bungeecord.join_global");
  }
  
  protected boolean useBlacklist() {
    return plugin.getConfig().getBoolean("chat_filter.enabled");
  }
  
  protected boolean opsUseGroupFormat() {
    return plugin.getConfig().getBoolean("ops_use_group_format");
  }
  
  protected String serverName() {
    return plugin.getConfig().getString("bungeecord.servername");
  }
  
  protected int loadBlacklist()
  {
    DeluxeChat.blacklist = new HashMap();
    
    List localList = plugin.getConfig().getStringList("chat_filter.list");
    
    if ((localList == null) || (localList.isEmpty())) {
      return 0;
    }
    
    for (String str1 : localList) {
      if (!str1.contains(";")) {
        DeluxeChat.blacklist.put(str1, str1);
      }
      else {
        String[] arrayOfString = str1.split(";");
        String str2 = arrayOfString[0];
        String str3 = arrayOfString[1];
        DeluxeChat.blacklist.put(str2, str3);
      }
    }
    

    return DeluxeChat.blacklist.size();
  }
  
  protected int loadFormats()
  {
    DeluxeChat.formats = new java.util.TreeMap();
    
    FileConfiguration localFileConfiguration = plugin.getConfig();
    
    if ((localFileConfiguration.contains("formats")) && (localFileConfiguration.isConfigurationSection("formats")))
    {
      Set localSet = localFileConfiguration.getConfigurationSection("formats")
        .getKeys(false);
      
      int i = 1;
      
      for (String str : localSet)
      {
        if (!localFileConfiguration.contains("formats." + str + ".channel")) {
          localFileConfiguration.set("formats." + str + ".channel", "");
          i = 0;
        }
        if (!localFileConfiguration.contains("formats." + str + ".channel_tooltip")) {
          localFileConfiguration.set("formats." + str + ".channel_tooltip", new ArrayList());
          i = 0;
        }
        if (!localFileConfiguration.contains("formats." + str + ".channel_click_command")) {
          localFileConfiguration.set("formats." + str + ".channel_click_command", "[URL]http://www.google.com");
          i = 0;
        }
        
        if (i == 0) {
          plugin.saveConfig();
          plugin.reloadConfig();
        }
        

        DeluxeFormat localDeluxeFormat = new DeluxeFormat(str, localFileConfiguration.getInt("formats." + str + 
          ".priority"));
        
        localDeluxeFormat.setChannel(ChatColor.translateAlternateColorCodes('&', 
          localFileConfiguration.getString("formats." + str + ".channel")));
        localDeluxeFormat.setPrefix(ChatColor.translateAlternateColorCodes('&', 
          localFileConfiguration.getString("formats." + str + ".prefix")));
        localDeluxeFormat.setNameColor(ChatColor.translateAlternateColorCodes('&', 
          localFileConfiguration.getString("formats." + str + ".name_color")));
        localDeluxeFormat.setName(ChatColor.translateAlternateColorCodes('&', 
          localFileConfiguration.getString("formats." + str + ".name")));
        localDeluxeFormat.setSuffix(ChatColor.translateAlternateColorCodes('&', 
          localFileConfiguration.getString("formats." + str + ".suffix")));
        localDeluxeFormat.setChatColor(ChatColor.translateAlternateColorCodes('&', 
          localFileConfiguration.getString("formats." + str + ".chat_color")));
        
        if ((localFileConfiguration.getStringList("formats." + str + ".channel_tooltip") == null) || 
        
          (localFileConfiguration.getStringList("formats." + str + ".channel_tooltip").isEmpty())) {
          localDeluxeFormat.setShowChannelTooltip(false);
        } else {
          localDeluxeFormat.setShowChannelTooltip(true);
          localDeluxeFormat.setChannelTooltip(localFileConfiguration.getStringList("formats." + str + 
            ".channel_tooltip"));
        }
        
        if ((localFileConfiguration.getStringList("formats." + str + ".prefix_tooltip") == null) || 
        
          (localFileConfiguration.getStringList("formats." + str + ".prefix_tooltip").isEmpty())) {
          localDeluxeFormat.setShowPreTooltip(false);
        } else {
          localDeluxeFormat.setShowPreTooltip(true);
          localDeluxeFormat.setPrefixTooltip(localFileConfiguration.getStringList("formats." + str + 
            ".prefix_tooltip"));
        }
        
        if ((localFileConfiguration.getStringList("formats." + str + ".name_tooltip") == null) || 
        
          (localFileConfiguration.getStringList("formats." + str + ".name_tooltip").isEmpty())) {
          localDeluxeFormat.setShowNameTooltip(false);
        } else {
          localDeluxeFormat.setShowNameTooltip(true);
          localDeluxeFormat.setNameTooltip(localFileConfiguration.getStringList("formats." + str + 
            ".name_tooltip"));
        }
        
        if ((localFileConfiguration.getStringList("formats." + str + ".suffix_tooltip") == null) || 
        
          (localFileConfiguration.getStringList("formats." + str + ".suffix_tooltip").isEmpty())) {
          localDeluxeFormat.setShowSuffixTooltip(false);
        } else {
          localDeluxeFormat.setShowSuffixTooltip(true);
          localDeluxeFormat.setSuffixTooltip(localFileConfiguration.getStringList("formats." + str + 
            ".suffix_tooltip"));
        }
        
        if ((localFileConfiguration.getString("formats." + str + ".channel_click_command") == null) || 
        

          (localFileConfiguration.getString("formats." + str + ".channel_click_command").isEmpty())) {
          localDeluxeFormat.setUseChannelClick(false);
        } else {
          localDeluxeFormat.setUseChannelClick(true);
          localDeluxeFormat.setChannelClickCommand(localFileConfiguration.getString("formats." + str + 
            ".channel_click_command"));
        }
        
        if ((localFileConfiguration.getString("formats." + str + ".prefix_click_command") == null) || 
        
          (localFileConfiguration.getString("formats." + str + ".prefix_click_command").isEmpty())) {
          localDeluxeFormat.setUsePreClick(false);
        } else {
          localDeluxeFormat.setUsePreClick(true);
          localDeluxeFormat.setPreClickCmd(localFileConfiguration.getString("formats." + str + 
            ".prefix_click_command"));
        }
        
        if ((localFileConfiguration.getString("formats." + str + ".name_click_command") == null) || 
        
          (localFileConfiguration.getString("formats." + str + ".name_click_command").isEmpty())) {
          localDeluxeFormat.setUseNameClick(false);
        } else {
          localDeluxeFormat.setUseNameClick(true);
          localDeluxeFormat.setNameClickCmd(localFileConfiguration.getString("formats." + str + 
            ".name_click_command"));
        }
        
        if ((localFileConfiguration.getString("formats." + str + ".suffix_click_command") == null) || 
        
          (localFileConfiguration.getString("formats." + str + ".suffix_click_command").isEmpty())) {
          localDeluxeFormat.setUseSuffixClick(false);
        } else {
          localDeluxeFormat.setUseSuffixClick(true);
          localDeluxeFormat.setSuffixClickCmd(localFileConfiguration.getString("formats." + str + 
            ".suffix_click_command"));
        }
        
        if (Bukkit.getPluginManager().getPermission("chatformat." + localDeluxeFormat.getIdentifier()) == null) {
          try
          {
            Permission localPermission = new Permission("chatformat." + localDeluxeFormat.getIdentifier(), PermissionDefault.FALSE);
            Bukkit.getPluginManager().addPermission(localPermission);
          }
          catch (Exception localException) {}
        }
        
        DeluxeChat.formats.put(Integer.valueOf(localDeluxeFormat.getIndex()), localDeluxeFormat);
      }
    }
    
    return DeluxeChat.getFormats().size();
  }
  


  protected void rc()
  {
    plugin.reloadConfig();
    plugin.saveConfig();
    
    DeluxeChat.useBlacklist = useBlacklist();
    DeluxeChat.blacklistIgnoreCase = blacklistIgnoreCase();
    DeluxeChat.serverName = serverName();
    DeluxeChat.joinGlobal = joinGlobal();
    DeluxeChat.useRelationPlaceholders = useRelationPlaceholders();
    DeluxeChat.booleanTrue = booleanTrue();
    DeluxeChat.booleanFalse = booleanFalse();
    loadPMFormats();
    DeluxeChat.socialSpyFormat = socialSpyFormat();
    if ((plugin.vault != null) && (plugin.vault.useVaultPerms())) {
      DeluxeChat.opsUseGroupFormat = opsUseGroupFormat();
    } else {
      DeluxeChat.opsUseGroupFormat = false;
    }
    
    plugin.getLog().info(loadFormats() + " formats loaded!");
    
    if (DeluxeChat.useBlacklist) {
      plugin.getLog().info(loadBlacklist() + " entries added to the chat_filter!");
    }
  }
}
