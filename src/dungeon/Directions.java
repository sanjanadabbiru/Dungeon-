package dungeon;


/**
 * Direction enum that specifies the different directions
 * a player can move to from their current location.
 * NORTH , SOUTH, EAST, WEST.
 */
public enum Directions {
  NORTH(1, 0), SOUTH(-1, 0), WEST(0, 1), EAST(0, -1);
  private final int x;
  private final int y;


  private Directions(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getDirectionX() {

    return this.x;
  }

  public int getDirectionY() {

    return this.y;
  }


}
