package test.mockdungeonmodelnew;

import dungeon.Directions;
import dungeon.Dungeon;
import dungeon.Graph;
import dungeon.Location;
import dungeon.Player;
import dungeon.Smell;

import java.util.List;
import java.util.Map;



/**
 * Class that implements the mock model of the Dungeon class. It returns a dummy string for
 * testing in each method to make sure the controller is returning the correct value.
 */
public class MockDungeonModel implements Dungeon {
  private StringBuilder log;

  /**
   * Constructs a dungeon model for testing purposes
   * which takes in input string.
   *
   * @param log string
   */
  public MockDungeonModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public int movePlayer(Directions direction) {
    log.append(direction.toString());
    return 0;
  }

  @Override
  public int pickUpTreasure() {
    log.append("Picking up treasure...\n");
    return 0;
  }

  @Override
  public String playerTreasureCollected() {
    log.append("Treasure collected...\n");
    return null;
  }

  @Override
  public String playerLocationDetails() {
    log.append("Location details of the player...\n");
    return null;
  }

  @Override
  public Location getStartCave() {
    log.append("Start cave of the dungeon...\n");
    return null;
  }

  @Override
  public List<Graph.Edge> getPotentialPaths() {
    log.append("Potential paths from this location...\n");
    return null;
  }

  @Override
  public List<List<Location>> getCaveList() {
    return null;
  }

  @Override
  public Map<Location, List<Location>> getAllEdges() {
    return null;
  }

  @Override
  public List<Graph.Edge> getResult() {
    return null;
  }

  @Override
  public void detectSmell() {
    log.append("Detecting smell...\n");
  }

  @Override
  public int pickUpArrow() {
    log.append("Picking up arrow..\n");
    return 0;
  }

  @Override
  public int shootArrow(Directions direction, int distance) {
    log.append(Directions.NORTH).append(String.valueOf(distance));
    return 0;
  }

  @Override
  public boolean isGameOver() {
    log.append("Game over...\n");
    return false;
  }

  @Override
  public Map<Location, List<Directions>> getState() {
    return null;
  }

  @Override
  public Smell getSmell() {
    return null;
  }

  @Override
  public int getPlayer() {
    return 0;
  }

  @Override
  public Player getPlayerObject() {
    return null;
  }

  @Override
  public int getRow() {
    log.append("Row...\n");
    return 0;
  }

  @Override
  public int getCol() {
    log.append("Column...\n");
    return 0;
  }
}
