package step;

import step.Console;
import step.Core;
import step.Database;
import step.SystemConsole;

import java.io.IOException;

public class Application {
  public static void main(String[] args) throws Exception {
    Console console = new SystemConsole();
    Database database = new Database();
    Core app = new Core(console, database);
    app.run();
  }
}
