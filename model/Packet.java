package model;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Packet {
  private String sender;
  private String receiver;
  private String message;
  private Integer TTL;
  private ArrayList<String> routersHistory;
  private ImageView image;

  public Packet() {
    this.config();
  }

  public Packet(String sender, String receiver, String message, Integer TTL) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
    this.TTL = TTL;
    routersHistory = new ArrayList<String>();

    this.config();
  }

  private void config() {
    this.image = new ImageView();
    Image im = new Image("./view/images/message.png");
    this.image.setImage(im);
    this.image.setFitWidth(20);
    this.image.setFitHeight(20);
    this.image.getStyleClass().add("message");
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

  public ImageView getImage() {
    return this.image;
  }

  public Packet duplicate() {
    Packet newPackage = new Packet(this.sender, this.receiver, this.message, this.TTL);
    newPackage.setRoutersHistory(new ArrayList<String>(this.routersHistory));
    return newPackage;
  }

  public TranslateTransition startTransition(Router from, Router to) {
    Store store = Store.getInstance();
    int vel = store.getVelocity().get(); // velue between 0 and 10. Default 5

    int each = 400; // mili
    int time = vel * each;
    time = (each * 10) - time;

    if (time == 0) {
      time = 200;
    }

    // Cria um TranslateTransition para mover o ImageView
    double move = 10;
    double fromX = from.getStack().getLayoutX() + move;
    double fromY = from.getStack().getLayoutY() + move;

    double toX = to.getStack().getLayoutX() + move;
    double toY = to.getStack().getLayoutY() + move;

    this.image.setX(0);
    this.image.setY(0);

    TranslateTransition transition = new TranslateTransition();
    transition.setNode(this.image);
    transition.setDuration(Duration.millis(time));
    transition.setFromX(fromX);
    transition.setFromY(fromY);
    transition.setToX(toX);
    transition.setToY(toY);
    transition.setCycleCount(1);
    transition.setAutoReverse(false);

    return transition;
  }
}
