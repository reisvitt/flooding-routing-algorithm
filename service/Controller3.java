package service;

import model.Connection;
import model.Package;
import model.Router;

// all interfaces
public class Controller3 implements IController {
  public Controller3() {
  }

  @Override
  public void send(Router from, Router router, Package packge) {
    new Thread(() -> {
      if (from != null) {
        if (packge.getTTL() == 0) {
          System.out.println("Reach TTL on Router: " + router.getIp() + " From Router: " + from.getIp());
          return;
        }
        packge.decrementTTL();
      }

      if (router.getIp().equals(packge.getReceiver())) {
        System.out.println(
            "CHEGOU AO DESTINO: " + packge.getMessage() + " FROM: " + from.getIp() + " TTL: " + packge.getTTL());
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
            "ROUTER: " + router.getIp() + " CONNECTIONS: " + router.getConnections() + " ENVIANDO PARA: " + to.getIp());

        to.getController().send(router, to, packge.duplicate());
      }
    }).start();
  }
}
