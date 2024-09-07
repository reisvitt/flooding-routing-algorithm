package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.Connection;
import model.Packet;
import model.Router;
import model.Store;
import model.TitleControllerPair;
import service.config.ConfigReader;
import service.flooding.FloodingAlgorithm;
import service.flooding.FloodingController1;
import service.flooding.FloodingController2;
import service.flooding.FloodingController3;
import service.flooding.FloodingController4;

public class FloodingAlgorithmController implements Initializable {
  @FXML
  private Pane network;

  @FXML
  private TextField ttlInput;

  @FXML
  private ComboBox<String> listSenders, listReceivers;

  @FXML
  private ComboBox<TitleControllerPair> listAlgorithms;

  @FXML
  private Slider velocity;

  @FXML
  private Button startButton, stopButton;

  Text messageCount;

  private ArrayList<Router> routers;
  private FloodingAlgorithm controller;
  private Store store;

  public void routerPositions() {
    // area of pane

    double x = this.network.getLayoutX();
    double y = this.network.getLayoutY();
    double width = this.network.getWidth();
    double height = this.network.getHeight();
    double centerX = ((x + width) / 2) - 20;
    double centerY = ((y + height) / 2) - 100;

    double radius = centerY / 1.3;

    double angleStep = 360.0 / this.routers.size();

    for (int i = 0; i < this.routers.size(); i++) {
      double angle = Math.toRadians(i * angleStep);
      double routerX = centerX + radius * Math.cos(angle);
      double routerY = centerY + radius * Math.sin(angle);
      this.addRouter(this.routers.get(i), routerX, routerY);
    }

    for (int i = 0; i < this.routers.size(); i++) {
      this.routers.get(i).getConnections().forEach(conn -> {
        if (!conn.isRendered()) {
          this.addConnection(conn);
        }
      });
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
    connection.setRendered(true);

    Router con1 = connection.getCon1();
    Router con2 = connection.getCon2();

    double move = 20;

    double x1 = con1.getStack().getLayoutX() + move;
    double y1 = con1.getStack().getLayoutY() + move;

    double x2 = con2.getStack().getLayoutX() + move;
    double y2 = con2.getStack().getLayoutY() + move;

    Line line = new Line(x1, y1, x2, y2);
    line.getStyleClass().add("router-connection");

    this.network.getChildren().add(line);
    line.toBack();
  }

  @FXML
  public void start() {
    this.store.start();

    this.store.getMessageCounterProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        messageCount.setText("Mensagens: " + newValue);
      }
    });

    TitleControllerPair controlerPair = this.listAlgorithms.getSelectionModel().getSelectedItem();
    FloodingAlgorithm controller = controlerPair.getController();

    this.routers.forEach(router -> {
      router.setController(controller);
    });

    String message = "Hello, World!";
    Integer TTL = Integer.parseInt(this.ttlInput.getText());

    String from = this.listSenders.getValue();
    String to = this.listReceivers.getValue();
    Packet packet = new Packet(from, to, message, TTL);

    Router selectedRouter = null;
    for (Router router : this.routers) {
      if (router.getIp().equals(from)) {
        selectedRouter = router;
        break;
      }
    }

    if (selectedRouter == null) {
      return;
    }

    selectedRouter.send(null, packet);
  }

  @FXML
  public void stop() {
    this.store.getRunningProperty().set(false);
  }

  private void startAlgorithms() {
    TitleControllerPair option1 = new TitleControllerPair("Todos", new FloodingController1());
    TitleControllerPair option2 = new TitleControllerPair("Todos, exceto remetente", new FloodingController2());
    TitleControllerPair option3 = new TitleControllerPair("Todos, exceto remetente + TTL", new FloodingController3());
    TitleControllerPair option4 = new TitleControllerPair("Todos, exceto remetente + TTL + histórico",
        new FloodingController4());

    this.listAlgorithms.getItems().addAll(option1, option2, option3, option4);
    this.listAlgorithms.setValue(option1);
  }

  private void startMessageCout() {
    messageCount = new Text("Mensagens: 0");
    double width = network.getWidth();

    messageCount.setY(0);
    messageCount.setX(width - 120);

    this.network.getChildren().add(messageCount);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    store = Store.getInstance();

    // load configs
    ConfigReader reader = new ConfigReader("backbone.txt");
    ArrayList<String> configs = reader.read();

    String firstLine = configs.get(0).replace(";", "");
    Integer size = Integer.parseInt(firstLine);

    routers = new ArrayList<Router>(size);
    controller = new FloodingController3();

    configs.subList(1, configs.size()).forEach(config -> {
      String[] ipAndRouter = config.split(";");
      String ip = ipAndRouter[0];
      String connectionIp = ipAndRouter[1];
      Integer cost = Integer.parseInt(ipAndRouter[2]);

      Router router = new Router(ip, this.network);
      Router connectionRouter = new Router(connectionIp, this.network);

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
      this.startMessageCout();
    });

    this.routers.forEach(router -> {
      // load list of senders
      listSenders.getItems().add(router.getIp());

      // load list of receivers
      listReceivers.getItems().add(router.getIp());
    });

    listSenders.setValue(this.routers.get(0).getIp());

    listReceivers.setValue(this.routers.get(this.routers.size() - 1).getIp());

    this.ttlInput.setText("5");

    this.startAlgorithms();

    this.ttlInput.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) { // Permite apenas dígitos
          ttlInput.setText(oldValue);
        }
      }
    });

    this.store.getRunningProperty().addListener((observable, oldValue, newValue) -> {
      this.stopButton.setDisable(!newValue);
      this.startButton.setDisable(newValue);

      if (newValue) {
        this.stopButton.setOpacity(1);
        this.startButton.setOpacity(0.45);
      } else {
        this.stopButton.setOpacity(0.45);
        this.startButton.setOpacity(1);
      }
    });

    velocity.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        store.setVelocity(newValue.intValue());
      }
    });
  }
}
