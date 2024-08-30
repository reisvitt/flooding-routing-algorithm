package model;

import java.util.ArrayList;

import service.flooding.FloodingAlgorithm;

public class Router {
  private String ip;
  private ArrayList<Connection> connections;
  private FloodingAlgorithm controller;

  public Router(String ip) {
    this.ip = ip;
    this.connections = new ArrayList<Connection>();
  }

  public Router(String ip, FloodingAlgorithm controller) {
    this.ip = ip;
    this.connections = new ArrayList<Connection>();
    this.controller = controller;
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

  public void setController(FloodingAlgorithm controller) {
    this.controller = controller;
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
