package me.clip.deluxechat.objects;

import java.util.List;







public class DeluxePrivateMessageFormat
{
  private String format;
  private List<String> tooltip;
  private String clickAction;
  private String chatColor;
  
  public DeluxePrivateMessageFormat(String paramString1, List<String> paramList, String paramString2, String paramString3)
  {
    setChatColor(paramString3);
    setFormat(paramString1);
    setTooltip(paramList);
    setClickAction(paramString2);
  }
  
  public DeluxePrivateMessageFormat(String paramString) {
    setFormat(paramString);
  }
  
  public static DeluxePrivateMessageFormat newInstance(DeluxePrivateMessageFormat paramDeluxePrivateMessageFormat) {
    DeluxePrivateMessageFormat localDeluxePrivateMessageFormat = new DeluxePrivateMessageFormat(paramDeluxePrivateMessageFormat.getFormat());
    localDeluxePrivateMessageFormat.setTooltip(paramDeluxePrivateMessageFormat.getTooltip());
    localDeluxePrivateMessageFormat.setClickAction(paramDeluxePrivateMessageFormat.getClickAction());
    localDeluxePrivateMessageFormat.setChatColor(paramDeluxePrivateMessageFormat.getChatColor());
    return localDeluxePrivateMessageFormat;
  }
  
  public String getFormat() {
    return format;
  }
  
  public void setFormat(String paramString) {
    format = paramString;
  }
  
  public List<String> getTooltip() {
    return tooltip;
  }
  
  public void setTooltip(List<String> paramList) {
    tooltip = paramList;
  }
  
  public String getClickAction() {
    return clickAction;
  }
  
  public void setClickAction(String paramString) {
    clickAction = paramString;
  }
  
  public String getChatColor() {
    return chatColor;
  }
  
  public void setChatColor(String paramString) {
    chatColor = paramString;
  }
}
