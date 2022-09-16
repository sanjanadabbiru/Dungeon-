package test;

import static org.junit.Assert.assertEquals;

import controller.DungeonConsoleController;
import controller.DungeonController;
import dungeon.Dungeon;
import dungeon.DungeonClass;
import dungeon.DungeonPlayer;
import dungeon.Player;

import org.junit.Test;

import test.mockdungeonmodel.LoggingDungeon;

import java.io.StringReader;
import java.util.Random;


/**
 * Test cases for the dungeon controller, using mocks for readable and
 * appendable. Test cases for passing an invalid model, appropriate messages for
 * arrow missing, arrow shooting and no more arrows left to shoot, player killing the otyugh,
 * player being killed,etc.
 */
public class DungeonControllerTest {

  private Player player;
  private final String welcomeMenu = "******** WELCOME TO THE DUNGEON **********\n"
          + "MENU\n"
          + "Choose 1 to Move\n"
          + "Choose 2 to Pick Up treasure\n"
          + "Choose 3 to Pick Up arrows\n"
          + "Choose 4 to Shoot arrows\n"
          + "q/quit to Quit Game\n"
          + "------------------------------";
  private final String menu = "MENU\n"
          + "Choose 1 to Move\n"
          + "Choose 2 to Pick Up treasure\n"
          + "Choose 3 to Pick Up arrows\n"
          + "Choose 4 to Shoot arrows\n"
          + "q/quit to Quit Game\n"
          + "------------------------------\n";

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    StringReader input = new StringReader("1");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(null);
  }


  @Test
  public void testOnlyController() {
    Appendable outputLog = new StringBuilder();
    Readable mockInput = new StringReader("1 s q");
    StringBuilder st = new StringBuilder();
    Dungeon m = new LoggingDungeon(st);
    DungeonController c = new DungeonConsoleController(mockInput, outputLog);
    c.playGame(m);
    assertEquals("Location details of the player...\n"
            + "Moving player...\n"
            + "Location details of the player...\n", st.toString());
  }

  @Test
  public void testInvalidMenuOption() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    StringReader input = new StringReader("move");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, DIAMONDS, DIAMONDS\n"
            + "Cave location is (1,2)\n"
            + "EAST, WEST, NORTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Unknown command move\n";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2),
            outputLog.toString());
  }

  @Test
  public void testMovePlayer() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1237);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    StringReader input = new StringReader("1 e");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, DIAMONDS, DIAMONDS\n"
            + "Cave location is (1,2)\n"
            + "EAST, WEST, NORTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter your move: N,S,E,W";
    String output3 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " CROOKED_ARROW\n"
            + "RUBIES, RUBIES\n"
            + "Cave location is (1,3)\n"
            + "NORTH, SOUTH, WEST, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output3, menu),
            outputLog.toString());
  }

  @Test
  public void testInvalidMoveContinue() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1239);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    StringReader input = new StringReader("1 wdef w");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " CROOKED_ARROW\n"
            + "RUBIES\n"
            + "Cave location is (1,3)\n"
            + "SOUTH, WEST, NORTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter your move: N,S,E,W";
    String output3 = "Enter valid move";
    String output4 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, SAPPHIRES\n"
            + "Cave location is (1,2)\n"
            + "EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output3,
            output4, menu), outputLog.toString());
  }

  @Test
  public void testInvalidShootContinue() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1242);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    StringReader input = new StringReader("1 e 4 w -2 1 1");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " CROOKED_ARROW\n"
            + "SAPPHIRES\n"
            + "Cave location is (0,0)\n"
            + "EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter your move: N,S,E,W";
    String output3 = "Enter directions to shoot arrow:";
    String output4 = "Enter distance";
    String output5 = "Enter valid distance\nArrow misses the monster\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "There is no next input!";
    String output6 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (0,1)\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "WEST, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output6,
            menu, output3, output4, output5), outputLog.toString());
  }

  @Test
  public void testKilledByOtyugh() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1248);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 12, rand);
    StringReader input = new StringReader("1 e");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something horrible nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, RUBIES, RUBIES\n"
            + "Cave location is (4,2)\n"
            + "EAST, NORTH, SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (0,5)\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "WEST, SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output3 = "Enter your move: N,S,E,W";
    String output4 = "Oops! You are eaten by the Otyugh :( Better luck next time!\n";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output3,
            output4), outputLog.toString());
  }

  @Test
  public void testMoveWrongDirection() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1254);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 12, rand);
    StringReader input = new StringReader("1 n");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something mildly pungent nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "RUBIES, SAPPHIRES, DIAMONDS\n"
            + "Cave location is (0,3)\n"
            + "EAST, SOUTH, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter your move: N,S,E,W";
    String output3 = "Invalid choice";
    String output4 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " Player smells something mildly pungent nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "RUBIES, SAPPHIRES, DIAMONDS\n"
            + "Cave location is (0,3)\n"
            + "EAST, SOUTH, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2,
                    output3, output4, menu),
            outputLog.toString());
    System.out.println(outputLog.toString());
  }

  @Test
  public void testShootArrow() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1249);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 16, rand);
    StringReader input = new StringReader("4 s 1 4 s 1 1 s");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something horrible nearby\n"
            + "CROOKED_ARROW\n"
            + "DIAMONDS, SAPPHIRES\n"
            + "Cave location is (0,4)\n"
            + "SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter directions to shoot arrow:";
    String output3 = "Enter distance";
    String output4 = "You have damaged the otyugh! Shoot wisely!\n";
    String output5 = "You have slayed the otyugh!\n";
    String output6 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " Player smells something mildly pungent nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "RUBIES\n"
            + "Cave location is (1,4)\n"
            + "EAST, NORTH, SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output7 = "Enter your move: N,S,E,W";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output3, output4,
            output2, output3, output5, output7, output6, menu), outputLog.toString());
  }

  @Test
  public void testArrowMisses() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1250);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 16, rand);
    StringReader input = new StringReader("4 n 2");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something horrible nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "DIAMONDS, DIAMONDS\n"
            + "Cave location is (5,4)\n"
            + "EAST, WEST, NORTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter directions to shoot arrow:";
    String output3 = "Enter distance";
    String output4 = "Arrow misses the monster\n";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output3,
            output4), outputLog.toString());
  }

  @Test
  public void testNoMoreArrowsLeftToShoot() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1250);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 16, rand);
    StringReader input = new StringReader("4 e 2 4 e 2 4 e 2 4 e 2");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something horrible nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "DIAMONDS, DIAMONDS\n"
            + "Cave location is (5,4)\n"
            + "EAST, WEST, NORTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter directions to shoot arrow:";
    String output3 = "Enter distance";
    String output4 = "Arrow misses the monster\n";
    String output5 = "No more arrows left to shoot :(\n";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output3, output4,
            output2, output3, output4, output2, output3, output4, output2, output3,
            output5), outputLog.toString());
  }

  @Test
  public void testQuitGame() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1251);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    StringReader input = new StringReader("1 w q");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " CROOKED_ARROW, CROOKED_ARROW\n"
            + "RUBIES, DIAMONDS\n"
            + "Cave location is (5,5)\n"
            + "WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter your move: N,S,E,W";
    String output3 = "Quitting the game!\n";
    String output4 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (5,4)\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "NORTH, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, output4,
            menu, output3), outputLog.toString());
  }

  @Test
  public void testPungentSmell() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1260);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    StringReader input = new StringReader("1 e");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something mildly pungent nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, SAPPHIRES, DIAMONDS\n"
            + "Cave location is (2,2)\n"
            + "EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Player smells something horrible nearby\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (2,3)\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "SOUTH, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2, menu),
            outputLog.toString());
  }

  @Test
  public void testFiftyPercentChance() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1299);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    StringReader input = new StringReader("1 e 4 s 1 1 s");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    assertEquals("******** WELCOME TO THE DUNGEON **********\n"
                    + "MENU\n"
                    + "Choose 1 to Move\n"
                    + "Choose 2 to Pick Up treasure\n"
                    + "Choose 3 to Pick Up arrows\n"
                    + "Choose 4 to Shoot arrows\n"
                    + "q/quit to Quit Game\n"
                    + "------------------------------\n"
                    + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
                    + "You are at the start cave..\n"
                    + " Player smells something mildly pungent nearby\n"
                    + "CROOKED_ARROW\n"
                    + "DIAMONDS, DIAMONDS\n"
                    + "Cave location is (1,4)\n"
                    + "EAST\n"
                    + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
                    + "MENU\n"
                    + "Choose 1 to Move\n"
                    + "Choose 2 to Pick Up treasure\n"
                    + "Choose 3 to Pick Up arrows\n"
                    + "Choose 4 to Shoot arrows\n"
                    + "q/quit to Quit Game\n"
                    + "------------------------------\n"
                    + "\n"
                    + "Enter your move: N,S,E,W\n"
                    + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
                    + "Player smells something horrible nearby\n"
                    + "Location is a tunnel\n"
                    + "Tunnel location is (1,5)\n"
                    + "CROOKED_ARROW\n"
                    + "SOUTH, WEST\n"
                    + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
                    + "MENU\n"
                    + "Choose 1 to Move\n"
                    + "Choose 2 to Pick Up treasure\n"
                    + "Choose 3 to Pick Up arrows\n"
                    + "Choose 4 to Shoot arrows\n"
                    + "q/quit to Quit Game\n"
                    + "------------------------------\n"
                    + "\n"
                    + "Enter directions to shoot arrow:\n"
                    + "Enter distance\n"
                    + "You have damaged the otyugh! Shoot wisely!\n"
                    + "\n"
                    + "Enter your move: N,S,E,W\n"
                    + "Oops! You are eaten by the Otyugh :( Better luck next time!\n",
            outputLog.toString());

  }

  @Test
  public void testPickUpTreasure() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1262);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    StringReader input = new StringReader("2");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells something horrible nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "DIAMONDS, DIAMONDS, RUBIES\n"
            + "Cave location is (4,1)\n"
            + "SOUTH, WEST, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Treasure Collected by Player :  [DIAMONDS, DIAMONDS, RUBIES] \n"
            + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]\n";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2),
            outputLog.toString());
  }

  @Test
  public void testPickUpArrow() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1270);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 10, rand);
    StringReader input = new StringReader("3");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    String output1 = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " CROOKED_ARROW\n"
            + "DIAMONDS\n"
            + "Cave location is (0,5)\n"
            + "SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
    String output2 = "Treasure Collected by Player :  [] \n"
            + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW,"
            + " CROOKED_ARROW]\n";
    assertEquals(String.join("\n", welcomeMenu, output1, menu, output2),
            outputLog.toString());
  }

  @Test
  public void testIsThief() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1276);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    StringReader input = new StringReader("2 1 n 1 w 1 w 2");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    assertEquals("******** WELCOME TO THE DUNGEON **********\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " You smell nothing!\n"
            + "CROOKED_ARROW\n"
            + "DIAMONDS, RUBIES, SAPPHIRES\n"
            + "Cave location is (1,4)\n"
            + "NORTH, WEST, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Treasure Collected by Player :  [DIAMONDS, RUBIES, SAPPHIRES] \n"
            + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You smell nothing!\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (0,4)\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "WEST, SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You smell nothing!\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (0,3)\n"
            + "CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW\n"
            + "WEST, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "Oops! Looks like you were robbed!\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You smell nothing!\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (0,2)\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "SOUTH, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Treasure Collected by Player :  [] \n"
            + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]\n",
            outputLog.toString());
  }

  @Test
  public void testWinningGame() {
    player = new DungeonPlayer();
    Random rand = new Random();
    rand.setSeed(1296);
    Dungeon d = new DungeonClass(6, 6, 2, false,
            100, player, 1, rand);
    StringReader input = new StringReader("1 s 2 1 s 3 1 w 1 w 1 w 1 w 1 w 4 n 1 4 n 1 1 n");
    Appendable outputLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, outputLog);
    c.playGame(d);
    assertEquals("******** WELCOME TO THE DUNGEON **********\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "You are at the start cave..\n"
            + " Player smells nothing!\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "RUBIES, DIAMONDS\n"
            + "Cave location is (3,4)\n"
            + "SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " Player smells nothing!\n"
            + "CROOKED_ARROW\n"
            + "SAPPHIRES, SAPPHIRES, RUBIES\n"
            + "Cave location is (4,4)\n"
            + "SOUTH, NORTH, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Treasure Collected by Player :  [SAPPHIRES, SAPPHIRES, RUBIES] \n"
            + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW]\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Player smells nothing!\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (5,4)\n"
            + "CROOKED_ARROW\n"
            + "NORTH, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Treasure Collected by Player :  [SAPPHIRES, SAPPHIRES, RUBIES] \n"
            + " Arrows Collected by Player: [CROOKED_ARROW, CROOKED_ARROW, CROOKED_ARROW,"
            + " CROOKED_ARROW]\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " Player smells nothing!\n"
            + "CROOKED_ARROW\n"
            + "RUBIES, DIAMONDS\n"
            + "Cave location is (5,3)\n"
            + "NORTH, EAST, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Player smells nothing!\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (5,2)\n"
            + "CROOKED_ARROW\n"
            + "EAST, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " Player smells something mildly pungent nearby\n"
            + "CROOKED_ARROW, CROOKED_ARROW\n"
            + "SAPPHIRES, RUBIES\n"
            + "Cave location is (5,1)\n"
            + "NORTH, EAST, WEST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Player smells something horrible nearby\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (5,0)\n"
            + "CROOKED_ARROW\n"
            + "NORTH, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "Invalid choice\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "Player smells something horrible nearby\n"
            + "Location is a tunnel\n"
            + "Tunnel location is (5,0)\n"
            + "CROOKED_ARROW\n"
            + "NORTH, EAST\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "\n"
            + "Enter directions to shoot arrow:\n"
            + "Enter distance\n"
            + "You have damaged the otyugh! Shoot wisely!\n"
            + "\n"
            + "Enter directions to shoot arrow:\n"
            + "Enter distance\n"
            + "You have slayed the otyugh!\n"
            + "\n"
            + "Enter your move: N,S,E,W\n"
            + "End Cave Reached\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + " Player smells nothing!\n"
            + "CROOKED_ARROW\n"
            + "DIAMONDS\n"
            + "Cave location is (4,0)\n"
            + "SOUTH\n"
            + "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n"
            + "MENU\n"
            + "Choose 1 to Move\n"
            + "Choose 2 to Pick Up treasure\n"
            + "Choose 3 to Pick Up arrows\n"
            + "Choose 4 to Shoot arrows\n"
            + "q/quit to Quit Game\n"
            + "------------------------------\n"
            + "Game is over", outputLog.toString());
  }


}
