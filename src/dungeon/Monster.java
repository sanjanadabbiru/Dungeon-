package dungeon;

/**
 * Interface that creates a monster that has an attribute of health.
 * The monster is removed when the health is 0.
 */
public interface Monster {
  /**
   * Sets the health of a monster. Decreases it by half when called when a monster
   * is hit once. The health further decreases when the monster is hit again.
   * The health becomes zero when the monster is killed.
   */
  void setHealth();

  /**
   * Gets the health of a monster to know whether the monster is damaged. The player can understand
   * whether the monster has been damaged to kill it again.
   *
   * @return flag whether the monster has been damaged.
   */
  int getHealth();
}
