package dungeon;

import view.ReadOnlyDungeonModel;

import java.util.List;
import java.util.Map;


/**
 * A dungeon is where the player moves through caves and tunnels
 * to choose to pick up treasure and reach the end cave. The dungeon
 * interface is responsible for moving the player through the dungeon,
 * picking up treasure, display the treasure collected by the player
 * as well as display player location details.
 */
public interface Dungeon extends ReadOnlyDungeonModel {
  /**
   * Moves player according to four directions mentioned in the
   * enumeration Directions - NORTH,SOUTH,EAST,WEST.
   *
   * @param direction direction player can move to
   * @return -1 if invalid move
   */
  int movePlayer(Directions direction);

  /**
   * Method that allows the player to pick up treasure and checks whether
   * the location is a cave or a tunnel. Since caves contain treasure and
   * tunnels do not, the conditions are checked here and treasure is added to the
   * player's treasure bag.
   */
  int pickUpTreasure();

  /**
   * Returns a description of the player's treasure after collecting
   * when it encounters a treasure bag in a cave.
   *
   * @return description of treasure collected
   */
  String playerTreasureCollected();

  /**
   * Returns a description of the player's possible moves
   * from the current position of a player. It also displays the
   * treasure in a particular cave if there is treasure present.
   *
   * @return String description of possible moves and treasure present in cave.
   */
  String playerLocationDetails();

  /**
   * Gets the location of start cave of the dungeon to ensure
   * that the player starts movement at the start cave in the dungeon.
   * @return Location object
   */
  Location getStartCave();

  /**
   * Gets a list of edges as potential paths that includes all the edges
   * that  make the graph and they are all connected.
   * @return List of edges
   */
  List<Graph.Edge> getPotentialPaths();

  /**
   * Gets a list of caves that make up the dungeon grid. Each vertex is a cave that allows
   * each edge to be made up of 2 caves connected.
   * @return List of location objects
   */
  List<List<Location>> getCaveList();

  /**
   * Gets a list of all the edges that exist in the graph. All of these edges
   * are connected and are a super set of all the potential set of edges.
   * @return Map of Location and List of Location objects
   */
  Map<Location, List<Location>> getAllEdges();

  /**
   * Gets a list of edges that make up the final result of the graph. These edges
   * are connected and is the final graph in which the user traverses through
   * @return List of Edges
   */
  List<Graph.Edge> getResult();

  /**
   * Method to detect a smell of an otyugh. Detects and displays less pungent smell can be
   * detected when there is a single Otyugh 2 positions from the player's current location.
   * Detects and displays a more pungent smell which either means that there is a single
   * Otyugh 1 position from the player's current location or that there are multiple Otyughs.
   */
  void detectSmell();

  /**
   * Method that allows the user to pick up an available arrow that exists in an cave or
   * a tunnel. Once picked up the arrows get added to arrow list of the player.
   * @return flag to display whether there are available arrows or no
   */
  int pickUpArrow();

  /**
   * Method to shoot an arrow in a valid direction and a valid distance of caves given by the user.
   * @param direction South,North,East,West
   * @param distance number of caves
   * @return flag to display messages if the arrow misses or arrow shoots an otyugh
   */
  int shootArrow(Directions direction, int distance);

  /**
   * Method that decides when the game is over. Conditions include if the player has reached the end
   * cave without being killed or if the player get killed by an otyugh.
   * @return true if game is over , false otherwise
   */
  boolean isGameOver();

}
