package service;

import model.Packet;
import model.Router;

public interface IController {
  public void send(Router from, Router router, Packet packet);
}
