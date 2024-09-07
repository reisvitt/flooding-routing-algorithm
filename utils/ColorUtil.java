package utils;

import java.util.Random;

import javafx.scene.paint.Color;

public class ColorUtil {
  // Função para gerar uma cor aleatória
  public static Color generateRandomColor() {
    Random random = new Random();
    double red = random.nextDouble(); // Valor aleatório entre 0 e 1
    double green = random.nextDouble(); // Valor aleatório entre 0 e 1
    double blue = random.nextDouble(); // Valor aleatório entre 0 e 1
    return new Color(red, green, blue, 1.0); // Alpha 1.0 (opacidade total)
  }
}
