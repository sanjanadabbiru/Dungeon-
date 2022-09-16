package controller;

import dungeon.Dungeon;
import dungeon.DungeonClass;
import dungeon.DungeonPlayer;
import dungeon.Player;

import java.io.InputStreamReader;
import java.util.Random;


/**
 * Driver class that initially starts the dungeon creation
 * and allows movement of player as per user interactivity.
 * It also displays player location and treasure details.
 * Allows user input to pick up treasure as well as exit the maze.
 */
public class SimpleDriver {

  /**
   * Method allows initialisation of player and dungeon
   * objects. Also allows creation of random object to make
   * sure that the dungeon is created randomly as well all other
   * parameters. Gives a menu system to allow users to choose
   * which move to make or whether to pick a treasure or not.
   */
  public static void main(String[] args) {
    int flag = 100;
    int row = Integer.parseInt(args[0]);
    if (row < 6 || row < 0) {
      throw new IllegalArgumentException("Row cannot be lesser than 0 or lesser than 6");
    }
    int column = Integer.parseInt(args[1]);
    if (column < 0 || column < 6) {
      throw new IllegalArgumentException("Column cannot be lesser than 0 or lesser than 6");
    }
    int interconnectivity = Integer.parseInt(args[2]);
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("degree of interconnectivity cannot be negative");
    }
    Boolean isWrapped = Boolean.parseBoolean(args[3]);
    int x = Integer.parseInt(args[4]);
    if (x < 0 || x > 100) {
      throw new IllegalArgumentException("percentage of caves holding "
              + "treasure cannot be negative or over 100");
    }
    int monsters = Integer.parseInt(args[5]);
    if (monsters < 1) {
      throw new IllegalArgumentException("Number of monsters cannot be less than 1");
    }
    Player player = new DungeonPlayer();
    Random rand = new Random();
    Dungeon ud = new DungeonClass(row, column, interconnectivity, isWrapped, x,
            player, monsters, rand);

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    new DungeonConsoleController(input, output).playGame(ud);
  }
}
