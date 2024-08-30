package service.flooding;

import model.Connection;
import model.Packet;
import model.Router;

// all interfaces
public class FloodingController1 implements FloodingAlgorithm {
  public FloodingController1() {
  }

  @Override
  public void send(Router from, Router router, Packet packet) {
    new Thread(() -> {
      if (router.getIp().equals(packet.getReceiver())) {
        System.out.println("CHEGOU AO DESTINO: " + packet.getMessage());
        return;
      }

      for (Connection connection : router.getConnections()) {
        Router to;
        if (connection.getCon1().getIp().equals(router.getIp())) {
          to = connection.getCon2();
        } else {
          to = connection.getCon1();
        }

        System.out.println(
            "ROUTER: " + router.getIp() + "CONNECTIONS: " + router.getConnections() + " ENVIANDO PARA: " + to.getIp());

        to.getController().send(router, to, packet);
      }
    }).start();
  }
}
