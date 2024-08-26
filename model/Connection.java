package model;

public class Connection {
  private Router from;
  private Router to;
  private Integer cost;

  public Connection() {
  }

  public Connection(Router from, Router to, Integer cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }

  public Router getFrom() {
    return from;
  }

  public void setFrom(Router from) {
    this.from = from;
  }

  public Router getTo() {
    return to;
  }

  public void setTo(Router to) {
    this.to = to;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "Connection{from='" + this.from.getIp() + "'" + " to=" + this.to.getIp() + " cost=" + this.cost + "}";
  }
}