package me.clip.deluxechat.bungee;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import me.clip.deluxechat.messaging.PrivateMessageType;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

public class DeluxeChatBungee extends Plugin implements Listener
{
  public DeluxeChatBungee() {}
  
  public void onEnable()
  {
    getProxy().registerChannel("DeluxeChat");
    getProxy().registerChannel("DeluxeChatPM");
    getProxy().registerChannel("DeluxeChatSocialSpy");
    getProxy().getPluginManager().registerListener(this, this);
  }
  

  public void onDisable()
  {
    getProxy().unregisterChannel("DeluxeChat");
    getProxy().unregisterChannel("DeluxeChatPM");
    getProxy().unregisterChannel("DeluxeChatSocialSpy"); }
  
  @EventHandler
  public void receievePluginMessage(PluginMessageEvent paramPluginMessageEvent) { Object localObject1;
    Object localObject3;
    Object localObject2;
    if (paramPluginMessageEvent.getTag().equalsIgnoreCase("DeluxeChat"))
    {
      if ((paramPluginMessageEvent.getSender() instanceof UserConnection)) {
        paramPluginMessageEvent.setCancelled(true);
        return;
      }
      
      localObject1 = paramPluginMessageEvent.getSender().getAddress();
      
      for (localObject3 = getProxy().getServers().values().iterator(); ((Iterator)localObject3).hasNext();) { localObject2 = (ServerInfo)((Iterator)localObject3).next();
        
        if ((!((ServerInfo)localObject2).getAddress().equals(localObject1)) && (((ServerInfo)localObject2).getPlayers().size() > 0))
        {
          ((ServerInfo)localObject2).sendData("DeluxeChat", paramPluginMessageEvent.getData());
        }
      }
    } else if (paramPluginMessageEvent.getTag().equalsIgnoreCase("DeluxeChatPM"))
    {
      if ((paramPluginMessageEvent.getSender() instanceof UserConnection)) {
        paramPluginMessageEvent.setCancelled(true);
        return;
      }
      
      localObject1 = ByteStreams.newDataInput(paramPluginMessageEvent.getData());
      
      localObject2 = ((ByteArrayDataInput)localObject1).readUTF();
      
      localObject3 = PrivateMessageType.fromName((String)localObject2);
      
      if (localObject3 == null) {
        return;
      }
      
      if (localObject3 != PrivateMessageType.MESSAGE_SEND) {
        return;
      }
      
      String str1 = ((ByteArrayDataInput)localObject1).readUTF();
      
      String str2 = ((ByteArrayDataInput)localObject1).readUTF();
      
      String str3 = ((ByteArrayDataInput)localObject1).readUTF();
      
      String str4 = ((ByteArrayDataInput)localObject1).readUTF();
      
      String str5 = ((ByteArrayDataInput)localObject1).readUTF();
      

      ProxiedPlayer localProxiedPlayer = getProxy().getPlayer(str1);
      
      if (localProxiedPlayer == null) {
        return;
      }
      
      Collection localCollection = null;
      
      try
      {
        localCollection = getProxy().matchPlayer(str2);
      }
      catch (NoSuchElementException localNoSuchElementException)
      {
        localByteArrayDataOutput1 = ByteStreams.newDataOutput();
        localByteArrayDataOutput1.writeUTF(PrivateMessageType.MESSAGE_SENT_FAIL.getType());
        localByteArrayDataOutput1.writeUTF(str1);
        localByteArrayDataOutput1.writeUTF(str2);
        localByteArrayDataOutput1.writeUTF(str3);
        localByteArrayDataOutput1.writeUTF(str4);
        localByteArrayDataOutput1.writeUTF(str5);
        localProxiedPlayer.getServer().sendData("DeluxeChatPM", localByteArrayDataOutput1.toByteArray());
        return;
      }
      
      if ((localCollection == null) || (localCollection.isEmpty())) {
        localObject4 = ByteStreams.newDataOutput();
        ((ByteArrayDataOutput)localObject4).writeUTF(PrivateMessageType.MESSAGE_SENT_FAIL.getType());
        ((ByteArrayDataOutput)localObject4).writeUTF(str1);
        ((ByteArrayDataOutput)localObject4).writeUTF(str2);
        ((ByteArrayDataOutput)localObject4).writeUTF(str3);
        ((ByteArrayDataOutput)localObject4).writeUTF(str4);
        ((ByteArrayDataOutput)localObject4).writeUTF(str5);
        localProxiedPlayer.getServer().sendData("DeluxeChatPM", ((ByteArrayDataOutput)localObject4).toByteArray());
        return;
      }
      
      Object localObject4 = (ProxiedPlayer)Iterables.get(localCollection, 0);
      
      if (localObject4 == null) {
        localByteArrayDataOutput1 = ByteStreams.newDataOutput();
        localByteArrayDataOutput1.writeUTF(PrivateMessageType.MESSAGE_SENT_FAIL.getType());
        localByteArrayDataOutput1.writeUTF(str1);
        localByteArrayDataOutput1.writeUTF(str2);
        localByteArrayDataOutput1.writeUTF(str3);
        localByteArrayDataOutput1.writeUTF(str4);
        localByteArrayDataOutput1.writeUTF(str5);
        localProxiedPlayer.getServer().sendData("DeluxeChatPM", localByteArrayDataOutput1.toByteArray());
        return;
      }
      
      ByteArrayDataOutput localByteArrayDataOutput1 = ByteStreams.newDataOutput();
      localByteArrayDataOutput1.writeUTF(PrivateMessageType.MESSAGE_TO_RECIPIENT.getType());
      localByteArrayDataOutput1.writeUTF(str1);
      localByteArrayDataOutput1.writeUTF(((ProxiedPlayer)localObject4).getName());
      localByteArrayDataOutput1.writeUTF(str3);
      localByteArrayDataOutput1.writeUTF(str4);
      localByteArrayDataOutput1.writeUTF(str5);
      ((ProxiedPlayer)localObject4).getServer().sendData("DeluxeChatPM", localByteArrayDataOutput1.toByteArray());
      
      ByteArrayDataOutput localByteArrayDataOutput2 = ByteStreams.newDataOutput();
      
      localByteArrayDataOutput2.writeUTF(PrivateMessageType.MESSAGE_SENT_SUCCESS.getType());
      localByteArrayDataOutput2.writeUTF(str1);
      localByteArrayDataOutput2.writeUTF(((ProxiedPlayer)localObject4).getName());
      localByteArrayDataOutput2.writeUTF(str3);
      localByteArrayDataOutput2.writeUTF(str4);
      localByteArrayDataOutput2.writeUTF(str5);
      localProxiedPlayer.getServer().sendData("DeluxeChatPM", localByteArrayDataOutput2.toByteArray());
      
      ByteArrayDataOutput localByteArrayDataOutput3 = ByteStreams.newDataOutput();
      localByteArrayDataOutput3.writeUTF(str1);
      localByteArrayDataOutput3.writeUTF(((ProxiedPlayer)localObject4).getName());
      localByteArrayDataOutput3.writeUTF(str5);
      
      byte[] arrayOfByte = localByteArrayDataOutput3.toByteArray();
      
      localProxiedPlayer.getServer().sendData("DeluxeChatSocialSpy", arrayOfByte);
      ((ProxiedPlayer)localObject4).getServer().sendData("DeluxeChatSocialSpy", arrayOfByte);
    }
  }
}
