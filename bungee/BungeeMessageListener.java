package me.clip.deluxechat.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import java.util.Iterator;
import me.clip.deluxechat.DeluxeChat;
import me.clip.deluxechat.DeluxeUtil;
import me.clip.deluxechat.Lang;
import me.clip.deluxechat.compatibility.CompatibilityManager;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.messaging.PrivateMessageType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class BungeeMessageListener
  implements PluginMessageListener
{
  DeluxeChat plugin;
  
  public BungeeMessageListener(DeluxeChat paramDeluxeChat) { plugin = paramDeluxeChat; }
  
  public void onPluginMessageReceived(String paramString, Player paramPlayer, byte[] paramArrayOfByte) {
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (paramString.equals("DeluxeChat"))
    {
      localObject1 = ByteStreams.newDataInput(paramArrayOfByte);
      
      localObject2 = ((ByteArrayDataInput)localObject1).readUTF();
      
      localObject3 = ((ByteArrayDataInput)localObject1).readUTF();
      
      boolean bool = ((ByteArrayDataInput)localObject1).readBoolean();
      
      plugin.getChat().sendBungeeChat((String)localObject2, (String)localObject3, bool); } else { String str1;
      String str2;
      Object localObject4; Object localObject5; if (paramString.equals("DeluxeChatPM"))
      {
        localObject1 = ByteStreams.newDataInput(paramArrayOfByte);
        
        localObject2 = ((ByteArrayDataInput)localObject1).readUTF();
        
        localObject3 = PrivateMessageType.fromName((String)localObject2);
        
        if (localObject3 == null) {
          return;
        }
        
        str1 = ((ByteArrayDataInput)localObject1).readUTF();
        
        str2 = ((ByteArrayDataInput)localObject1).readUTF();
        
        localObject4 = ((ByteArrayDataInput)localObject1).readUTF();
        
        localObject5 = ((ByteArrayDataInput)localObject1).readUTF();
        
        String str3 = ((ByteArrayDataInput)localObject1).readUTF();
        Player localPlayer;
        if (localObject3 == PrivateMessageType.MESSAGE_SENT_FAIL)
        {
          localPlayer = Bukkit.getPlayer(str1);
          
          DeluxeUtil.sms(localPlayer, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] {
            str2 }));
          

          DeluxeChat.removeFromPM(localPlayer.getName());
        }
        else if (localObject3 == PrivateMessageType.MESSAGE_TO_RECIPIENT)
        {
          localPlayer = Bukkit.getPlayer(str2);
          
          localObject4 = ((String)localObject4).replace("%recipient%", localPlayer.getName()).replace("%player%", str1);
          
          plugin.getChat().sendPrivateMessage(localPlayer, (String)localObject4, (String)localObject5);
          
          DeluxeChat.setInPm(localPlayer.getName(), str1);
        }
        else if (localObject3 == PrivateMessageType.MESSAGE_SENT_SUCCESS)
        {
          localPlayer = Bukkit.getPlayer(str1);
          
          FancyMessage localFancyMessage = plugin.getBungeePrivateMessageFormat(localPlayer, DeluxeChat.getToSenderPMFormat());
          
          String str4 = localFancyMessage.toJSONString();
          
          str4 = PlaceholderAPI.setPlaceholders(localPlayer, str4);
          
          str4 = str4.replace("%recipient%", str2).replace("%player%", str1);
          
          String str5 = plugin.getChat().convertPm(localPlayer, localFancyMessage.getLastColor() + localFancyMessage.getChatColor() + str3);
          
          plugin.getChat().sendPrivateMessage(localPlayer, org.bukkit.ChatColor.translateAlternateColorCodes('&', str4), str5);
          
          DeluxeChat.setInPm(localPlayer.getName(), str2);
        }
        
        return; }
      if (paramString.equals("DeluxeChatSocialSpy"))
      {
        localObject1 = DeluxeChat.getSocialSpyFormat();
        
        if ((localObject1 == null) || (((String)localObject1).isEmpty())) {
          return;
        }
        
        localObject2 = ByteStreams.newDataInput(paramArrayOfByte);
        
        localObject3 = ((ByteArrayDataInput)localObject2).readUTF();
        str1 = ((ByteArrayDataInput)localObject2).readUTF();
        str2 = ((ByteArrayDataInput)localObject2).readUTF();
        
        localObject1 = ((String)localObject1).replace("%player%", (CharSequence)localObject3).replace("%recipient%", str1);
        
        for (localObject5 = Bukkit.getOnlinePlayers().iterator(); ((Iterator)localObject5).hasNext();) { localObject4 = (Player)((Iterator)localObject5).next();
          
          if (DeluxeChat.inSocialSpy((Player)localObject4))
          {
            if ((!((Player)localObject4).getName().equals(localObject3)) && (!((Player)localObject4).getName().equals(str1)))
            {

              DeluxeUtil.sms((Player)localObject4, localObject1 + str2);
            }
          }
        }
      }
    }
  }
}
