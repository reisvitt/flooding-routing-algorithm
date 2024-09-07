import controller.FloodingAlgorithmController;
import controller.SplashController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Principal extends Application {
  SplashController splashController = new SplashController();
  FloodingAlgorithmController floodingController = new FloodingAlgorithmController();

  @Override
  public void start(Stage stage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/view/SplashScreen.fxml"));

    double screenWidth = Screen.getPrimary().getBounds().getWidth();
    double screenHeight = Screen.getPrimary().getBounds().getHeight();

    double windowWidth = screenWidth * 1;
    double windowHeight = screenHeight * 1;

    Scene scene = new Scene(root, windowWidth, windowHeight);

    stage.setScene(scene);
    stage.setTitle("Algoritmo de inundação");
    stage.setResizable(true);
    stage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}