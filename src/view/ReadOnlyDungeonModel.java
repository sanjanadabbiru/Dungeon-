package view;

import dungeon.Directions;
import dungeon.Location;
import dungeon.Player;
import dungeon.Smell;

import java.util.List;
import java.util.Map;



/**
 * Interface that gives a read only model of the Dungeon. It gives access to only those methods
 * that the view wants to use. Consists of methods such as player location details, getting
 * player info on the arrow and treasure count, the location details on the arrow, treasure count
 * or even if a monster exists in that location.
 */
public interface ReadOnlyDungeonModel {

  /**
   * Returns a string where the details of a player such as the next possible directions that
   * the player can move towards, the treasure and arrow count of a location that the player is in
   * right now.
   * @return String
   */
  String playerLocationDetails();

  /**
   * Returns a map of a location and the list of possible direction from that particular location
   * useful for when the player wants to know if a moving action is possible from that location.
   * @return HashMap
   */
  Map<Location, List<Directions>> getState();

  /**
   * Returns a flag whether the player is alive or not. Depending on that the player details are
   * displayed or else it doesn't.
   * @return int flag
   */
  int getPlayer();

  /**
   * Returns a location of the start cave so the player starts from that location and plays the
   * game from that location.
   * @return Location
   */
  Location getStartCave();

  /**
   * Gets the smell a player smells from it's current location to where a monster resides. The
   * appropriate image is displayed based on no pungent smell, less pungent smell, more pungent
   * smell.
   * @return Smell smell
   */
  Smell getSmell();

  /**
   * Gets the player's details such as arrow count and treasure count. The player details are
   * displayed appropriately based on the player's details updated in the model.
   * @return Player player
   */
  Player getPlayerObject();

  /**
   * Returns the column which is stored and updated in the model.
   * @return int column
   */
  int getCol();

  /**
   * Returns the row which is stored and updated in the model.
   * @return int row
   */
  int getRow();

  /**
   * Returns the column which is stored and updated in the model.
   * @return Boolean game over condition
   */
  boolean isGameOver();
}
