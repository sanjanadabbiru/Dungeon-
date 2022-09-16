package test;


import static org.junit.Assert.assertEquals;

import controller.DungeonControllerView;
import dungeon.Directions;
import dungeon.Dungeon;
import org.junit.Test;
import test.mockdungeonmodelnew.MockDungeonModel;
import test.mockdungeonmodelnew.MockDungeonModelException;
import test.mockdungeonview.MockDungeonView;
import view.DungeonView;
import view.Features;


/**
 * Test cases for the dungeon MVC controller, using mocks for readable and
 * appendable. Test cases for passing an invalid model, appropriate messages for
 * arrow missing, arrow shooting and no more arrows left to shoot, player killing the otyugh,
 * player being killed,etc.
 */
public class TestMvccontroller {

  @Test
  public void testMovePlayer() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.playGame();
    f.movePlayer(Directions.NORTH);
    assertEquals("Setting features...\n"
            + "Clicking...\n"
            + "Making visible...\n"
            + "NORTH", st.toString());
  }


  @Test
  public void testHandleCellClickException() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModelException(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.handleCellClick(Directions.NORTH);
    assertEquals("Setting features...\n"
            + "Error..\n", st.toString());
  }

  @Test
  public void testPickUpArrow() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.pickUpArrow();
    assertEquals("Setting features...\n"
            + "Picking up arrow..\n", st.toString());
  }

  @Test
  public void testPickUpTreasure() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.pickUpTreasure();
    assertEquals("Setting features...\n"
            + "Picking up treasure...\n", st.toString());
  }

  @Test
  public void testShootArrow() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.shootArrow(Directions.NORTH, 1);
    assertEquals("Setting features...\n"
            + "NORTH1", st.toString());
  }

  @Test
  public void testPlayGame() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.playGame();
    assertEquals("Setting features...\n"
            + "Clicking...\n"
            + "Making visible...\n", st.toString());
  }

  @Test
  public void testHandleCellClick() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.handleCellClick(Directions.WEST);
    assertEquals("Setting features...\n"
            + "WESTRefreshing...\n", st.toString());
  }

  @Test
  public void testProcessInput() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.processInput("6", "6", "4",
            false, "100", "1");
    assertEquals("Setting features...\n"
            + "Resetting flags...\n"
            + "66Clicking...\n"
            + "Refreshing...\n"
            + "Resetting focus...\n", st.toString());
  }

  @Test
  public void testRestartGame() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.restartGame();
    assertEquals("Setting features...\n"
            + "Resetting flags...\n"
            + "66Clicking...\n"
            + "Refreshing...\n"
            + "Resetting focus...\n", st.toString());
  }

  @Test
  public void testProcessInputInvalid() {
    StringBuilder st = new StringBuilder();
    Dungeon m = new MockDungeonModel(st);
    DungeonView v = new MockDungeonView(st);
    Features f = new DungeonControllerView(v, m);
    f.processInput("-6", "7", "4",
            false, "-100", "1");
    assertEquals("Setting features...\n"
            + "Error..\n"
            + "Error..\n"
            + "Error..\n"
            + "Clicking...\n"
            + "Refreshing...\n"
            + "Resetting focus...\n", st.toString());
  }

}
