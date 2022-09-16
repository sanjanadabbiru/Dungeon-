package dungeon;

/**
 * Class that implements the Monster interface. It initially sets the health of a monster
 * to 100. Decreases the health when it has been shot by the player. Also returns the health
 * of a monster.
 */
public class MonsterClass implements Monster {

  private int health;

  /**
   * Constructs a monster and sets the health to 100(full health).
   * The monster is created with full health and is updated when damaged or killed.
   */
  public MonsterClass() {
    this.health = 100;
  }

  @Override
  public void setHealth() {
    this.health -= 50;
  }

  @Override
  public int getHealth() {
    return this.health;
  }
}
