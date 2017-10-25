package me.clip.deluxechat.messaging;

public enum PrivateMessageType
{
  MESSAGE_SEND("MESSAGE_SEND"), 
  MESSAGE_SENT_SUCCESS("MESSAGE_SENT_SUCCESS"), 
  MESSAGE_SENT_FAIL("MESSAGE_SENT_FAIL"), 
  MESSAGE_TO_RECIPIENT("MESSAGE_TO_RECIPIENT");
  
  private String name;
  
  private PrivateMessageType(String paramString) {
    name = paramString;
  }
  
  public String getType() {
    return name;
  }
  
  public static PrivateMessageType fromName(String paramString) {
    for (PrivateMessageType localPrivateMessageType : ) {
      if (localPrivateMessageType.getType().equalsIgnoreCase(paramString)) {
        return localPrivateMessageType;
      }
    }
    return null;
  }
}
