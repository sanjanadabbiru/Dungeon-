package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.Cave;
import dungeon.Directions;
import dungeon.Dungeon;
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
 * Tests the Player class that allows testing for checking the
 * player position. For checking whether the player starts at the start cave and
 * reaches the end cave. Checks whether the treasure list of a player
 * gets added correctly.
 */
public class TestPlayer {
  private Player player;
  private Dungeon unwrapped;
  private Dungeon wrapped;
  private Random rand;

  @Before
  public void setup() {
    rand = new Random();
  }

  @Test
  public void testSetPlayerPosition() {
    player = new DungeonPlayer();
    Location cave = new Cave(0, 0);
    player.setPlayerPosition(cave);
    assertEquals(cave, player.getPlayerPosition());
  }

  @Test
  public void testGetPlayerPosition() {
    player = new DungeonPlayer();
    Location cave = new Cave(0, 0);
    player.setPlayerPosition(cave);
    assertEquals(cave, player.getPlayerPosition());
  }

  @Test
  public void testSetEndCave() {
    player = new DungeonPlayer();
    Location cave = new Cave(0, 0);
    player.setEndCave(cave);
    assertEquals(cave, player.getEndCave());
  }

  @Test
  public void testGetEndCave() {
    player = new DungeonPlayer();
    Location cave = new Cave(0, 0);
    player.setEndCave(cave);
    assertEquals(cave, player.getEndCave());
  }

  @Test
  public void testSetRowCol() {
    player = new DungeonPlayer();
    rand = new Random();
    rand.setSeed(0);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 0,
            true, 60, player,10, rand);
    player.setRowCol(6, 6);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    List<Directions> possibleMoves = new ArrayList<>();

    possibleMoves.add(Directions.EAST);
    possibleMoves.add(Directions.NORTH);

    assertEquals(possibleMoves, player.getPossibleMoves());


  }

  @Test
  public void testValidEnum() {
    for (Treasure treasure : Treasure.values()) {
      assertFalse(treasure == null);
    }
  }

  @Test
  public void testSetTreasureList() {
    player = new DungeonPlayer();
    List<Treasure> treasureList = new ArrayList<>();
    treasureList.add(Treasure.RUBIES);
    treasureList.add(Treasure.DIAMONDS);
    player.setTreasureList(treasureList);
    assertEquals(treasureList, player.getTreasureList());
  }

  @Test
  public void testEmptyTreasureList() {
    player = new DungeonPlayer();
    rand = new Random();
    rand.setSeed(0);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 0,
            false, 60,player,10, rand);
    assertEquals("Treasure Collected by Player :  []",
            unwrappedDungeon.playerTreasureCollected());
    assertTrue(player.getTreasureList().isEmpty());
  }

  @Test
  public void testInitialPosition() {
    player = new DungeonPlayer();
    rand = new Random();
    rand.setSeed(0);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 0,
            false, 60, player,10, rand);
    Location testStartCave = unwrappedDungeon.getStartCave();
    assertEquals(testStartCave, player.getPlayerPosition());
  }


  @Test
  public void testGetTreasureList() {
    player = new DungeonPlayer();
    List<Treasure> treasureList = new ArrayList<>();
    treasureList.add(Treasure.RUBIES);
    treasureList.add(Treasure.DIAMONDS);
    player.setTreasureList(treasureList);
    assertEquals(treasureList, player.getTreasureList());

  }

  @Test
  public void testPossibleMoves() {
    player = new DungeonPlayer();
    rand = new Random();
    rand.setSeed(0);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 0,
            true, 60, player,10, rand);
    player.setRowCol(6, 6);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    List<Directions> possibleMoves = new ArrayList<>();

    possibleMoves.add(Directions.EAST);
    possibleMoves.add(Directions.NORTH);

    assertEquals(possibleMoves, player.getPossibleMoves());


  }

  @Test
  public void testGetPossibleMoves() {
    player = new DungeonPlayer();
    rand = new Random();
    rand.setSeed(0);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 0,
            true, 60, player,10, rand);
    player.setRowCol(6, 6);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    List<Directions> possibleMoves = new ArrayList<>();

    possibleMoves.add(Directions.EAST);
    possibleMoves.add(Directions.NORTH);

    assertEquals(possibleMoves, player.getPossibleMoves());
  }

}
