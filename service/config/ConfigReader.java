package service.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigReader {
  private String pathname;

  public ConfigReader(String pathname) {
    this.pathname = pathname;
  }

  public ArrayList<String> read() {
    ArrayList<String> array = new ArrayList<String>();

    try {
      File backbone = new File(this.pathname);
      Scanner reader = new Scanner(backbone);
      while (reader.hasNextLine()) {
        String data = reader.nextLine();
        array.add(data);
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return array;
  }
}
