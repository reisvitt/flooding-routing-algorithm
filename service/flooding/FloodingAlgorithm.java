package service.flooding;

import model.Packet;
import model.Router;

public interface FloodingAlgorithm {
  public void send(Router from, Router router, Packet packet);
}
