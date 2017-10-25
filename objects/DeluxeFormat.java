package me.clip.deluxechat.objects;

import java.util.List;
















public class DeluxeFormat
{
  private String identifier;
  private int index;
  private String channel;
  private String prefix;
  private String nameColor;
  private String name;
  private String suffix;
  private String chatColor;
  private boolean showChannelTooltip;
  private boolean showNameTooltip;
  private boolean showPreTooltip;
  private boolean showSuffixTooltip;
  private List<String> channelTooltip;
  private List<String> prefixTooltip;
  private List<String> nameTooltip;
  private List<String> suffixTooltip;
  private boolean useChannelClick;
  private boolean usePreClick;
  private boolean useNameClick;
  private boolean useSuffixClick;
  private String channelClickCommand;
  private String preClickCmd;
  private String nameClickCmd;
  private String suffixClickCmd;
  
  public DeluxeFormat(String paramString, int paramInt)
  {
    setIndex(paramInt);
    setIdentifier(paramString);
    
    setPrefix("");
    setNameColor("&b");
    setName("%displayname%");
    setSuffix("&b: ");
    setChatColor("&f");
  }
  
  public static DeluxeFormat newInstance(DeluxeFormat paramDeluxeFormat) {
    DeluxeFormat localDeluxeFormat = new DeluxeFormat(paramDeluxeFormat.getIdentifier(), paramDeluxeFormat.getIndex());
    localDeluxeFormat.setChannel(paramDeluxeFormat.getChannel());
    localDeluxeFormat.setUseChannelClick(paramDeluxeFormat.useChannelClick());
    localDeluxeFormat.setChannelClickCommand(paramDeluxeFormat.getChannelClickCommand());
    localDeluxeFormat.setShowChannelTooltip(paramDeluxeFormat.showChannelTooltip());
    localDeluxeFormat.setChannelTooltip(paramDeluxeFormat.getChannelTooltip());
    localDeluxeFormat.setPrefix(paramDeluxeFormat.getPrefix());
    localDeluxeFormat.setUsePreClick(paramDeluxeFormat.usePreClick());
    localDeluxeFormat.setPreClickCmd(paramDeluxeFormat.getPreClickCmd());
    localDeluxeFormat.setShowPreTooltip(paramDeluxeFormat.showPreTooltip());
    localDeluxeFormat.setPrefixTooltip(paramDeluxeFormat.getPrefixTooltip());
    localDeluxeFormat.setNameColor(paramDeluxeFormat.getNameColor());
    localDeluxeFormat.setName(paramDeluxeFormat.getName());
    localDeluxeFormat.setUseNameClick(paramDeluxeFormat.useNameClick());
    localDeluxeFormat.setNameClickCmd(paramDeluxeFormat.getNameClickCmd());
    localDeluxeFormat.setShowNameTooltip(paramDeluxeFormat.showNameTooltip());
    localDeluxeFormat.setNameTooltip(paramDeluxeFormat.getNameTooltip());
    localDeluxeFormat.setSuffix(paramDeluxeFormat.getSuffix());
    localDeluxeFormat.setUseSuffixClick(paramDeluxeFormat.useSuffixClick());
    localDeluxeFormat.setSuffixClickCmd(paramDeluxeFormat.getSuffixClickCmd());
    localDeluxeFormat.setShowSuffixTooltip(paramDeluxeFormat.showSuffixTooltip());
    localDeluxeFormat.setSuffixTooltip(paramDeluxeFormat.getSuffixTooltip());
    localDeluxeFormat.setChatColor(paramDeluxeFormat.getChatColor());
    return localDeluxeFormat;
  }
  
  public int getIndex() {
    return index;
  }
  
  public void setIndex(int paramInt) {
    index = paramInt;
  }
  
  public String getPrefix() {
    return prefix;
  }
  
  public void setPrefix(String paramString) {
    prefix = paramString;
  }
  
  public String getSuffix() {
    return suffix;
  }
  
  public void setSuffix(String paramString) {
    suffix = paramString;
  }
  
  public List<String> getNameTooltip() {
    return nameTooltip;
  }
  
  public void setNameTooltip(List<String> paramList) {
    nameTooltip = paramList;
  }
  
  public String getIdentifier() {
    return identifier;
  }
  
  public void setIdentifier(String paramString) {
    identifier = paramString;
  }
  
  public String getNameColor()
  {
    return nameColor;
  }
  
  public void setNameColor(String paramString) {
    nameColor = paramString;
  }
  
  public String getChatColor() {
    return chatColor;
  }
  
  public void setChatColor(String paramString) {
    chatColor = paramString;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String paramString) {
    name = paramString;
  }
  
  public List<String> getPrefixTooltip() {
    return prefixTooltip;
  }
  
  public void setPrefixTooltip(List<String> paramList) {
    prefixTooltip = paramList;
  }
  
  public List<String> getSuffixTooltip() {
    return suffixTooltip;
  }
  
  public void setSuffixTooltip(List<String> paramList) {
    suffixTooltip = paramList;
  }
  
  public boolean showNameTooltip() {
    return showNameTooltip;
  }
  
  public void setShowNameTooltip(boolean paramBoolean) {
    showNameTooltip = paramBoolean;
  }
  
  public boolean showPreTooltip() {
    return showPreTooltip;
  }
  
  public void setShowPreTooltip(boolean paramBoolean) {
    showPreTooltip = paramBoolean;
  }
  
  public boolean showSuffixTooltip() {
    return showSuffixTooltip;
  }
  
  public void setShowSuffixTooltip(boolean paramBoolean) {
    showSuffixTooltip = paramBoolean;
  }
  
  public boolean usePreClick() {
    return usePreClick;
  }
  
  public void setUsePreClick(boolean paramBoolean) {
    usePreClick = paramBoolean;
  }
  
  public boolean useNameClick() {
    return useNameClick;
  }
  
  public void setUseNameClick(boolean paramBoolean) {
    useNameClick = paramBoolean;
  }
  
  public boolean useSuffixClick() {
    return useSuffixClick;
  }
  
  public void setUseSuffixClick(boolean paramBoolean) {
    useSuffixClick = paramBoolean;
  }
  
  public String getPreClickCmd() {
    return preClickCmd;
  }
  
  public void setPreClickCmd(String paramString) {
    preClickCmd = paramString;
  }
  
  public String getNameClickCmd() {
    return nameClickCmd;
  }
  
  public void setNameClickCmd(String paramString) {
    nameClickCmd = paramString;
  }
  
  public String getSuffixClickCmd() {
    return suffixClickCmd;
  }
  
  public void setSuffixClickCmd(String paramString) {
    suffixClickCmd = paramString;
  }
  
  public boolean showChannelTooltip() {
    return showChannelTooltip;
  }
  
  public void setShowChannelTooltip(boolean paramBoolean) {
    showChannelTooltip = paramBoolean;
  }
  
  public List<String> getChannelTooltip() {
    return channelTooltip;
  }
  
  public void setChannelTooltip(List<String> paramList) {
    channelTooltip = paramList;
  }
  
  public boolean useChannelClick() {
    return useChannelClick;
  }
  
  public void setUseChannelClick(boolean paramBoolean) {
    useChannelClick = paramBoolean;
  }
  
  public String getChannelClickCommand() {
    return channelClickCommand;
  }
  
  public void setChannelClickCommand(String paramString) {
    channelClickCommand = paramString;
  }
  
  public String getChannel() {
    return channel;
  }
  
  public void setChannel(String paramString) {
    channel = paramString;
  }
}
