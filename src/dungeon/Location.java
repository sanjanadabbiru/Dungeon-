package dungeon;

import java.util.List;

/**
 * Location refers to where the player exists at particular point
 * in time and after a move of the player. Location can refer to a
 * cave or a tunnel. Coordinates of a location specify the player
 * is at currently. Caves can have treasure.
 */
public interface Location {
  /**
   * Returns the x coordinate of a cave or a tunnel.
   *
   * @return x coordinate
   */
  int getX();

  /**
   * Returns the y coordinate of a cave or a tunnel.
   *
   * @return y coordinate
   */
  int getY();

  /**
   * Checks whether the current location is a tunnel.
   *
   * @return true when it's a tunnel
   */
  boolean isTunnel();

  /**
   * When the entrances are 2 then the current location
   * is set as a tunnel.
   */
  void setTunnel();

  /**
   * Returns the list of treasure at the current location
   * when called.
   *
   * @return List of treasure
   */
  List<Treasure> getTreasureList();

  /**
   * Takes in a treasure list and sets the treasure list
   * of location randomly from the Enum Treasure.
   *
   * @param treasureList List of Treasure
   */
  void setTreasureList(List<Treasure> treasureList);

  /**
   * Sets a monster in a cave in a random fashion. The cave contains this monster
   * until it is slain by the player.
   */
  void setMonster();

  /**
   * Checks whether the particular cave has a monster residing in it. If the monster
   * exists in the cave it returns true or else false. Also used to check whether monster
   * does not reside in a tunnel.
   *
   * @return true if there is a monster residing in cave , false otherwise
   */
  boolean isMonster();

  /**
   * Sets a list of arrows to be assigned to a location. The player picks up arrows from this
   * particular location depending on the availability.
   *
   * @param arrowList List of Arrows
   */
  void setArrowList(List<Arrow> arrowList);

  /**
   * Gets the list of arrows that are assigned to a particular location. Lets the user know how
   * many arrows are present or whether they are available or not.
   *
   * @return List of Arrows
   */
  List<Arrow> getArrowList();

  /**
   * Sets the monster health by decreasing it by 50 to make sure that when a monster is hit once
   * the monster's health decreases and if it is hit again then the monster is killed completely.
   */
  void setMonsterHealth();

  /**
   * Returns the monster's health to check whether a monster is in complete, partial health or
   * killed by the player.
   *
   * @return flag that displays an appropriate message
   */
  int getMonsterHealth();

  /**
   * Method to remove a monster from a cave once it has been slain by the player. This also
   * ensures that the monster's smell also has been removed.
   */
  void removeMonster();

  /**
   * Sets a thief in a cave in a random fashion. The cave contains this thief which
   * steals the treasure of the player when it reaches the cave.
   */
  void setThief();

  /**
   * Checks whether the particular cave has a thief residing in it. If the thief
   * exists in the cave it returns true or else false.
   *
   * @return true if there is a thief residing in cave , false otherwise
   */
  boolean isThief();

}
