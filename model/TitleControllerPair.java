package model;

import service.flooding.FloodingAlgorithm;

public class TitleControllerPair {
  private String title;
  private FloodingAlgorithm controller;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public FloodingAlgorithm getController() {
    return controller;
  }

  public void setController(FloodingAlgorithm controller) {
    this.controller = controller;
  }

  public TitleControllerPair(String title, FloodingAlgorithm controller) {
    this.title = title;
    this.controller = controller;
  }

  public String toString() {
    return this.title;
  }
}
