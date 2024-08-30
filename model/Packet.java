package model;

import java.util.ArrayList;

public class Packet {
  private String sender;
  private String receiver;
  private String message;
  private Integer TTL;
  private ArrayList<String> routersHistory;

  public Packet() {
  }

  public Packet(String sender, String receiver, String message, Integer TTL) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
    this.TTL = TTL;
    routersHistory = new ArrayList<String>();
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

  public ArrayList<String> getRoutersHistory() {
    return routersHistory;
  }

  public void setRoutersHistory(ArrayList<String> routersHistory) {
    this.routersHistory = routersHistory;
  }

  public void addRouterToHistory(String router) {
    this.routersHistory.add(router);
  }

  public Packet duplicate() {
    Packet newPackage = new Packet(this.sender, this.receiver, this.message, this.TTL);
    newPackage.setRoutersHistory(new ArrayList<String>(this.routersHistory));
    return newPackage;
  }
}
