
import controller.DungeonConsoleController;
import dungeon.Dungeon;
import dungeon.DungeonClass;
import dungeon.DungeonPlayer;
import dungeon.Player;
import controller.DungeonControllerView;
import view.DungeonView;
import view.DungeonViewClass;

import java.io.InputStreamReader;
import java.util.Random;


/**
 * Main class to faciliate both types of controllers : synchronous and asynchronous. Controllers
 * that implement such a pre-defined sequence are called synchronous controllers. If  the
 * program does not execute a pre-defined sequence of operations, but rather executes
 * depending on user input, the program uses an asyncronous controller.
 * Most programs with graphical user interfaces use an asynchronous controller.
 */
public class Main {
  /**
   * The main method takes in command line arguments. If the user enters the command line arguments
   * then the text based controller game opens up. If the user does not enter any command line
   * arguments then the GUI based controller opens up.
   *
   * @param args String args[]
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      Player player = new DungeonPlayer();
      Random rand = new Random();
      Dungeon dm = new DungeonClass(6, 6, 1, false,
              100, player, 5, rand);
      DungeonView dv = new DungeonViewClass(dm);
      DungeonControllerView controller = new DungeonControllerView(dv, dm);
      controller.playGame();
    } else {
      int row = Integer.parseInt(args[0]);
      int column = Integer.parseInt(args[1]);
      int interconnectivity = Integer.parseInt(args[2]);
      Boolean isWrapped = Boolean.parseBoolean(args[3]);
      int x = Integer.parseInt(args[4]);
      int monsters = Integer.parseInt(args[5]);
      Player player = new DungeonPlayer();
      Random rand = new Random();
      Dungeon ud = new DungeonClass(row, column, interconnectivity, isWrapped, x,
              player, monsters, rand);

      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      new DungeonConsoleController(input, output).playGame(ud);
    }
  }
}
