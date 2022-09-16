package view;

import dungeon.Directions;

/**
 * Interface that lists methods that the player uses to interact with the model through the
 * controller. These methods include taking in input from the player regarding starting a new game
 * with new game settings. Methods such as moving a player, shooting an arrow or picking up
 * treasure which in turn calls the model's respective method and handles exceptions as well.
 */
public interface Features {

  /**
   * Takes in input from the user through a textbox that pops up taking in values of the
   * game settings. This method takes in these inputs and creates a new model with these inputs.
   * Also handles any exceptions that occur if the inputs are negative or invalid like a string
   * instead of a number. Calls the view error message pop up method which displays an error
   * message whenever an invalid input is given.
   * @param row int row
   * @param col int column
   * @param interconnectivity int interconnectivity
   * @param isWrapped Boolean isWrapped
   * @param percent int percentage
   * @param monsters int number of monsters
   */
  void processInput(String row, String col, String interconnectivity, Boolean isWrapped,
                    String percent, String monsters);

  /**
   * This method takes in an input of a direction which in turns calls the model's move player
   * method. It returns a flag that decides whether the player has moved to which particular
   * direction. Calls the model's move player method by taking in the direction specified by the
   * user in the method parameter.
   * @param direction Direction direction
   * @return int flag
   */
  int movePlayer(Directions direction);

  /**
   * Method call to restart a game with existing game parameters. The restart game feature allows
   * the user to re-use the dungeon with the same randomness and the previous game settings.
   * The previous game settings are not checked for validation again as it restarts only after the
   * player has started a new game with new parameters given in the text box input.
   */
  void restartGame();

  /**
   * Method to pick up an arrow. It calls the method pick up arrow in the model. This enables the
   * controller to directly call the respective method in the model. It then reflects in the
   * game panel. The command press key listener 'a' picks up an arrow at that particular position
   * where there are arrows available.
   */
  void pickUpArrow();

  /**
   * Method to pick up treasure. It calls the method pick up treasure in the model.
   * This enables the controller to directly call the respective method in the model.
   * It then reflects in the game panel. The command press key listener 't' picks up
   * an treasure at that particular position where there are treasures available.
   */
  void pickUpTreasure();

  /**
   * Method to aid in mouse click on the place on the dungeon maze to move the player in the
   * direction the player is clicking on. Method that adds a click listener where the player
   * has to click on a valid place on the dungeon maze and the player moves in the right direction.
   * Calculates whether the click listener listens to a click on the right place.
   * Based on the direction clicked, the player then moves to that position if it is valid.
   * @param direction Direction direction
   */
  int handleCellClick(Directions direction);

  /**
   * Method to call the shoot arrow method in the model. Takes in direction and distance from the
   * player using a keyboard arrows and a textbox with a number input.
   * @param direction Direction direction
   * @param distance int Distance
   * @return int flag
   */
  int shootArrow(Directions direction, int distance);

  /**
   * Method that the controller initiates the game. This method adds the mouse click listener
   * functionality and makes the view visible. The play game method is called in the main method
   * and starts the dungeon game.
   */
  void playGame();
}
