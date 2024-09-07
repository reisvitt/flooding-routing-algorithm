package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SplashController implements Initializable {
  @FXML
  private VBox mainBox;

  public void goToMainScreen() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/FloodingAlgorithmScreen.fxml"));
      Parent newScreen = fxmlLoader.load();
      Stage stage = (Stage) mainBox.getScene().getWindow();
      stage.setTitle("Algoritmo de Inundação");

      double screenWidth = Screen.getPrimary().getBounds().getWidth();
      double screenHeight = Screen.getPrimary().getBounds().getHeight();

      double windowWidth = screenWidth * 1;
      double windowHeight = screenHeight * 1;
      stage.setScene(new Scene(newScreen, windowWidth, windowHeight));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Agendar a execução do método após 2 segundos
    scheduler.schedule(() -> {
      Platform.runLater(() -> goToMainScreen());
    }, 2, TimeUnit.SECONDS);

    // Fechar o scheduler quando não for mais necessário
    scheduler.shutdown();

  }
}
