package me.clip.deluxechat;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;
import me.clip.deluxechat.bungee.BungeeMessageListener;
import me.clip.deluxechat.compatibility.CompatibilityManager;
import me.clip.deluxechat.compatibility.Spigot_1_10_R1_Chat;
import me.clip.deluxechat.compatibility.Spigot_1_12_R1_Chat;
import me.clip.deluxechat.compatibility.Spigot_1_8_R3_Chat;
import me.clip.deluxechat.compatibility.Spigot_1_9_R1_Chat;
import me.clip.deluxechat.compatibility.Spigot_1_9_R2_Chat;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.hooks.EssentialsHook;
import me.clip.deluxechat.hooks.VanishNoPacketHook;
import me.clip.deluxechat.hooks.VaultHook;
import me.clip.deluxechat.listeners.ChatToPlayerListener;
import me.clip.deluxechat.listeners.PlayerJoinListener;
import me.clip.deluxechat.listeners.TownyChatListener;
import me.clip.deluxechat.messaging.PrivateMessageType;
import me.clip.deluxechat.objects.DeluxeFormat;
import me.clip.deluxechat.objects.DeluxePrivateMessageFormat;
import me.clip.deluxechat.updater.SpigotUpdater;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class DeluxeChat extends JavaPlugin
{
  private Logger log = getLogger();
  
  protected static Map<Integer, DeluxeFormat> formats = new TreeMap();
  protected static List<String> localPlayers;
  protected static Map<String, String> blacklist = new HashMap();
  protected static boolean blacklistIgnoreCase;
  protected static boolean useBlacklist;
  protected static boolean opsUseGroupFormat;
  protected static boolean joinGlobal;
  protected static String serverName;
  protected static String booleanTrue;
  protected static String booleanFalse;
  protected static Map<String, String> inPrivateChat = new HashMap();
  protected static List<String> socialSpy = new ArrayList();
  
  protected static DeluxePrivateMessageFormat toSenderPmFormat;
  protected static DeluxePrivateMessageFormat toRecipientPmFormat;
  protected static String socialSpyFormat;
  protected static boolean useRelationPlaceholders;
  protected static SimpleDateFormat timestampFormat;
  private TownyChatListener townychat;
  protected EssentialsHook essentials;
  protected VanishNoPacketHook vanishNoPacket = null;
  
  protected VaultHook vault;
  protected DeluxeConfig c = new DeluxeConfig(this);
  
  protected DeluxeCommands commands = new DeluxeCommands(this);
  
  private SpigotUpdater updater = null;
  
  private CompatibilityManager chat;
  
  private static BungeeMessageListener bungee;
  
  private static boolean bungeePM;
  
  private static DeluxeChat instance;
  
  private ConfigWrapper messages = new ConfigWrapper(this, "", "messages.yml");
  
  public DeluxeChat() {}
  
  public void onEnable() { 
    if (setupCompatibility())
    {
      if (!hookPlaceholderAPI()) {
        System.out.println("----------------------------");
        System.out.println("     DeluxeChat ERROR");
        System.out.println(" ");
        System.out.println("As of DeluxeChat 1.12.0, PlaceholderAPI is now required to be installed on your server.");
        System.out.println("PlaceholderAPI now handles all parsing of placeholders within DeluxeChat.");
        System.out.println(" ");
        System.out.println("Download PlaceholderAPI at https://www.spigotmc.org/resources/placeholderapi.6245/");
        System.out.println("----------------------------");
        throw new RuntimeException("Failed to detect PlaceholderAPI!");
      }
      instance = this;
      c.loadConfigFile();
      getLog().info(c.loadFormats() + " formats loaded!");
      messages.createNewFile("Loading DeluxeChat messages.yml", "DeluxeChat language file\nYou can edit all the messages here!\nYou must restart for changes to take affect when editing this file!");
      loadMessages();
      blacklistIgnoreCase = c.blacklistIgnoreCase();
      useBlacklist = c.useBlacklist();
      joinGlobal = true;
      booleanTrue = c.booleanTrue();
      booleanFalse = c.booleanFalse();
      useRelationPlaceholders = c.useRelationPlaceholders();
      socialSpyFormat = c.socialSpyFormat();
      c.loadPMFormats();
      try {
        timestampFormat = new SimpleDateFormat(c.timestampFormat());
      } catch (Exception localException) {
        timestampFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
      }
      if (useBlacklist) {
        getLog().info(c.loadBlacklist() + " entries added to the chat_filter!");
      }
      if ((Bukkit.getPluginManager().isPluginEnabled("Towny")) && (Bukkit.getPluginManager().isPluginEnabled("TownyChat"))) {
        townychat = new TownyChatListener(this);
        townychat.addPlaceholders();
        Bukkit.getServer().getPluginManager().registerEvents(townychat, this);
        getLog().info("TownyChat integration is enabled!");
      } else {
        Bukkit.getServer().getPluginManager().registerEvents(new me.clip.deluxechat.listeners.AsyncPlayerChatListener(this), this);
      }
      
      if (useRelationPlaceholders) {
        Bukkit.getServer().getPluginManager().registerEvents(new ChatToPlayerListener(), this);
      }
      


      if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
        essentials = new EssentialsHook(this);
        if (!essentials.hook()) {
          essentials = null;
        }
      }
      
      if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
        vault = new VaultHook(this);
        vault.hook();
      }
      
      if (Bukkit.getPluginManager().isPluginEnabled("VanishNoPacket")) {
        vanishNoPacket = new VanishNoPacketHook();
        vanishNoPacket.hook();
      }
      
      Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
      
      serverName = getConfig().getString("bungeecord.servername");
      
      if (getConfig().getBoolean("bungeecord.enabled")) {
        bungeePM = c.bungeePMEnabled();
        joinGlobal = c.joinGlobal();
        localPlayers = new ArrayList();
        
        getLog().info("Bungee integration has been enabled!");
        getLog().info("Remember DeluxeChat.jar must be running on your bungee proxy server also for integration to work properly!!");
        bungee = new BungeeMessageListener(this);
        getCommand("gtoggle").setExecutor(new ToggleCommand(this));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "DeluxeChat");
        getServer().getMessenger().registerIncomingPluginChannel(this, "DeluxeChat", bungee);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "DeluxeChatPM");
        getServer().getMessenger().registerIncomingPluginChannel(this, "DeluxeChatPM", bungee);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "DeluxeChatSocialSpy");
        getServer().getMessenger().registerIncomingPluginChannel(this, "DeluxeChatSocialSpy", bungee);
      } else {
        bungeePM = false;
      }
      
      getCommand("dchat").setExecutor(commands);
      
      if (getConfig().getBoolean("private_message.enabled")) {
        getCommand("msg").setExecutor(new MessageCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this));
        getCommand("socialspy").setExecutor(new SocialSpyCommand(this));
      }
      
      if (getConfig().getBoolean("check_updates")) {
        updater = new SpigotUpdater(this);
        getUpdater().checkUpdates();
        if (SpigotUpdater.updateAvailable()) {
          System.out.println("----------------------------");
          System.out.println("     DeluxeChat Updater");
          System.out.println(" ");
          System.out.println("An update for DeluxeChat has been found!");
          System.out.println("DeluxeChat " + SpigotUpdater.getHighest());
          System.out.println("You are running " + getDescription().getVersion());
          System.out.println(" ");
          System.out.println("Download at http://www.spigotmc.org/resources/deluxechat.1277/");
          System.out.println("----------------------------");
        } else {
          System.out.println("----------------------------");
          System.out.println("     DeluxeChat Updater");
          System.out.println(" ");
          System.out.println("You are running " + getDescription().getVersion());
          System.out.println("The latest version");
          System.out.println("of DeluxeChat!");
          System.out.println(" ");
          System.out.println("----------------------------");
        }
      }
    }
    else {
      System.out.println("----------------------------");
      getLog().warning("DeluxeChat version: " + getDescription().getVersion());
      System.out.println("");
      getLog().warning("This version of DeluxeChat is not compatible with your server version!");
      System.out.println("");
      getLog().warning("Find a specific version of DeluxeChat here:");
      getLog().warning("https://www.spigotmc.org/resources/deluxechat.1277/history");
      System.out.println("----------------------------");
      Bukkit.getPluginManager().disablePlugin(this);
    }
  }
  

  public void onDisable()
  {
    if (townychat != null) {
      townychat.clear();
    }
    
    toSenderPmFormat = null;
    toRecipientPmFormat = null;
    blacklist = null;
    formats = null;
    localPlayers = null;
    inPrivateChat = null;
    socialSpy = null;
    instance = null;
  }
  
  private void loadMessages() {
    Lang.setFile(messages.getConfig());
    
    for (Lang localLang : Lang.values()) {
      messages.getConfig().addDefault(localLang.getPath(), localLang.getDefault());
    }
    
    messages.getConfig().options().copyDefaults(true);
    messages.saveConfig();
  }
  
  public CompatibilityManager getChat() {
    return chat;
  }
  
  public SpigotUpdater getUpdater() {
    return updater;
  }
  
  public static Map<Integer, DeluxeFormat> getFormats() {
    return formats;
  }
  
  private boolean hookPlaceholderAPI() {
    return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
  }
  

  private boolean setupCompatibility()
  {
    String str;
    try
    {
      str = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      return false;
    }
    
    if (str.equals("v1_8_R3"))
    {
      chat = new Spigot_1_8_R3_Chat();
    } else if (str.equals("v1_9_R1"))
    {
      chat = new Spigot_1_9_R1_Chat();
    } else if (str.equals("v1_9_R2"))
    {
      chat = new Spigot_1_9_R2_Chat();
    } else if (str.equals("v1_10_R1"))
    {
      chat = new Spigot_1_10_R1_Chat();
    } else if (str.equals("v1_11_R1"))
    {
      chat = new me.clip.deluxechat.compatibility.Spigot_1_11_R1_Chat();
    } else if (str.equals("v1_12_R1"))
    {
      chat = new Spigot_1_12_R1_Chat();
    }
    
    return chat != null;
  }
  
  public DeluxeFormat getTestFormat(String paramString)
  {
    Iterator localIterator = getFormats().values().iterator();
    
    while (localIterator.hasNext())
    {
      DeluxeFormat localDeluxeFormat = (DeluxeFormat)localIterator.next();
      
      if (localDeluxeFormat.getIdentifier().equalsIgnoreCase(paramString)) {
        return DeluxeFormat.newInstance(localDeluxeFormat);
      }
    }
    
    return null;
  }
  
  public DeluxeFormat getPlayerFormat(Player paramPlayer)
  {
    Object localObject = null;
    Iterator localIterator;
    DeluxeFormat localDeluxeFormat; if ((paramPlayer.isOp()) && (opsUseGroupFormat) && (vault != null) && (vault.useVaultPerms()))
    {
      localIterator = getFormats().values().iterator();
      
      while (localIterator.hasNext())
      {
        localDeluxeFormat = (DeluxeFormat)localIterator.next();
        
        if (vault.opHasPermission(paramPlayer, "chatformat." + localDeluxeFormat.getIdentifier()))
        {
          localObject = localDeluxeFormat;
          
          break;
        }
      }
    }
    else
    {
      localIterator = getFormats().values().iterator();
      
      while (localIterator.hasNext())
      {
        localDeluxeFormat = (DeluxeFormat)localIterator.next();
        
        if (paramPlayer.hasPermission("chatformat." + localDeluxeFormat.getIdentifier()))
        {
          localObject = localDeluxeFormat;
          
          break;
        }
      }
    }
    
    if (localObject == null)
    {
      localObject = new DeluxeFormat("default", Integer.MAX_VALUE);
      ((DeluxeFormat)localObject).setChannel("");
      ((DeluxeFormat)localObject).setPrefix("");
      ((DeluxeFormat)localObject).setNameColor("");
      ((DeluxeFormat)localObject).setName("%player_name%");
      ((DeluxeFormat)localObject).setSuffix(" &8> &r");
      ((DeluxeFormat)localObject).setChatColor("");
      ((DeluxeFormat)localObject).setShowChannelTooltip(false);
      ((DeluxeFormat)localObject).setChannelTooltip(null);
      ((DeluxeFormat)localObject).setShowPreTooltip(false);
      ((DeluxeFormat)localObject).setPrefixTooltip(null);
      ((DeluxeFormat)localObject).setShowNameTooltip(false);
      ((DeluxeFormat)localObject).setNameTooltip(null);
      ((DeluxeFormat)localObject).setShowSuffixTooltip(false);
      ((DeluxeFormat)localObject).setSuffixTooltip(null);
      ((DeluxeFormat)localObject).setUsePreClick(false);
      ((DeluxeFormat)localObject).setPreClickCmd(null);
      ((DeluxeFormat)localObject).setUseNameClick(true);
      ((DeluxeFormat)localObject).setNameClickCmd("/msg %player_name% ");
      ((DeluxeFormat)localObject).setUseSuffixClick(false);
      ((DeluxeFormat)localObject).setSuffixClickCmd(null);
    }
    
    return localObject;
  }
  
  public boolean isMuted(Player paramPlayer)
  {
    boolean bool = false;
    




    if (essentials != null) {
      bool = essentials.isMuted(paramPlayer);
    }
    
    return bool;
  }
  
  public FancyMessage getBungeePrivateMessageFormat(Player paramPlayer, DeluxePrivateMessageFormat paramDeluxePrivateMessageFormat)
  {
    if (paramDeluxePrivateMessageFormat == null) {
      return null;
    }
    
    String str1 = paramDeluxePrivateMessageFormat.getFormat();
    
    str1 = PlaceholderAPI.setPlaceholders(paramPlayer, str1);
    
    str1 = ChatColor.translateAlternateColorCodes('&', str1);
    
    FancyMessage localFancyMessage = new FancyMessage(str1);
    
    String str2 = DeluxeUtil.getLastColor(str1);
    
    localFancyMessage.setLastColor(str2);
    
    localFancyMessage.setChatColor(ChatColor.translateAlternateColorCodes('&', paramDeluxePrivateMessageFormat.getChatColor()));
    
    if ((paramDeluxePrivateMessageFormat.getTooltip() != null) && (!paramDeluxePrivateMessageFormat.getTooltip().isEmpty()))
    {
      localFancyMessage.tooltip(paramDeluxePrivateMessageFormat.getTooltip());
    }
    
    if (paramDeluxePrivateMessageFormat.getClickAction() != null)
    {
      String str3 = paramDeluxePrivateMessageFormat.getClickAction();
      
      if (str3.startsWith("[EXECUTE]"))
      {
        str3 = str3.replace("[EXECUTE]", "");
        localFancyMessage.command(str3);
      } else if (str3.startsWith("[URL]"))
      {
        str3 = str3.replace("[URL]", "");
        
        if ((!str3.startsWith("http://")) && 
          (!str3.startsWith("https://"))) {
          str3 = "http://" + str3;
        }
        

        localFancyMessage.link(str3);
      } else {
        localFancyMessage.suggest(str3);
      }
    }
    localFancyMessage.then(ChatColor.translateAlternateColorCodes('&', "&f"));
    return localFancyMessage;
  }
  
  public FancyMessage getPrivateMessageFormat(DeluxePrivateMessageFormat paramDeluxePrivateMessageFormat)
  {
    if (paramDeluxePrivateMessageFormat == null) {
      return null;
    }
    
    String str1 = paramDeluxePrivateMessageFormat.getFormat();
    
    str1 = ChatColor.translateAlternateColorCodes('&', str1);
    
    FancyMessage localFancyMessage = new FancyMessage(str1);
    
    String str2 = DeluxeUtil.getLastColor(str1);
    
    localFancyMessage.setLastColor(str2);
    
    localFancyMessage.setChatColor(ChatColor.translateAlternateColorCodes('&', paramDeluxePrivateMessageFormat.getChatColor()));
    
    if ((paramDeluxePrivateMessageFormat.getTooltip() != null) && (!paramDeluxePrivateMessageFormat.getTooltip().isEmpty()))
    {
      localFancyMessage.tooltip(paramDeluxePrivateMessageFormat.getTooltip());
    }
    
    if (paramDeluxePrivateMessageFormat.getClickAction() != null)
    {
      String str3 = paramDeluxePrivateMessageFormat.getClickAction();
      
      if (str3.startsWith("[EXECUTE]"))
      {
        str3 = str3.replace("[EXECUTE]", "");
        localFancyMessage.command(str3);
      } else if (str3.startsWith("[URL]"))
      {
        str3 = str3.replace("[URL]", "");
        
        if ((!str3.startsWith("http://")) && 
          (!str3.startsWith("https://"))) {
          str3 = "http://" + str3;
        }
        

        localFancyMessage.link(str3);
      } else {
        localFancyMessage.suggest(str3);
      }
    }
    localFancyMessage.then(ChatColor.translateAlternateColorCodes('&', "&f"));
    return localFancyMessage;
  }
  
  public FancyMessage getFancyChatFormat(Player paramPlayer, DeluxeFormat paramDeluxeFormat)
  {
    if (paramDeluxeFormat == null) {
      return null;
    }
    
    FancyMessage localFancyMessage = null;
    
    String str1 = "";
    
    String str2 = paramDeluxeFormat.getChannel();
    
    if ((str2 != null) && (!str2.isEmpty()))
    {
      str2 = PlaceholderAPI.setPlaceholders(paramPlayer, str2);
      
      str1 = DeluxeUtil.getLastColor(str2);
      
      localFancyMessage = new FancyMessage(ChatColor.translateAlternateColorCodes('&', str2));
      
      if ((paramDeluxeFormat.useChannelClick()) && (paramDeluxeFormat.getChannelClickCommand() != null))
      {
        str3 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getChannelClickCommand());
        
        if (str3.startsWith("[EXECUTE]"))
        {
          str3 = str3.replace("[EXECUTE]", "");
          localFancyMessage.command(ChatColor.translateAlternateColorCodes('&', str3));
        } else if (str3.startsWith("[URL]"))
        {
          str3 = str3.replace("[URL]", "");
          
          if ((!str3.startsWith("http://")) && 
            (!str3.startsWith("https://"))) {
            str3 = "http://" + str3;
          }
          
          localFancyMessage.link(str3);
        } else {
          localFancyMessage.suggest(ChatColor.translateAlternateColorCodes('&', str3));
        }
      }
      
      if ((paramDeluxeFormat.showChannelTooltip()) && (paramDeluxeFormat.getChannelTooltip() != null))
      {
        localFancyMessage.tooltip(PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getChannelTooltip()));
      }
    }
    
    String str3 = paramDeluxeFormat.getPrefix();
    
    if ((str3 != null) && (!str3.isEmpty()))
    {
      str3 = PlaceholderAPI.setPlaceholders(paramPlayer, str3);
      
      if (localFancyMessage == null) {
        localFancyMessage = new FancyMessage(ChatColor.translateAlternateColorCodes('&', str3));
      } else {
        localFancyMessage.then(ChatColor.translateAlternateColorCodes('&', str1 + str3));
      }
      
      str1 = DeluxeUtil.getLastColor(str3);
      
      if ((paramDeluxeFormat.usePreClick()) && (paramDeluxeFormat.getPreClickCmd() != null))
      {
        str4 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getPreClickCmd());
        
        if (str4.startsWith("[EXECUTE]")) {
          str4 = str4.replace("[EXECUTE]", "");
          localFancyMessage.command(ChatColor.translateAlternateColorCodes('&', str4));
        } else if (str4.startsWith("[URL]"))
        {
          str4 = str4.replace("[URL]", "");
          
          if ((!str4.startsWith("http://")) && 
            (!str4.startsWith("https://"))) {
            str4 = "http://" + str4;
          }
          

          localFancyMessage.link(str4);
        }
        else {
          localFancyMessage.suggest(ChatColor.translateAlternateColorCodes('&', str4));
        }
      }
      
      if ((paramDeluxeFormat.showPreTooltip()) && (paramDeluxeFormat.getPrefixTooltip() != null))
      {
        localFancyMessage.tooltip(PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getPrefixTooltip()));
      }
    }
    
    String str4 = paramDeluxeFormat.getName();
    
    if ((str4 != null) && (!str4.isEmpty()))
    {
      str4 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getName());
      
      str5 = "";
      
      if (paramDeluxeFormat.getNameColor() != null) {
        str5 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getNameColor());
      }
      
      if (localFancyMessage == null) {
        localFancyMessage = new FancyMessage(ChatColor.translateAlternateColorCodes('&', str5 + str4));
      } else {
        localFancyMessage.then(ChatColor.translateAlternateColorCodes('&', str1 + str5 + str4));
      }
      
      str1 = DeluxeUtil.getLastColor(str5 + str4);
      
      if ((paramDeluxeFormat.useNameClick()) && (paramDeluxeFormat.getNameClickCmd() != null))
      {
        str6 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getNameClickCmd());
        
        if (str6.startsWith("[EXECUTE]"))
        {
          str6 = str6.replace("[EXECUTE]", "");
          localFancyMessage.command(ChatColor.translateAlternateColorCodes('&', str6));
        } else if (str6.startsWith("[URL]"))
        {
          str6 = str6.replace("[URL]", "");
          
          if ((!str6.startsWith("http://")) && 
            (!str6.startsWith("https://"))) {
            str6 = "http://" + str6;
          }
          

          localFancyMessage.link(str6);
        }
        else {
          localFancyMessage.suggest(ChatColor.translateAlternateColorCodes('&', str6));
        }
      }
      
      if ((paramDeluxeFormat.showNameTooltip()) && (paramDeluxeFormat.getNameTooltip() != null))
      {
        localFancyMessage.tooltip(PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getNameTooltip()));
      }
    }
    
    String str5 = paramDeluxeFormat.getSuffix();
    
    if ((str5 != null) && (!str5.isEmpty()))
    {
      str5 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getSuffix());
      
      if (localFancyMessage == null) {
        localFancyMessage = new FancyMessage(ChatColor.translateAlternateColorCodes('&', str5));
      } else {
        localFancyMessage.then(ChatColor.translateAlternateColorCodes('&', str1 + str5));
      }
      
      str1 = DeluxeUtil.getLastColor(str1 + str5);
      
      if ((paramDeluxeFormat.useSuffixClick()) && (paramDeluxeFormat.getSuffixClickCmd() != null))
      {
        str6 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getSuffixClickCmd());
        
        if (str6.startsWith("[EXECUTE]"))
        {
          str6 = str6.replace("[EXECUTE]", "");
          localFancyMessage.command(ChatColor.translateAlternateColorCodes('&', str6));
        } else if (str6.startsWith("[URL]"))
        {
          str6 = str6.replace("[URL]", "");
          
          if ((!str6.startsWith("http://")) && 
            (!str6.startsWith("https://"))) {
            str6 = "http://" + str6;
          }
          

          localFancyMessage.link(str6);
        }
        else {
          localFancyMessage.suggest(ChatColor.translateAlternateColorCodes('&', str6));
        }
      }
      if ((paramDeluxeFormat.showSuffixTooltip()) && (paramDeluxeFormat.getSuffixTooltip() != null))
      {
        localFancyMessage.tooltip(PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getSuffixTooltip()));
      }
    }
    
    String str6 = "";
    
    if ((paramDeluxeFormat.getChatColor() != null) && (!paramDeluxeFormat.getChatColor().isEmpty())) {
      str6 = PlaceholderAPI.setPlaceholders(paramPlayer, paramDeluxeFormat.getChatColor());
    }
    
    localFancyMessage.setLastColor(ChatColor.translateAlternateColorCodes('&', str1));
    
    if ((str6 != null) && (!str6.isEmpty()))
    {
      localFancyMessage.setChatColor(ChatColor.translateAlternateColorCodes('&', str1 + str6));
    }
    
    return localFancyMessage;
  }
  
  public String setRecipientPlaceholders(Player paramPlayer1, Player paramPlayer2, String paramString)
  {
    if (!useRelationPlaceholders) {
      return paramString;
    }
    
    if (paramString.contains("%recipient_name%")) {
      paramString = paramString.replace("%recipient_name%", paramPlayer2.getName());
    }
    
    if (paramString.contains("%recipient_displayname%")) {
      paramString = paramString.replace("%recipient_displayname%", paramPlayer2.getDisplayName());
    }
    
    return paramString;
  }
  

  public static boolean useBungee()
  {
    return bungee != null;
  }
  
  public static boolean enableSocialSpy(String paramString) {
    if (socialSpy == null) {
      socialSpy = new ArrayList();
      socialSpy.add(paramString);
      return true;
    }
    if (socialSpy.contains(paramString)) {
      return false;
    }
    socialSpy.add(paramString);
    return true;
  }
  
  public static boolean enableSocialSpy(Player paramPlayer) {
    return enableSocialSpy(paramPlayer.getUniqueId().toString());
  }
  
  public static boolean disableSocialSpy(String paramString) {
    if (socialSpy == null) {
      socialSpy = new ArrayList();
      return false;
    }
    if (!socialSpy.contains(paramString)) {
      return false;
    }
    socialSpy.remove(paramString);
    return true;
  }
  
  public static boolean disableSocialSpy(Player paramPlayer) {
    return disableSocialSpy(paramPlayer.getUniqueId().toString());
  }
  
  public static boolean inSocialSpy(String paramString) {
    if (socialSpy == null) {
      return false;
    }
    return socialSpy.contains(paramString);
  }
  
  public static boolean inSocialSpy(Player paramPlayer) {
    return inSocialSpy(paramPlayer.getUniqueId().toString());
  }
  
  public boolean setLocal(String paramString) {
    if (localPlayers == null) {
      localPlayers = new ArrayList();
      localPlayers.add(paramString);
      return true;
    }
    if (localPlayers.contains(paramString)) {
      return false;
    }
    localPlayers.add(paramString);
    return true;
  }
  
  public boolean setGlobal(String paramString) {
    if (localPlayers == null) {
      localPlayers = new ArrayList();
      return false;
    }
    if (!localPlayers.contains(paramString)) {
      return false;
    }
    localPlayers.remove(paramString);
    return true;
  }
  
  public static boolean isLocal(String paramString) {
    if (localPlayers == null) {
      return false;
    }
    return localPlayers.contains(paramString);
  }
  
  public static void forwardString(Player paramPlayer, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (!useBungee()) {
      return;
    }
    
    try
    {
      ByteArrayDataOutput localByteArrayDataOutput = ByteStreams.newDataOutput();
      localByteArrayDataOutput.writeUTF(paramString1);
      localByteArrayDataOutput.writeUTF(paramString2);
      localByteArrayDataOutput.writeBoolean(paramBoolean);
      
      paramPlayer.sendPluginMessage(instance, "DeluxeChat", localByteArrayDataOutput.toByteArray());
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  public static void forwardPm(Player paramPlayer, PrivateMessageType paramPrivateMessageType, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (!useBungee()) {
      return;
    }
    
    try
    {
      ByteArrayDataOutput localByteArrayDataOutput = ByteStreams.newDataOutput();
      localByteArrayDataOutput.writeUTF(paramPrivateMessageType.getType());
      localByteArrayDataOutput.writeUTF(paramPlayer.getName());
      localByteArrayDataOutput.writeUTF(paramString1);
      localByteArrayDataOutput.writeUTF(paramString2);
      localByteArrayDataOutput.writeUTF(paramString3);
      localByteArrayDataOutput.writeUTF(paramString4);
      paramPlayer.sendPluginMessage(instance, "DeluxeChatPM", localByteArrayDataOutput.toByteArray());
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  public static boolean globalOnJoin() {
    return joinGlobal;
  }
  
  public static String getServerName()
  {
    return serverName != null ? serverName : "";
  }
  
  public static boolean useRelationPlaceholders() {
    return useRelationPlaceholders;
  }
  
  public static boolean bungeePMEnabled() {
    return bungeePM;
  }
  
  public static SimpleDateFormat getTimestampFormat() {
    return timestampFormat;
  }
  
  public static String getBooleanTrue() {
    return booleanTrue != null ? booleanTrue : "true";
  }
  
  public static String getBooleanFalse() {
    return booleanFalse != null ? booleanFalse : "false";
  }
  
  public static String getSocialSpyFormat() {
    return socialSpyFormat;
  }
  
  public static DeluxePrivateMessageFormat getToSenderPMFormat() {
    return DeluxePrivateMessageFormat.newInstance(toSenderPmFormat);
  }
  
  public static DeluxePrivateMessageFormat getToRecipientPMFormat() {
    return DeluxePrivateMessageFormat.newInstance(toRecipientPmFormat);
  }
  
  public static void setInPm(String paramString1, String paramString2) {
    if (inPrivateChat == null) {
      inPrivateChat = new HashMap();
    }
    inPrivateChat.put(paramString1, paramString2);
  }
  
  public static void removeFromPM(String paramString) {
    if (inPrivateChat == null) {
      return;
    }
    if (inPrivateChat.containsKey(paramString)) {
      inPrivateChat.remove(paramString);
    }
  }
  
  public static String getPmRecipient(String paramString) {
    if (inPrivateChat == null) {
      return null;
    }
    if (inPrivateChat.containsKey(paramString)) {
      return (String)inPrivateChat.get(paramString);
    }
    return null;
  }
  
  public Logger getLog() {
    return log;
  }
  
  public void setLog(Logger paramLogger) {
    log = paramLogger;
  }
}
