package test.mockdungeonview;

import controller.DungeonControllerView;
import view.DungeonView;
import view.Features;
import view.ReadOnlyDungeonModel;

/**
 * Class that implements the mock view of the Dungeon class. It returns a dummy string for
 * testing in each method to make sure the controller is returning the correct value.
 */
public class MockDungeonView implements DungeonView {
  private StringBuilder log;

  /**
   * Constructs a dungeon view for testing purposes
   * which takes in input string.
   *
   * @param log string
   */
  public MockDungeonView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addClickListener(DungeonControllerView listener) {
    log.append("Clicking...\n");
  }

  @Override
  public void refresh() {
    log.append("Refreshing...\n");
  }

  @Override
  public void makeVisible() {
    log.append("Making visible...\n");
  }

  @Override
  public void setFeatures(Features f) {
    log.append("Setting features...\n");

  }

  @Override
  public void resetFocus() {
    log.append("Resetting focus...\n");

  }

  @Override
  public void showErrorMessage(String error) {
    log.append("Error..\n");
  }

  @Override
  public void setModel(ReadOnlyDungeonModel model) {
    log.append(model.getRow()).append(model.getCol());
  }

  @Override
  public void resetFlags() {
    log.append("Resetting flags...\n");

  }
}
