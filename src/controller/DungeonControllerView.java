package controller;

import dungeon.Directions;
import dungeon.Dungeon;
import dungeon.DungeonClass;
import dungeon.DungeonPlayer;
import dungeon.Player;
import view.DungeonView;
import view.Features;
import view.ReadOnlyDungeonModel;

import java.util.Random;


/**
 * Implements a class for representing the view of the Dungeon model. Implements the Features
 * interface which consists of methods which represent the action required from the player
 * when navigating through the dungeon.
 */
public class DungeonControllerView implements Features {

  private final DungeonView dv;
  private Dungeon dm;
  private ReadOnlyDungeonModel model;
  private int row1;
  private int col1;
  private int numberOfMonsters;
  private int interconnectivity1;
  private boolean isWrapped1;
  private int x;
  private int seed;


  /**
   * Constructs Dungeon controller view that initalises all the game settings the player sets and
   * creates the model. The view calls the features method for callbacks.
   */
  public DungeonControllerView(DungeonView dv, Dungeon dm) {
    row1 = 6;
    col1 = 6;
    numberOfMonsters = 1;
    interconnectivity1 = 1;
    isWrapped1 = false;
    x = 100;
    this.dm = dm;
    this.dv = dv;
    Random rand = new Random();
    seed = rand.nextInt(9000) + 1000;
    dv.setFeatures(this);
  }

  @Override
  public void processInput(String row, String col, String interconnectivity, Boolean isWrapped,
                           String percent, String monsters) {
    try {
      Random newRand = new Random();
      newRand.setSeed(seed);
      this.row1 = Integer.parseInt(row);
      this.col1 = Integer.parseInt(col);
      this.interconnectivity1 = Integer.parseInt(interconnectivity);
      this.isWrapped1 = isWrapped;
      this.x = Integer.parseInt(percent);
      this.numberOfMonsters = Integer.parseInt(monsters);
      if (row1 < 6 || row1 < 0) {
        dv.showErrorMessage("Row cannot be lesser than 0 or lesser than 6!");
      }
      if (col1 < 0 || col1 < 6) {
        dv.showErrorMessage("Column cannot be lesser than 0 or lesser than 6!");
      }
      if (interconnectivity1 < 0) {
        dv.showErrorMessage("Interconnectivity cannot be lesser than 0!");
      }
      if (x < 0 || x > 100) {
        dv.showErrorMessage("Percentage cannot be lesser than 0 or greater than 100!");
      }
      if (numberOfMonsters < 1) {
        dv.showErrorMessage("Number of monsters cannot be lesser than 0!");
      }
      Player player = new DungeonPlayer();
      dm = new DungeonClass(row1, col1, interconnectivity1, isWrapped1, x,
              player, numberOfMonsters, newRand);

      dv.resetFlags();
      dv.setModel(dm);
    } catch (NumberFormatException nie) {
      dv.showErrorMessage("Input not valid!");
    } catch (IllegalArgumentException ie) {
      dv.showErrorMessage("Enter values again please!");
    }
    this.dv.addClickListener(this);
    dv.refresh();
    dv.resetFocus();
  }

  @Override
  public int movePlayer(Directions direction) {
    return dm.movePlayer(direction);
  }

  @Override
  public void restartGame() {
    try {
      Random newRand = new Random();
      newRand.setSeed(seed);
      Player playerNew = new DungeonPlayer();
      dm = new DungeonClass(row1, col1, interconnectivity1, isWrapped1, x,
              playerNew, numberOfMonsters, newRand);
      dv.resetFlags();
      dv.setModel(dm);
    } catch (NumberFormatException nie) {
      dv.showErrorMessage("Input not valid!");
    }
    this.dv.addClickListener(this);
    dv.refresh();
    dv.resetFocus();
  }

  @Override
  public void pickUpArrow() {
    dm.pickUpArrow();
  }

  @Override
  public void pickUpTreasure() {
    dm.pickUpTreasure();
  }

  @Override
  public int shootArrow(Directions direction, int distance) {

    return dm.shootArrow(direction, distance);
  }

  @Override
  public void playGame() {
    this.dv.addClickListener(this);
    this.dv.makeVisible();
  }

  @Override
  public int handleCellClick(Directions direction) {
    try {
      int moveFlag = dm.movePlayer(direction);
      dv.refresh();
      return moveFlag;
    } catch (IllegalArgumentException ie) {
      dv.showErrorMessage("Cannot move to this position!");
    } catch (IllegalStateException is) {
      dv.showErrorMessage("Game is over!");

    }
    return -10;
  }

}
