package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A Cave is a generic location which may or may not contains
 * treasures. If the number of entrances is more than 2, then
 * the location is characterized as a cave and contains treasure.
 * Otherwise, it is a tunnel.
 */
public final class Cave implements Location {
  private List<Treasure> treasureList;
  private List<Arrow> arrowList;
  private final int x;
  private final int y;
  private boolean isTunnel;
  private Monster otyugh;
  private int thief;

  /**
   * Constructs a cave which takes in
   * coordinates that specify it's location and
   * also contains a treasure list.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Cave(int x, int y) {
    treasureList = new ArrayList<>();
    arrowList = new ArrayList<>();
    this.x = x;
    this.y = y;
    this.thief = 0;
    isTunnel = false;
    otyugh = null;
  }

  public Cave(Location that) {
    this(that.getX(), that.getY());
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public boolean isTunnel() {
    return this.isTunnel;
  }

  @Override
  public boolean isMonster() {
    return this.otyugh != null;
  }

  /**
   * Sets if the cave is a tunnel or no
   * depending on the number of entrances. If the
   * cave is a tunnel then no treasure is assigned.
   */
  @Override
  public void setTunnel() {
    this.isTunnel = true;
  }

  @Override
  public List<Treasure> getTreasureList() {
    List<Treasure> copyTreasure = this.treasureList;
    return copyTreasure;
  }

  @Override
  public void setTreasureList(List<Treasure> treasureList) {
    if (treasureList == null) {
      throw new IllegalArgumentException("Treasure list cannot be null");
    }
    this.treasureList = treasureList;
  }

  @Override
  public void setMonster() {
    this.otyugh = new MonsterClass();
  }

  @Override
  public void setMonsterHealth() {
    this.otyugh.setHealth();
  }

  @Override
  public int getMonsterHealth() {
    return this.otyugh.getHealth();
  }

  @Override
  public void removeMonster() {
    this.otyugh = null;
  }

  @Override
  public void setThief() {
    this.thief = 1;
  }

  @Override
  public boolean isThief() {
    return this.thief != 0;
  }


  @Override
  public void setArrowList(List<Arrow> arrowList) {
    if (arrowList == null) {
      throw new IllegalArgumentException("Arrow list cannot be null");
    }
    this.arrowList = arrowList;
  }

  @Override
  public List<Arrow> getArrowList() {
    List<Arrow> copyArrow = this.arrowList;
    return copyArrow;
  }

}
