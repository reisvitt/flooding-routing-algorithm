import java.util.ArrayList;

import model.Connection;
import model.Package;
import model.Router;
import service.Controller3;
import service.IController;
import utils.ReadConfigFile;

public class Principal {
  public static void main(String[] args) {
    ReadConfigFile reader = new ReadConfigFile("backbone.txt");
    ArrayList<String> configs = reader.read();

    System.out.println("configs: " + configs.toString());

    String firstLine = configs.get(0).replace(";", "");
    Integer size = Integer.parseInt(firstLine);

    ArrayList<Router> routers = new ArrayList<Router>(size);
    IController controller = new Controller3();

    configs.subList(1, configs.size()).forEach(config -> {
      String[] ipAndRouter = config.split(";");
      String ip = ipAndRouter[0];
      String connectionIp = ipAndRouter[1];
      Integer cost = Integer.parseInt(ipAndRouter[2]);

      Router router = new Router(ip);
      Router connectionRouter = new Router(connectionIp);

      router.setController(controller);
      connectionRouter.setController(controller);

      if (routers.contains(router)) {
        Integer index = routers.indexOf(router);
        router = routers.get(index);
      } else {
        routers.add(router);
      }

      if (routers.contains(connectionRouter)) {
        Integer index = routers.indexOf(connectionRouter);
        connectionRouter = routers.get(index);
      } else {
        // add connection router to the routers
        routers.add(connectionRouter);
      }

      // add connection to current router
      Connection connection = new Connection(router, connectionRouter, cost);
      router.addConnection(connection);
      connectionRouter.addConnection(connection);
    });

    routers.forEach(router -> {
      System.out.println("ROUTER : " + router.toString());
    });

    String message = "Hello World";
    Integer TTL = 6;

    Package packge = new Package("A", "D", message, TTL);

    routers.get(0).getController().send(null, routers.get(0), packge);
  }
}