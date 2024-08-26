package model;

public class Package {
  private String sender;
  private String receiver;
  private String message;
  private Integer TTL;

  public Package() {
  }

  public Package(String sender, String receiver, String message, Integer TTL) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
    this.TTL = TTL;
  }

  public String getMessage() {
    return message;
  }

  public String getReceiver() {
    return receiver;
  }

  public String getSender() {
    return sender;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public Integer getTTL() {
    return TTL;
  }

  public void setTTL(Integer tTL) {
    TTL = tTL;
  }

  public void decrementTTL() {
    this.TTL = this.TTL - 1;
  }

  public Package duplicate() {
    return new Package(this.sender, this.receiver, this.message, this.TTL);
  }
}
