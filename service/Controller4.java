package service;

import model.Connection;
import model.Packet;
import model.Router;

// all interfaces
public class Controller4 implements IController {
  public Controller4() {
  }

  @Override
  public void send(Router from, Router router, Packet packet) {
    new Thread(() -> {
      if (from != null) {
        if (packet.getTTL() == 1) {
          System.out.println("Reach TTL on Router: " + router.getIp() + " History: " + packet.getRoutersHistory());
          return;
        }
        packet.decrementTTL();
      }

      if (packet.getRoutersHistory().contains(router.getIp())) {
        return;
      }

      packet.addRouterToHistory(router.getIp());

      if (router.getIp().equals(packet.getReceiver())) {
        System.out.println(
            "CHEGOU AO DESTINO: " + packet.getMessage() + " FROM: " + from.getIp() + " TTL: " + packet.getTTL()
                + " History: " + packet.getRoutersHistory());
        return;
      }

      for (Connection connection : router.getConnections()) {
        Router to;
        if (connection.getCon1().getIp().equals(router.getIp())) {
          to = connection.getCon2();
        } else {
          to = connection.getCon1();
        }

        if (from != null && from.getIp().equals(to.getIp())) {
          continue; // skip sending to the same router
        }

        System.out.println(
            "ROUTER: " + router.getIp() + " ENVIANDO PARA: " + to.getIp());

        to.getController().send(router, to, packet.duplicate());
      }
    }).start();
  }
}
