package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class implements the Player interface and all the methods
 * in it. It implements functionality such as getting the position of
 * player in the dungeon, setting the treasure bag of a player as well
 * as configuring a set of moves for a player as they move through
 * the dungeon.
 */
public final class DungeonPlayer implements Player {
  private final List<Treasure> treasureList;
  private final List<Arrow> arrowList;
  private Location playerPosition;
  private Location endCave;
  private List<Directions> possibleMoves;
  private int row;
  private int col;
  private Smell smell;

  /**
   * Constructs a player which contains the current position
   * of the player as well as the treasure list of the player.
   * The treasure list gets added to the player's bag when picked up.
   */
  public DungeonPlayer() {
    this.smell = null;
    treasureList = new ArrayList<>();
    arrowList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      arrowList.add(Arrow.CROOKED_ARROW);
    }
    playerPosition = new Cave(-1, -1);
  }

  @Override
  public void setPlayerPosition(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    this.playerPosition = location;
  }

  @Override
  public Location getPlayerPosition() {
    return this.playerPosition;
  }

  @Override
  public Location getEndCave() {

    return this.endCave;
  }

  @Override
  public void setEndCave(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("location cannot be null");
    }
    this.endCave = location;
  }

  /**
   * Sets the column and row of the grid of the dungeon.
   * The dungeon is constructed with these paramaters and these values
   * are sent through parameters to be used in finding possible moves.
   *
   * @param row row value
   * @param col column value
   */
  @Override
  public void setRowCol(int row, int col) {
    this.row = row - 1;
    this.col = col - 1;
  }

  /**
   * Sets the list of treasures collected by the player as
   * it moves through the dungeon while picking up available
   * treasure in a cave.
   *
   * @param treasureList list of treasures
   */
  @Override
  public void setTreasureList(List<Treasure> treasureList) {
    if (treasureList == null) {
      throw new IllegalArgumentException("treasure list cannot be null");
    }
    this.treasureList.addAll(treasureList);
  }

  @Override
  public List<Treasure> getTreasureList() {
    List<Treasure> treasureListCopy = this.treasureList;
    return treasureListCopy;
  }

  /**
   * List of possible moves derived from the list of edges
   * pertaining to the dungeon creation. The possible moves
   * of a player is defined here - NORTH, SOUTH, EAST, WEST.
   *
   * @param allEdges list of edges from a cave to another
   */
  @Override
  public void possibleMoves(Map<Location, List<Location>> allEdges) {
    if (allEdges == null) {
      throw new IllegalArgumentException("Edges cannot be null");
    }
    List<Location> neighborCaves = allEdges.get(playerPosition);
    possibleMoves = new ArrayList<>();

    for (Location neighbor : neighborCaves) {
      if (((playerPosition.getX() - neighbor.getX() == 1)
              || (playerPosition.getX() - neighbor.getX() == -row))
              && (playerPosition.getY() - neighbor.getY() == 0)) {
        possibleMoves.add(Directions.NORTH);
      } else if (((playerPosition.getX() - neighbor.getX() == -1)
              || (playerPosition.getX() - neighbor.getX() == row))
              && (playerPosition.getY() - neighbor.getY() == 0)) {
        possibleMoves.add(Directions.SOUTH);
      } else if ((playerPosition.getX() - neighbor.getX() == 0)
              && ((playerPosition.getY() - neighbor.getY() == -1)
              || (playerPosition.getY() - neighbor.getY() == col))) {
        possibleMoves.add(Directions.EAST);
      } else if ((playerPosition.getX() - neighbor.getX() == 0)
              && ((playerPosition.getY() - neighbor.getY() == 1)
              || (playerPosition.getY() - neighbor.getY() == -col))) {
        possibleMoves.add(Directions.WEST);
      } else {
        throw new IllegalArgumentException("No more moves");
      }
    }

  }

  /**
   * Gets a list of possible moves which the player can make
   * while moving through the dungeon to make a valid move.
   *
   * @return List of possible directions
   */
  @Override
  public List<Directions> getPossibleMoves() {
    List<Directions> copy = new ArrayList<>(this.possibleMoves);
    return copy;
  }

  @Override
  public void setSmell(Smell smell) {
    if (smell == null) {
      throw new IllegalArgumentException("Smell cannot be null");
    }
    this.smell = smell;
  }

  @Override
  public Smell getSmell() {
    return this.smell;
  }

  @Override
  public void setArrowList(List<Arrow> arrowList) {
    if (arrowList == null) {
      throw new IllegalArgumentException("arrow list cannot be null");
    }
    this.arrowList.addAll(arrowList);
  }

  @Override
  public void removeTreasure() {
    this.treasureList.removeAll(treasureList);
  }

  @Override
  public void removeArrow() {
    if (arrowList == null) {
      throw new IllegalArgumentException("arrow list cannot be null");
    }
    this.arrowList.remove(arrowList.size() - 1);
  }

  @Override
  public List<Arrow> getArrowList() {
    List<Arrow> arrowListCopy = this.arrowList;
    return arrowListCopy;
  }


}
