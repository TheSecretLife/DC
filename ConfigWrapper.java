package me.clip.deluxechat;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigWrapper
{
  private final JavaPlugin plugin;
  private FileConfiguration config;
  private File configFile;
  private final String folderName;
  private final String fileName;
  
  public ConfigWrapper(JavaPlugin paramJavaPlugin, String paramString1, String paramString2)
  {
    plugin = paramJavaPlugin;
    folderName = paramString1;
    fileName = paramString2;
  }
  
  public void createNewFile(String paramString1, String paramString2) {
    reloadConfig();
    saveConfig();
    loadConfig(paramString2);
    
    if (paramString1 != null) {
      plugin.getLogger().info(paramString1);
    }
  }
  
  public FileConfiguration getConfig() {
    if (config == null) {
      reloadConfig();
    }
    return config;
  }
  
  public void loadConfig(String paramString) {
    config.options().header(paramString);
    config.options().copyDefaults(true);
    saveConfig();
  }
  
  public void reloadConfig() {
    if (configFile == null) {
      configFile = new File(plugin.getDataFolder() + folderName, fileName);
    }
    config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
  }
  
  public void saveConfig() {
    if ((config == null) || (configFile == null)) {
      return;
    }
    try {
      getConfig().save(configFile);
    } catch (IOException localIOException) {
      plugin.getLogger().log(java.util.logging.Level.SEVERE, "Could not save config to " + configFile, localIOException);
    }
  }
}
