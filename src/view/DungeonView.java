package view;

import controller.DungeonControllerView;

/**
 * Interface that contains methods to implement the mouse click for moving a player, for refreshing
 * the view every time an action takes place, showing any error messages in case of exception
 * handling, etc. It adds method for a click listener that allows the player to click on a
 * position on the maze and the player can move. Contains methods such as makeVisible to make the
 * view visible to the user.
 */
public interface DungeonView {
  /**
   * Method that adds a click listener where the player has to click on a valid place on the
   * dungeon maze and the player moves in the right direction. Calculates whether the click
   * listener listens to a click on the right place. Based on the direction clicked, the player
   * then moves to that position if it is valid.
   *
   * @param listener clickListener of DungeonControllerView type
   */
  void addClickListener(DungeonControllerView listener);

  /**
   * Refresh the view to reflect any changes in the game state. The view uses the repaint() method
   * which refreshes the view after components have been added or removed. The panel's repaint
   * method calls the paintComponent method which refreshes the frame with a new addition or
   * removal of an image.
   */
  void refresh();

  /**
   * Make the view visible to start the game session. This method is called to make a particular
   * component on the view visible to the user such as the JFrame, JPanel, etc. Sets the frame
   * visible and every component in it.
   */
  void makeVisible();

  /**
   * This method contains key press listeners as well as action listeners for buttons declared
   * in the view. Implements the button's action listeners and calls the methods in the
   * controller. The method in the controller then calls the appropriate function in the model.
   * The features are obtained from the features interface which the controller implements.
   * Based on the method calls in the view, features would call the appropriate method in the
   * controller.
   *
   * @param f Features f
   */
  void setFeatures(Features f);

  /**
   * This method appends any error string that is passed and is mainly used to handle any exceptions
   * that the view throws upon any input from the user. Any exception that is to be handled in
   * the controller, the view's error method is called to display a message without breaking
   * the program. The error message is in the form of an JOption pane and allows the game to
   * continue even if input is invalid.
   *
   * @param error String error
   */
  void showErrorMessage(String error);

  /**
   * This method sets a new model in the view which the controller sends after starting a new game
   * with new game settings or resets a game with the same settings. When starting a new game
   * or refreshing , the old game panel and player details panel is removed and the new updated
   * panels are displayed on the frame.
   *
   * @param model readonly model of the dungeon
   */
  void setModel(ReadOnlyDungeonModel model);

  /**
   * This method is mainly used to set flags to make display of player details easier. These flags
   * are set and then the appropriate method is called to display status on a player shooting an
   * arrow.
   */
  void resetFlags();

  /**
   * This method uses setFocusable mainly used for enable/disable view's focus event on
   * keypad mode( using up/down/next key) and used when the user types in a text field. If there is
   * a text field and the user types in some information, it focuses on the text box while entering
   * information.
   */
  void resetFocus();
}
