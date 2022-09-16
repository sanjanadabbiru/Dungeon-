package dungeon;

import java.util.List;
import java.util.Map;

/**
 * A Player moves around in the dungeon and collects available
 * treasure. A player is assigned a start cave as the initial position
 * and also contains a treasure list which the player picks up from a
 * cave.
 */
public interface Player {
  /**
   * Sets the position of the player location
   * to the location sent as a parameter to this method.
   *
   * @param location cave or tunnel
   */
  void setPlayerPosition(Location location);

  /**
   * Returns the position of the player in terms of it's
   * location whether it's a cave or a tunnel.
   *
   * @return Location object
   */
  Location getPlayerPosition();

  /**
   * Returns the end cave initialised in the dungeon randomly.
   * The end cave is displayed to the user using this method.
   *
   * @return Location object
   */
  Location getEndCave();

  /**
   * Sets a cave as the end cave and saves it for the player
   * to know where the end cave is.
   *
   * @param location Location object
   */
  void setEndCave(Location location);

  /**
   * Sets the column and row of the grid of the dungeon.
   * The dungeon is constructed with these paramaters and these values
   * are sent through parameters to be used in finding possible moves.
   *
   * @param row row value
   * @param col column value
   */
  void setRowCol(int row, int col);

  /**
   * Sets the list of treasures collected by the player as
   * it moves through the dungeon while picking up available
   * treasure in a cave.
   *
   * @param treasureList list of treasures
   */
  void setTreasureList(List<Treasure> treasureList);

  /**
   * Returns a list of treasures that the player bag contains
   * picked up from an available cave.
   *
   * @return List of treasures.
   */
  List<Treasure> getTreasureList();

  /**
   * List of possible moves derived from the list of edges
   * pertaining to the dungeon creation. The possible moves
   * of a player is defined here - NORTH, SOUTH, EAST, WEST.
   *
   * @param allEdges list of edges from a cave to another
   */
  void possibleMoves(Map<Location, List<Location>> allEdges);

  /**
   * Gets a list of possible moves which the player can make
   * while moving through the dungeon to make a valid move.
   *
   * @return List of possible directions
   */
  List<Directions> getPossibleMoves();

  /**
   * Sets the smell of monster that a player can smell. Takes in 2 values of smell : pungent and
   * less pungent. Depending on the smell the player can detect the proximity of a monster.
   *
   * @param smell pungent or less pungent
   */
  void setSmell(Smell smell);

  /**
   * Gets the smell that the player can smell from a location. Can be of pungent or less pungent
   * smell. Depending on the smell the player gets to decide whether a monster is nearby.
   *
   * @return Smell
   */
  Smell getSmell();

  /**
   * Removes the arrow from the player's arrow list when the player shoots an arrow. Used to
   * display when there are no more arrows to shoot.
   */
  void removeArrow();

  /**
   * Gets the list of arrows that the player has currently after removing or adding
   * arrows in the arrow list.
   *
   * @return List of Arrows
   */
  List<Arrow> getArrowList();

  /**
   * Adds arrows to the list of arrows when the player picks up arrows from a location
   * that contains available arrows there.
   *
   * @param arrowList List of Arrows
   */
  void setArrowList(List<Arrow> arrowList);

  /**
   * This method empties the player's treasure list completely when the player encounters a thief
   * in the dungeon. If there is a player and a thief in the same position , then the player's
   * treasure list is removed.
   */
  void removeTreasure();

}
