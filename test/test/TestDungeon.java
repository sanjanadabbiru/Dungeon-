package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Arrow;
import dungeon.Directions;
import dungeon.Dungeon;
import dungeon.DungeonClass;
import dungeon.DungeonPlayer;
import dungeon.Graph;
import dungeon.Location;
import dungeon.Player;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;


/**
 * This class tests the dungeon and all the public methods in it.
 * Tests for invalid arguments of the constructor, whether treasure
 * is picked up from a cave or tunnel. Checks whether the toString
 * descriptions of player's location and treasure is returned correctly,etc.
 */
public class TestDungeon {
  private Dungeon dungeon;
  private Player player;
  private Dungeon unwrappedDungeon;


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRow() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1234);
    dungeon = new DungeonClass(-4, 4, 5, false,
            30, null, 4, rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCol() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1234);
    dungeon = new DungeonClass(4, -6, 5, false,
            30, null, 10, rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPercentage() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1234);
    dungeon = new DungeonClass(4, -6, 5, false,
            -30, null, 10, rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1234);
    dungeon = new DungeonClass(4, -6, -9, false,
            30, null, 10, rand);
  }


  @Test
  public void testPickupTreasureFromTunnel() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1236);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            60, player, 10, rand);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals(-1, unwrappedDungeon.pickUpTreasure());
  }

  @Test
  public void testMovePlayerForUnWrapped() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    assertEquals(" Player smells something mildly pungent nearby\n"
            + "Cave does not have any arrows\n"
            + "RUBIES, RUBIES\n"
            + "Cave location is (5,1)\n"
            + "NORTH, EAST, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals(" Player smells something mildly pungent nearby\n"
            + "CROOKED_ARROW\n"
            + "DIAMONDS\n"
            + "Cave location is (5,2)\n"
            + "WEST, NORTH, EAST", unwrappedDungeon.playerLocationDetails());
  }

  @Test
  public void testEntrances() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1238);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    Map<Location, List<Location>> allEdges = unwrappedDungeon.getAllEdges();
    for (Map.Entry<Location, List<Location>> entry : allEdges.entrySet()) {
      Location cave = entry.getKey();
      if (cave.isTunnel()) {
        assertEquals(2, entry.getValue().size());
      } else {
        assertNotEquals(2, entry.getValue().size());
      }
    }
  }

  @Test
  public void testCheckPathDistance() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1239);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    Location testStartCave = player.getPlayerPosition();
    Map<Location, Integer> distance = new HashMap<>();
    Map<Location, Location> path = new HashMap<>();
    for (Map.Entry<Location, List<Location>> entry : unwrappedDungeon.getAllEdges().entrySet()) {
      distance.put(entry.getKey(), -1);
    }
    distance.put(testStartCave, 0);
    path.put(testStartCave, testStartCave);

    Queue<Location> q = new LinkedList<>();
    q.add(testStartCave);

    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        Location cave = q.remove();
        List<Location> adjList = unwrappedDungeon.getAllEdges().get(cave);
        for (Location adjCave : adjList) {
          if (distance.get(adjCave) == -1) {
            distance.put(adjCave, distance.get(cave) + 1);
            path.put(adjCave, cave);
            q.add(adjCave);
          }
        }
      }
    }
    Location testEndCave = player.getEndCave();
    assertTrue(distance.get(testEndCave) >= 5);
  }

  @Test
  public void testPlayerMovesToInvalidPosition() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1240);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    assertEquals(-1, unwrappedDungeon.movePlayer(Directions.EAST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInterconnectivity() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1241);
    unwrappedDungeon = new DungeonClass(6, 6, 50, false,
            60, player, 10, rand);
  }

  @Test
  public void testGenerateCaves() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1242);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    List<List<Location>> testCaveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : testCaveList) {
      for (Location cave : list) {
        assertNotEquals(null, cave);
      }
    }
  }

  @Test
  public void testAllTunnelsNoTreasure() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1243);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    List<List<Location>> testCaveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : testCaveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          assertTrue(cave.getTreasureList().isEmpty());
        }
      }
    }
  }


  @Test
  public void testGrid() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1244);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6,
            0, false, 60, player, 10, rand);
    List<List<Location>> testCaveList = unwrappedDungeon.getCaveList();

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertTrue(i == testCaveList.get(i).get(j).getX()
                && j == testCaveList.get(i).get(j).getY());
      }
    }
  }

  @Test
  public void testRandomnessGrid() {
    Random rand = new Random();
    for (int i = 0; i < 1000; i++) {
      Player player1 = new DungeonPlayer();
      Player player2 = new DungeonPlayer();
      Dungeon unwrappedDungeon1 = new DungeonClass(6, 6,
              2, false, 60, player1, 10, rand);
      Dungeon unwrappedDungeon2 = new DungeonClass(6, 6,
              2, false, 60, player2, 10, rand);
      assertNotEquals(unwrappedDungeon1.getResult(), unwrappedDungeon2.getResult());
    }
  }

  @Test
  public void testRandomTreasure() {
    int count = 0;
    Random rand = new Random();
    Player player1 = new DungeonPlayer();
    Player player2 = new DungeonPlayer();
    Dungeon unwrappedDungeon1 = new DungeonClass(6, 6,
            2, false, 100, player1, 10, rand);
    List<List<Location>> testCaveList1 = unwrappedDungeon1.getCaveList();
    List<Location> properCaveList1 = new ArrayList<>();

    for (List<Location> list : testCaveList1) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          properCaveList1.add(cave);
        }
      }
    }
    for (int i = 0; i < properCaveList1.size(); i++) {
      for (int j = 0; j < properCaveList1.size(); j++) {
        if (i == j) {
          continue;
        } else {
          if (properCaveList1.get(i).getTreasureList()
                  == properCaveList1.get(j).getTreasureList()) {
            count++;
          }
        }
      }
    }
    assertTrue(count < 5);
  }


  @Test
  public void testPercentageOfTreasure() {
    player = new DungeonPlayer();
    Random rand = new Random();
    int percent = 10;
    Dungeon unwrappedDungeon = new DungeonClass(6, 6,
            2, false, percent, player, 10, rand);
    int treasureCount = 0;
    int caveCount = 0;
    List<List<Location>> testCaveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : testCaveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          if (cave.getTreasureList().isEmpty()) {
            caveCount++;
            continue;
          } else {
            caveCount++;
            treasureCount++;
          }
        }
      }
    }
    int actualPercent = (treasureCount * 100) / caveCount;
    assertTrue(actualPercent <= percent && actualPercent >= percent - 10);
  }


  @Test
  public void testStartEndCaves() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1246);
    DungeonClass unwrappedDungeon = new DungeonClass(6, 6,
            2, false, 60, player, 10, rand);
    Location startCave = player.getPlayerPosition();
    Location endCave = player.getEndCave();
    assertFalse(startCave.isTunnel());
    assertFalse(endCave.isTunnel());
  }


  @Test
  public void testPlayerTreasureDetails() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1247);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 100, player, 10, rand);
    unwrappedDungeon.pickUpTreasure();
    assertEquals("Treasure Collected by Player :  [DIAMONDS, RUBIES] \n"
                    + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]",
            unwrappedDungeon.playerTreasureCollected());
  }

  @Test
  public void testPlayerLocationDetails() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1248);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 60, player, 10, rand);
    assertEquals("SAPPHIRES, RUBIES, RUBIES\n"
            + "Cave location is (5,5)\n"
            + "WEST", unwrappedDungeon.playerLocationDetails());
  }


  @Test
  public void testInterconnectivityUnWrappedEdges() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1249);
    int row = 6;
    int col = 6;
    int interconnectivity = 2;
    int expected = row * col - 1 + interconnectivity;
    Dungeon unwrappedDungeon = new DungeonClass(row, col, interconnectivity,
            false, 60, player, 10, rand);
    List<Graph.Edge> testResult = unwrappedDungeon.getResult();
    assertEquals(expected, testResult.size());
  }

  @Test
  public void testIfEdgesConnected() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1250);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 3,
            false, 60, player, 10, rand);
    assertEquals("RUBIES, SAPPHIRES\n"
            + "Cave location is (2,0)\n"
            + "SOUTH, EAST, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (1,0)\n"
            + "EAST, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("DIAMONDS\n"
            + "Cave location is (1,1)\n"
            + "EAST, WEST, SOUTH, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("RUBIES, SAPPHIRES, RUBIES\n"
            + "Cave location is (0,1)\n"
            + "WEST, EAST, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("RUBIES\n"
            + "Cave location is (0,0)\n"
            + "EAST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (0,2)\n"
            + "EAST, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (0,3)\n"
            + "EAST, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (0,4)\n"
            + "WEST, EAST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (0,5)\n"
            + "SOUTH, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("SAPPHIRES, DIAMONDS\n"
            + "Cave location is (1,5)\n"
            + "NORTH, WEST, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("DIAMONDS\n"
            + "Cave location is (1,4)\n"
            + "EAST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("RUBIES, DIAMONDS\n"
            + "Cave location is (2,5)\n"
            + "SOUTH, WEST, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (2,4)\n"
            + "EAST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (3,5)\n"
            + "NORTH, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (4,5)\n"
            + "NORTH, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (4,4)\n"
            + "EAST, WEST, SOUTH, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("RUBIES\n"
            + "Cave location is (5,4)\n"
            + "EAST, WEST, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("DIAMONDS, SAPPHIRES\n"
            + "Cave location is (5,5)\n"
            + "WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("DIAMONDS\n"
            + "Cave location is (5,3)\n"
            + "EAST, WEST, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.EAST);
    unwrappedDungeon.movePlayer(Directions.NORTH);
    unwrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (3,4)\n"
            + "WEST, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (3,3)\n"
            + "NORTH, EAST, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.NORTH);
    unwrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("DIAMONDS, RUBIES, SAPPHIRES\n"
            + "Cave location is (1,3)\n"
            + "SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (3,2)\n"
            + "WEST, NORTH, SOUTH, EAST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (2,2)\n"
            + "SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.WEST);
    unwrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("DIAMONDS, SAPPHIRES, DIAMONDS\n"
            + "Cave location is (2,1)\n"
            + "SOUTH, NORTH, WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (3,0)\n"
            + "SOUTH, NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (4,0)\n"
            + "NORTH, EAST, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("SAPPHIRES, RUBIES\n"
            + "Cave location is (5,0)\n"
            + "NORTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.NORTH);
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (4,1)\n"
            + "SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals("SAPPHIRES, RUBIES\n"
            + "Cave location is (5,1)\n"
            + "End cave reached", unwrappedDungeon.playerLocationDetails());
  }


  //  WRAPPED DUNGEON TESTING
  @Test
  public void testInterconnectivityWrappedEdges() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1250);
    int row = 6;
    int col = 6;
    int interconnectivity = 2;
    int expected = row * col - 1 + interconnectivity;
    Dungeon wrappedDungeon = new DungeonClass(row, col, interconnectivity,
            true, 60, player, 10, rand);
    List<Graph.Edge> testResult = wrappedDungeon.getResult();
    assertEquals(expected, testResult.size());
  }

  @Test
  public void testMovePlayerForWrapped() {

    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1257);
    Dungeon wrappedDungeon = new DungeonClass(6, 6, 2,
            true, 60, player, 10, rand);

    assertEquals("DIAMONDS\n"
            + "Cave location is (0,3)\n"
            + "SOUTH, WEST, NORTH", wrappedDungeon.playerLocationDetails());
    wrappedDungeon.movePlayer(Directions.NORTH);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (5,3)\n"
            + "SOUTH, WEST, EAST", wrappedDungeon.playerLocationDetails());

  }

  @Test
  public void testEntrancesForWrapped() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1260);
    DungeonClass wrappedDungeon = new DungeonClass(6, 6,
            2, true, 60, player, 10, rand);
    Map<Location, List<Location>> allEdges = wrappedDungeon.getAllEdges();
    for (Map.Entry<Location, List<Location>> entry : allEdges.entrySet()) {
      Location cave = entry.getKey();
      if (cave.isTunnel()) {
        assertEquals(2, entry.getValue().size());
      } else {
        assertNotEquals(2, entry.getValue().size());
      }
    }
  }

  @Test
  public void testPercentageOfTreasureWrapped() {
    player = new DungeonPlayer();
    Random rand = new Random();
    int percent = 10;
    Dungeon unwrappedDungeon = new DungeonClass(6, 6,
            2, true, percent, player, 10, rand);
    int treasureCount = 0;
    int caveCount = 0;
    List<List<Location>> testCaveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : testCaveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          if (cave.getTreasureList().isEmpty()) {
            caveCount++;
            continue;
          } else {
            caveCount++;
            treasureCount++;
          }
        }
      }
    }
    int actualPercent = (treasureCount * 100) / caveCount;
    assertTrue(actualPercent <= percent && actualPercent >= percent - 10);
  }

  @Test
  public void testCheckPathDistanceForWrapped() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1261);
    DungeonClass wrappedDungeon = new DungeonClass(6, 6, 2,
            true, 60, player, 10, rand);
    Location testStartCave = player.getPlayerPosition();
    Map<Location, Integer> distance = new HashMap<>();
    Map<Location, Location> path = new HashMap<>();
    for (Map.Entry<Location, List<Location>> entry : wrappedDungeon.getAllEdges().entrySet()) {
      distance.put(entry.getKey(), -1);
    }
    distance.put(testStartCave, 0);
    path.put(testStartCave, testStartCave);

    Queue<Location> q = new LinkedList<>();
    q.add(testStartCave);

    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        Location cave = q.remove();
        List<Location> adjList = wrappedDungeon.getAllEdges().get(cave);
        for (Location adjCave : adjList) {
          if (distance.get(adjCave) == -1) {
            distance.put(adjCave, distance.get(cave) + 1);
            path.put(adjCave, cave);
            q.add(adjCave);
          }
        }
      }
    }
    Location testEndCave = player.getEndCave();
    assertTrue(distance.get(testEndCave) >= 5);
  }

  @Test
  public void testPlayerTreasureDetailsWrapped() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1247);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            true, 100, player, 10, rand);
    unwrappedDungeon.pickUpTreasure();
    assertEquals("Treasure Collected by Player :  [RUBIES, SAPPHIRES, SAPPHIRES]",
            unwrappedDungeon.playerTreasureCollected());
  }

  @Test
  public void testPlayerLocationDetailsWrapped() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1248);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            true, 60, player, 10, rand);
    assertEquals("Cave does not have any treasure\n"
            + "Cave location is (2,0)\n"
            + "NORTH, SOUTH, EAST", unwrappedDungeon.playerLocationDetails());
  }

  @Test
  public void testRandomnessGridWrapped() {
    Random rand = new Random();
    for (int i = 0; i < 1000; i++) {
      Player player1 = new DungeonPlayer();
      Player player2 = new DungeonPlayer();
      Dungeon unwrappedDungeon1 = new DungeonClass(6, 6,
              2, true, 60, player1, 10, rand);
      Dungeon unwrappedDungeon2 = new DungeonClass(6, 6,
              2, true, 60, player2, 10, rand);
      assertNotEquals(unwrappedDungeon1.getResult(), unwrappedDungeon2.getResult());
    }
  }

  @Test
  public void testShootArrowWrapped() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1232);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6,
            6, true, 100, player, 10, rand);
    assertEquals(" Player smells something horrible nearby\n"
            + "CROOKED_ARROW\n"
            + "SAPPHIRES, RUBIES\n"
            + "Cave location is (3,5)\n"
            + "EAST", unwrappedDungeon.playerLocationDetails());
    assertEquals(2, unwrappedDungeon.shootArrow(Directions.EAST, 1));
    assertEquals(1, unwrappedDungeon.shootArrow(Directions.EAST, 1));
  }

  @Test
  public void testPickupTreasureFromTunnelWrapped() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1236);
    unwrappedDungeon = new DungeonClass(6, 6, 2, true,
            60, player, 10, rand);
    unwrappedDungeon.movePlayer(Directions.NORTH);
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals(-1, unwrappedDungeon.pickUpTreasure());
  }

  // ********** NEW MODEL TESTING ****************

  @Test
  public void testMonsterAtEndCave() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    assertTrue(player.getEndCave().isMonster());
  }

  @Test
  public void testNoMonsterAtStartCave() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    assertFalse(unwrappedDungeon.getStartCave().isMonster());
  }

  @Test
  public void testNoMonsterAtTunnel() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals(" CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "RUBIES\n"
            + "Cave location is (3,1)\n"
            + "WEST, NORTH, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.WEST);
    assertEquals("Location is a tunnel\n"
            + "Tunnel location is (3,0)\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "EAST, NORTH", unwrappedDungeon.playerLocationDetails());
    assertFalse(player.getPlayerPosition().isMonster());
  }


  @Test
  public void testPlayerStartsWith3Arrows() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    int count = 0;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    List<Arrow> arrowList = player.getArrowList();
    for (Arrow arrow : arrowList) {
      count++;
    }
    assertEquals(3, count);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumberOfMonsters() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    int count = 0;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, -10, rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumberOfMonsters2() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    int count = 0;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 100, rand);
  }


  @Test
  public void testArrowsInLocation() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    int countIsArrow = 0;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    List<List<Location>> locationList = unwrappedDungeon.getCaveList();
    for (List<Location> location : locationList) {
      for (Location l : location) {
        if (l.getArrowList().isEmpty()) {
          continue;
        } else {
          countIsArrow++;
        }
      }
    }
    assertEquals(36, countIsArrow);
  }

  @Test
  public void testMonstersAssigned() {
    player = new DungeonPlayer();
    Random rand = new Random();
    int monsterCount = 0;
    rand.setSeed(1237);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    List<Location> properCave = new ArrayList<>();
    List<List<Location>> caveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          properCave.add(cave);
        }
      }
    }
    for (Location location : properCave) {
      if (location.isMonster()) {
        monsterCount++;
      }
    }
    assertEquals(10, monsterCount);
  }

  @Test
  public void testMorePungentSmell() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    assertEquals(-2, unwrappedDungeon.movePlayer(Directions.NORTH));
  }

  @Test
  public void testLessPungentSmell() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1238);
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.movePlayer(Directions.NORTH);
    List<List<Location>> caveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        if (cave.getX() == 2 && cave.getY() == 3) {
          assertTrue(cave.isMonster());
        }
      }
    }
  }

  @Test
  public void monsterCount() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    int monsterBeforeCount = 0;
    int monsterAfterCount = 0;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    List<Location> properCave = new ArrayList<>();
    List<List<Location>> caveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          properCave.add(cave);
        }
      }
    }
    for (Location location : properCave) {
      if (location.isMonster()) {
        monsterBeforeCount++;
      }
    }
    unwrappedDungeon.shootArrow(Directions.NORTH, 1);
    unwrappedDungeon.shootArrow(Directions.NORTH, 1);
    properCave = new ArrayList<>();
    caveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          properCave.add(cave);
        }
      }
    }
    for (Location location : properCave) {
      if (location.isMonster()) {
        monsterAfterCount++;
      }
    }
    assertTrue(monsterAfterCount == monsterBeforeCount - 1);
  }

  @Test
  public void testCrookedArrowDirection() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1267);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals(-1, unwrappedDungeon.shootArrow(Directions.SOUTH, 1));
    unwrappedDungeon.movePlayer(Directions.EAST);
    assertEquals(3, unwrappedDungeon.shootArrow(Directions.WEST, 1));
  }

  @Test
  public void testArrowMiss() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1221);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.shootArrow(Directions.NORTH, 2);
    assertEquals(-2, unwrappedDungeon.movePlayer(Directions.EAST));
  }

  @Test
  public void testSmellRemovedAfterMonsterDeath() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1222);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 3, rand);
    unwrappedDungeon.pickUpArrow();
    unwrappedDungeon.movePlayer(Directions.EAST);
    unwrappedDungeon.pickUpArrow();
    assertEquals(" Player smells something horrible nearby\n"
            + "Cave does not have any arrows\n"
            + "DIAMONDS\n"
            + "Cave location is (3,1)\n"
            + "WEST, NORTH, SOUTH", unwrappedDungeon.playerLocationDetails());
    assertEquals(2, unwrappedDungeon.shootArrow(Directions.NORTH, 1));
    assertEquals(1, unwrappedDungeon.shootArrow(Directions.NORTH, 1));
    assertEquals(" Player smells nothing!\n"
            + "Cave does not have any arrows\n"
            + "DIAMONDS\n"
            + "Cave location is (3,1)\n"
            + "WEST, NORTH, SOUTH", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    assertEquals(" Player smells nothing!\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, DIAMONDS\n"
            + "Cave location is (4,1)\n"
            + "NORTH, EAST, WEST", unwrappedDungeon.playerLocationDetails());
  }

  @Test
  public void testNoMoreArrows() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1211);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.shootArrow(Directions.EAST, 2);
    unwrappedDungeon.shootArrow(Directions.EAST, 2);
    unwrappedDungeon.shootArrow(Directions.EAST, 2);
    assertEquals(0, unwrappedDungeon.shootArrow(Directions.NORTH, 2));
  }

  @Test
  public void testArrowWrongDirection() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1210);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    assertEquals(" Player smells something horrible nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES\n"
            + "Cave location is (5,3)\n"
            + "EAST, WEST, NORTH", unwrappedDungeon.playerLocationDetails());
    assertEquals(-1, unwrappedDungeon.shootArrow(Directions.SOUTH, 1));
  }

  @Test
  public void testArrowShot() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1210);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.shootArrow(Directions.EAST, 1);
    assertEquals(1, unwrappedDungeon.shootArrow(Directions.EAST, 1));
  }

  @Test
  public void testWinningGame() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1296);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);

    d.movePlayer(Directions.SOUTH);
    d.pickUpTreasure();
    d.movePlayer(Directions.SOUTH);
    d.pickUpArrow();
    d.movePlayer(Directions.WEST);
    d.movePlayer(Directions.WEST);
    d.movePlayer(Directions.WEST);
    d.movePlayer(Directions.WEST);
    d.movePlayer(Directions.WEST);
    d.shootArrow(Directions.NORTH, 1);
    d.shootArrow(Directions.NORTH, 1);
    d.movePlayer(Directions.NORTH);
    assertTrue(d.isGameOver());
  }

  @Test
  public void testFiftyPercentChanceOfSurvival() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1267);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.shootArrow(Directions.EAST, 1);
    assertEquals(0, unwrappedDungeon.movePlayer(Directions.EAST));
  }

  @Test
  public void testFiftyPercentChanceOfDeath() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1282);
    int flag = 100;
    unwrappedDungeon = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    unwrappedDungeon.shootArrow(Directions.SOUTH, 1);
    assertEquals(-2, unwrappedDungeon.movePlayer(Directions.SOUTH));
  }

  @Test
  public void testPlayerLocationDetailsNew() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1219);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            true, 60, player, 10, rand);
    assertEquals(" Player smells something horrible nearby\n"
            + "Cave does not have any arrows\n"
            + "DIAMONDS, RUBIES\n"
            + "Cave location is (4,4)\n"
            + "EAST, WEST, SOUTH, NORTH", unwrappedDungeon.playerLocationDetails());
  }

  @Test
  public void testPlayerTreasureDetailsNew() {
    Player player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1289);
    Dungeon unwrappedDungeon = new DungeonClass(6, 6, 2,
            false, 100, player, 10, rand);
    assertEquals(" Player smells something horrible nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, DIAMONDS\n"
            + "Cave location is (0,5)\n"
            + "WEST", unwrappedDungeon.playerLocationDetails());
    unwrappedDungeon.pickUpTreasure();
    unwrappedDungeon.pickUpArrow();
    assertEquals(" Player smells something horrible nearby\n"
            + "Cave does not have any arrows\n"
            + "Cave does not have any treasure\n"
            + "Cave location is (0,5)\n"
            + "WEST", unwrappedDungeon.playerLocationDetails());
  }

  @Test
  public void testPercentageOfArrows() {
    player = new DungeonPlayer();
    Random rand = new Random();
    int percent = 10;
    Dungeon unwrappedDungeon = new DungeonClass(6, 6,
            2, false, percent, player, 10, rand);
    int arrowCount = 0;
    int caveCount = 0;
    List<List<Location>> testCaveList = unwrappedDungeon.getCaveList();
    for (List<Location> list : testCaveList) {
      for (Location cave : list) {
        if (cave.getTreasureList().isEmpty()) {
          caveCount++;
          continue;
        } else {
          caveCount++;
          arrowCount++;
        }
      }
    }

    int actualPercent = (arrowCount * 100) / caveCount;
    assertTrue(actualPercent <= percent && actualPercent >= percent - 10);
  }

  @Test
  public void testIsThief() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1281);
    int percent = 100;
    Dungeon unwrappedDungeon = new DungeonClass(6, 6,
            2, false, percent, player, 1, rand);

    unwrappedDungeon.movePlayer(Directions.WEST);
    unwrappedDungeon.pickUpTreasure();


    unwrappedDungeon.movePlayer(Directions.NORTH);


    unwrappedDungeon.movePlayer(Directions.EAST);


    unwrappedDungeon.movePlayer(Directions.SOUTH);
    unwrappedDungeon.pickUpTreasure();


    unwrappedDungeon.movePlayer(Directions.WEST);


    unwrappedDungeon.movePlayer(Directions.WEST);


    unwrappedDungeon.movePlayer(Directions.WEST);


    unwrappedDungeon.movePlayer(Directions.NORTH);


    unwrappedDungeon.movePlayer(Directions.EAST);


    unwrappedDungeon.movePlayer(Directions.EAST);


    unwrappedDungeon.movePlayer(Directions.NORTH);

    assertEquals("Treasure Collected by Player :  [RUBIES, RUBIES, RUBIES, RUBIES] \n"
                    + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]",
            unwrappedDungeon.playerTreasureCollected());
    unwrappedDungeon.movePlayer(Directions.NORTH);

    assertEquals("Treasure Collected by Player :  [] \n"
                    + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]",
            unwrappedDungeon.playerTreasureCollected());
  }


}
