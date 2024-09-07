package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.Connection;
import model.Router;
import service.config.ConfigReader;
import service.flooding.FloodingAlgorithm;
import service.flooding.FloodingController2;

public class FloodingAlgorithmController implements Initializable {
  @FXML
  private Pane network;

  private ArrayList<Router> routers;
  private FloodingAlgorithm controller;

  public void routerPositions() {
    // area of pane

    double x = this.network.getLayoutX();
    double y = this.network.getLayoutY();
    double width = this.network.getWidth();
    double height = this.network.getHeight();
    double centerX = (x + width) / 2;
    double centerY = ((y + height) / 2) - 100;
    double radius = 150;

    double angleStep = 360.0 / this.routers.size();

    for (int i = 0; i < this.routers.size(); i++) {
      double angle = Math.toRadians(i * angleStep);
      double routerX = centerX + radius * Math.cos(angle);
      double routerY = centerY + radius * Math.sin(angle);
      this.addRouter(this.routers.get(i), routerX, routerY);
    }
  }

  public void addRouter(Router router, double x, double y) {
    Text text = new Text(router.getIp());
    text.getStyleClass().add("router-text");

    StackPane stack = new StackPane();
    // Renderizar aqui se necessário
    stack.setLayoutX(x);
    stack.setLayoutY(y);
    stack.getStyleClass().add("router");
    stack.getChildren().addAll(text);

    network.getChildren().add(stack);

    router.setStack(stack);
  }

  public void addConnection(Connection connection) {
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // load configs
    ConfigReader reader = new ConfigReader("backbone.txt");
    ArrayList<String> configs = reader.read();

    String firstLine = configs.get(0).replace(";", "");
    Integer size = Integer.parseInt(firstLine);

    routers = new ArrayList<Router>(size);
    controller = new FloodingController2();

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

    Platform.runLater(() -> {
      this.routerPositions();
    });
  }

}
