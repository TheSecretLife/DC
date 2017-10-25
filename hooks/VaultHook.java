package me.clip.deluxechat.hooks;

import me.clip.deluxechat.DeluxeChat;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;


















public class VaultHook
  implements PluginHook
{
  private DeluxeChat plugin;
  private Permission perms = null;
  
  private Economy econ = null;
  
  private Chat chat = null;
  
  public VaultHook(DeluxeChat paramDeluxeChat) {
    plugin = paramDeluxeChat;
  }
  
  public boolean hook() {
    setupEconomy();
    setupPerms();
    setupChat();
    return true;
  }
  
  private boolean setupEconomy()
  {
    if (plugin.getConfig().getBoolean("hooks.vault_eco"))
    {
      RegisteredServiceProvider localRegisteredServiceProvider = plugin.getServer()
        .getServicesManager().getRegistration(Economy.class);
      
      if (localRegisteredServiceProvider == null) {
        return false;
      }
      
      econ = ((Economy)localRegisteredServiceProvider.getProvider());
      
      return econ != null;
    }
    
    return false;
  }
  
  private boolean setupChat() {
    if (plugin.getConfig().getBoolean("hooks.vault_perms"))
    {
      RegisteredServiceProvider localRegisteredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
      
      if ((localRegisteredServiceProvider != null) && (localRegisteredServiceProvider.getPlugin() != null)) {
        chat = ((Chat)localRegisteredServiceProvider.getProvider());
      }
      
      return chat != null;
    }
    return false;
  }
  
  private boolean setupPerms()
  {
    if (plugin.getConfig().getBoolean("hooks.vault_perms"))
    {
      RegisteredServiceProvider localRegisteredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
      
      if ((localRegisteredServiceProvider != null) && (localRegisteredServiceProvider.getPlugin() != null)) {
        perms = ((Permission)localRegisteredServiceProvider.getProvider());
      }
      
      return perms != null;
    }
    return false;
  }
  
  public boolean useVaultChat() {
    return chat != null;
  }
  
  public boolean useVaultEcon() {
    return econ != null;
  }
  
  public boolean useVaultPerms() {
    return perms != null;
  }
  
  public String getVaultVersion() {
    return 
      Bukkit.getServer().getPluginManager().getPlugin("Vault").getDescription().getVersion();
  }
  
  public String[] getGroups(Player paramPlayer) {
    if (perms.getPlayerGroups(paramPlayer) != null) {
      return perms.getPlayerGroups(paramPlayer);
    }
    return new String[] { "" };
  }
  
  public String getMainGroup(Player paramPlayer) {
    if (perms.getPrimaryGroup(paramPlayer) != null) {
      return String.valueOf(perms.getPrimaryGroup(paramPlayer));
    }
    return "";
  }
  
  public boolean opHasPermission(Player paramPlayer, String paramString) {
    if (perms.getPrimaryGroup(paramPlayer) != null)
    {
      return perms.groupHas(paramPlayer.getWorld(), perms.getPrimaryGroup(paramPlayer), paramString);
    }
    return false;
  }
  
  public boolean hasPerm(Player paramPlayer, String paramString) {
    if (perms != null) {
      return perms.has(paramPlayer, paramString);
    }
    return paramPlayer.hasPermission(paramString);
  }
  
  public String[] getServerGroups() {
    if (perms.getGroups() != null) {
      return perms.getGroups();
    }
    return new String[] { "" };
  }
}
