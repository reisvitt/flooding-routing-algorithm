package model;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import service.flooding.FloodingAlgorithm;

public class Router {
  private String ip;
  private ArrayList<Connection> connections;
  private FloodingAlgorithm controller;
  private StackPane stack;
  private Pane network;
  private Store store;

  public Router(String ip, Pane network) {
    this.ip = ip;
    this.connections = new ArrayList<Connection>();
    this.network = network;
    this.init();
  }

  public Router(String ip, FloodingAlgorithm controller, Pane network) {
    this.ip = ip;
    this.connections = new ArrayList<Connection>();
    this.controller = controller;
    this.network = network;
    this.init();
  }

  public void init() {
    this.store = Store.getInstance();
  }

  public String getIp() {
    return ip;
  }

  public ArrayList<Connection> getConnections() {
    return connections;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setConnections(ArrayList<Connection> connections) {
    this.connections = connections;
  }

  public void addConnection(Connection connection) {
    this.connections.add(connection);
  }

  public void removeConnection(Connection connection) {
    this.connections.remove(connection);
  }

  public FloodingAlgorithm getController() {
    return controller;
  }

  public void showSuccess(Router from, Packet packet) {
    // exibir mensagem de sucesso que chegou o pacote corretamente.
  }

  public void showError(Router from, Packet packet) {
    // exibir mensagem de erro -> venceu ttl ou ja passou
  }

  public void send(Router from, Packet packet) {
    if (!this.store.getRunningProperty().get()) {
      return;
    }

    if (from == null && packet.getReceiver().equals(this.getIp())) {
      this.store.decrement();
    }

    new Thread(() -> {
      if (this.getIp().equals(packet.getReceiver())) {
        String fromIp;
        if (from == null) {
          fromIp = this.ip;
        } else {
          fromIp = from.getIp();
        }

        System.out.println(
            "CHEGOU AO DESTINO: " + packet.getMessage() + " FROM: " + fromIp + " TTL: " + packet.getTTL()
                + " History: " + packet.getRoutersHistory());
      } else {
        this.controller.send(from, this, packet);
      }
    }).start();
  }

  public void flush(Router to, Packet packet) {
    this.store.increment();

    Platform.runLater(() -> {
      this.store.incrementMessageCounter();
      this.getNetwork().getChildren().add(packet.getImage());

      TranslateTransition transition = packet.startTransition(this, to);
      transition.setOnFinished(e -> {
        this.getNetwork().getChildren().remove(packet.getImage());
        to.send(this, packet);

        store.decrement();
      });
      transition.play();
    });
  }

  public void setController(FloodingAlgorithm controller) {
    this.controller = controller;
  }

  public StackPane getStack() {
    return stack;
  }

  public void setStack(StackPane stack) {
    this.stack = stack;
  }

  public Pane getNetwork() {
    return this.network;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Router router = (Router) obj;

    return this.ip.equals(router.getIp());
  }

  @Override
  public String toString() {
    return "Router{ip='" + this.ip + "'" + " connections=" + this.connections.toString() + "}";
  }
}
