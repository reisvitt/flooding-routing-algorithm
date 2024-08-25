package model;

import java.util.ArrayList;

public class Router {
  private String ip;
  private ArrayList<Router> connections;

  public Router(String ip) {
    this.ip = ip;
    this.connections = new ArrayList<Router>();
  }

  public String getIp() {
    return ip;
  }

  public ArrayList<Router> getConnections() {
    return connections;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setConnections(ArrayList<Router> connections) {
    this.connections = connections;
  }

  public void addConnection(Router router) {
    this.connections.add(router);
  }

  public void removeConnectino(Router router) {
    this.connections.remove(router);
  }
}
