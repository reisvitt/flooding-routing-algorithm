package service.flooding;

import model.Connection;
import model.Packet;
import model.Router;

// all interfaces
public class FloodingController3 implements FloodingAlgorithm {
  public FloodingController3() {
  }

  @Override
  public void send(Router from, Router router, Packet packet) {
    if (packet.getTTL() <= 1) {
      System.out.println("Reach TTL on Router: " + router.getIp() + " From Router: " + from.getIp());
      router.showError(from, packet);
      return;
    }

    if (from != null) {
      packet.decrementTTL();
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
          "ROUTER: " + router.getIp() + " CONNECTIONS: " + router.getConnections() + " ENVIANDO PARA: " + to.getIp());

      Packet newPacket = packet.duplicate();
      newPacket.addLineHistory(connection.getLine());
      router.flush(to, newPacket);
    }
  }
}
