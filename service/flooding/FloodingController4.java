package service.flooding;

import model.Connection;
import model.Packet;
import model.Router;

// all interfaces
public class FloodingController4 implements FloodingAlgorithm {
  public FloodingController4() {
  }

  @Override
  public void send(Router from, Router router, Packet packet) {
    if (from != null) {
      if (packet.getTTL() == 1) {
        System.out.println("Reach TTL on Router: " + router.getIp() + " History: " + packet.getRoutersHistory());
        router.showError(from, packet);
        return;
      }
      packet.decrementTTL();
    }

    if (packet.getRoutersHistory().contains(router.getIp())) {
      router.showError(from, packet);
      return;
    }

    packet.addRouterToHistory(router.getIp());

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

      Packet newPacket = packet.duplicate();
      newPacket.addLineHistory(connection.getLine());
      router.flush(to, newPacket);
    }
  }
}
