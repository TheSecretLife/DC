package me.clip.deluxechat.updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import me.clip.deluxechat.DeluxeChat;
import org.bukkit.plugin.PluginDescriptionFile;


public class SpigotUpdater
{
  private DeluxeChat plugin;
  final int resource = 1277;
  private static String latestVersion = "";
  private static boolean updateAvailable = false;
  
  public SpigotUpdater(DeluxeChat paramDeluxeChat) {
    plugin = paramDeluxeChat;
  }
  
  private String getSpigotVersion() {
    try {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(
        "http://www.spigotmc.org/api/general.php").openConnection();
      localHttpURLConnection.setDoOutput(true);
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.getOutputStream()
        .write("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=1277"
        .getBytes("UTF-8"));
      String str = new BufferedReader(new InputStreamReader(
        localHttpURLConnection.getInputStream())).readLine();
      if (str.length() <= 7) {
        return str;
      }
    } catch (Exception localException) {
      System.out.println("----------------------------");
      System.out.println("     DeluxeChat Updater");
      System.out.println(" ");
      System.out.println("Could not connect to spigotmc.org");
      System.out.println("to check for updates! ");
      System.out.println(" ");
      System.out.println("----------------------------");
    }
    return null;
  }
  
  private boolean checkHigher(String paramString1, String paramString2) {
    String str1 = toReadable(paramString1);
    String str2 = toReadable(paramString2);
    return str1.compareTo(str2) < 0;
  }
  
  public boolean checkUpdates() {
    if (getHighest() != "") {
      return true;
    }
    String str = getSpigotVersion();
    if ((str != null) && 
      (checkHigher(plugin.getDescription().getVersion(), str))) {
      latestVersion = str;
      updateAvailable = true;
      return true;
    }
    
    return false;
  }
  
  public static boolean updateAvailable() {
    return updateAvailable;
  }
  
  public static String getHighest() {
    return latestVersion;
  }
  
  private String toReadable(String paramString) {
    String[] arrayOfString1 = Pattern.compile(".", 16).split(
      paramString.replace("v", ""));
    paramString = "";
    for (String str : arrayOfString1)
      paramString = paramString + String.format("%4s", new Object[] { str });
    return paramString;
  }
}
