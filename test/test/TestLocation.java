package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.Cave;
import dungeon.Directions;
import dungeon.DungeonClass;
import dungeon.DungeonPlayer;
import dungeon.Location;
import dungeon.Player;
import dungeon.Treasure;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;





/**
 * Test class for location that allows test cases for checking
 * a particular coordinates of x and y are correct, checking validity of
 * whether a location is a tunnel or not. Whether the location is a cave
 * and if so does it hold treasure.
 */
public class TestLocation {
  private Cave cave;
  private Random rand;

  @Before
  public void setup() {
    rand = new Random();
    rand.setSeed(0);
  }

  @Test
  public void testGetX() {
    int x = 10;
    int y = 20;
    Location cave1 = new Cave(x, y);
    assertEquals(x, cave1.getX());
  }

  @Test
  public void testGetY() {
    int x = 10;
    int y = 20;
    Location cave1 = new Cave(x, y);
    assertEquals(y, cave1.getY());
  }

  @Test
  public void testValidEnum() {
    for (Directions directions : Directions.values()) {
      assertFalse(directions == null);
    }
  }

  @Test
  public void testIsTunnel() {
    int x = 10;
    int y = 20;
    Location cave1 = new Cave(x, y);
    assertFalse(cave1.isTunnel());
  }

  @Test
  public void testSetTunnel() {
    int x = 10;
    int y = 20;
    Location cave1 = new Cave(x, y);
    cave1.setTunnel();
    assertTrue(cave1.isTunnel());
  }

  @Test
  public void testGetTreasureList() {
    int x = 10;
    int y = 20;
    Location cave1 = new Cave(x, y);
    List<Treasure> treasureList = new ArrayList<>();
    treasureList.add(Treasure.DIAMONDS);
    treasureList.add(Treasure.SAPPHIRES);
    cave1.setTreasureList(treasureList);
    assertEquals(treasureList, cave1.getTreasureList());
  }

  @Test
  public void testSetTreasureList() {
    int x = 10;
    int y = 20;
    Location cave1 = new Cave(x, y);
    List<Treasure> treasureList = new ArrayList<>();
    treasureList.add(Treasure.DIAMONDS);
    treasureList.add(Treasure.SAPPHIRES);
    cave1.setTreasureList(treasureList);
    assertEquals(treasureList, cave1.getTreasureList());
  }


  @Test
  public void testTreasureUpdation() {
    Player player = new DungeonPlayer();
    rand = new Random();
    rand.setSeed(0);
    DungeonClass unwrappedDungeon = new DungeonClass(6, 6, 0,
            false, 60, player,10, rand);
    assertEquals("DIAMONDS, DIAMONDS\n"
            + "Cave location is (1,0)\n"
            + "EAST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.pickUpTreasure();
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (1,0)\n"
            + "EAST", unwrappedDungeon.playerLocationDetails());
    assertEquals("Treasure Collected by Player :  [DIAMONDS, DIAMONDS]",
            unwrappedDungeon.playerTreasureCollected());
  }
}
