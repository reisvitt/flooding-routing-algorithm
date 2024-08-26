package model;

public class Connection {
  private Router con1;
  private Router con2;
  private Integer cost;

  public Connection() {
  }

  public Connection(Router con1, Router con2, Integer cost) {
    this.con1 = con1;
    this.con2 = con2;
    this.cost = cost;
  }

  public Router getCon1() {
    return con1;
  }

  public void setCon1(Router con1) {
    this.con1 = con1;
  }

  public Router getCon2() {
    return con2;
  }

  public void setCon2(Router con2) {
    this.con2 = con2;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "Connection{con1=" + this.con1.getIp() + " con2=" + this.con2.getIp() + " cost=" + this.cost + "}";
  }
}