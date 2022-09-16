package controller;

import dungeon.Directions;
import dungeon.Dungeon;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Implements the controller for a dungeon that takes in a menu to pick a choice
 * to move player,pick or shoot an arrow.
 */
public class DungeonConsoleController implements DungeonController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs a controller that takes in a Readable object and an Appendable object
   * for system.in and system.out
   *
   * @param in  System.in
   * @param out System.out
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(Dungeon d) {
    int flag = 100;
    int shootFlag = 100;
    Boolean flagCondition = true;
    Boolean flagCondition2 = true;
    if (d == null) {
      throw new IllegalArgumentException("Invalid Model\n");
    }
    append("******** WELCOME TO THE DUNGEON **********\n");
    append("MENU\n");
    append("Choose 1 to Move\n");
    append("Choose 2 to Pick Up treasure\n");
    append("Choose 3 to Pick Up arrows\n");
    append("Choose 4 to Shoot arrows\n");
    append("q/quit to Quit Game\n");
    append("------------------------------\n");

    append("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n");
    append("You are at the start cave..\n");
    append(d.playerLocationDetails());
    append("\n");
    append("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n");

    append("MENU\n");
    append("Choose 1 to Move\n");
    append("Choose 2 to Pick Up treasure\n");
    append("Choose 3 to Pick Up arrows\n");
    append("Choose 4 to Shoot arrows\n");
    append("q/quit to Quit Game\n");
    append("------------------------------\n");
    while (scan.hasNext() && !d.isGameOver()) {
      append("\n");
      try {
        String in = scan.next();

        switch (in) {
          case "q":
          case "quit":
            append("Quitting the game!\n");
            return;
          case "1":
            try {
              String direction = "";
              append("Enter your move: N,S,E,W\n");
              while (flagCondition) {
                direction = scan.next();
                if (direction.equalsIgnoreCase("N")
                        || direction.equalsIgnoreCase("S")
                        || direction.equalsIgnoreCase("W")
                        || direction.equalsIgnoreCase("E")) {
                  break;
                } else {
                  append("Enter valid move\n");
                  continue;
                }
              }

              if (direction.equalsIgnoreCase("N")) {
                flag = d.movePlayer(Directions.NORTH);
              } else if (direction.equalsIgnoreCase("S")) {
                flag = d.movePlayer(Directions.SOUTH);
              } else if (direction.equalsIgnoreCase("E")) {
                flag = d.movePlayer(Directions.EAST);
              } else if (direction.equalsIgnoreCase("W")) {
                flag = d.movePlayer(Directions.WEST);
              } else {
                throw new IllegalArgumentException("Invalid input");
              }

              if (flag == 1) {
                append("End Cave Reached\n");
              } else if (flag == -1) {
                append("Invalid choice\n");
                flag = 100;
              } else if (flag == -2) {
                append("Oops! You are eaten by the Otyugh :( Better luck next time!\n");
                return;
              } else if (flag == 5) {
                append("Oops! Looks like you were robbed!\n");
              }
              append("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n");
              append(d.playerLocationDetails());
              append("\n");
              append("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n");
              append("MENU\n");
              append("Choose 1 to Move\n");
              append("Choose 2 to Pick Up treasure\n");
              append("Choose 3 to Pick Up arrows\n");
              append("Choose 4 to Shoot arrows\n");
              append("q/quit to Quit Game\n");
              append("------------------------------\n");
            } catch (IllegalArgumentException ime) {
              append(ime.getMessage());
            } catch (NoSuchElementException noe) {
              append("There is no next input!");
            }
            break;
          case "2":
            try {
              d.pickUpTreasure();
              append(d.playerTreasureCollected());
              append("\n");
            } catch (IllegalArgumentException ime) {
              append("There is no treasure to pick up");
            }
            break;
          case "3":
            try {
              d.pickUpArrow();
              append(d.playerTreasureCollected());
              append("\n");
            } catch (IllegalArgumentException ime) {
              append("There are no arrows to pick up");
            }
            break;
          case "4":
            String direction = "";
            append("Enter directions to shoot arrow:\n");
            String distance = "";
            while (flagCondition) {
              direction = scan.next();
              if (direction.equalsIgnoreCase("N")
                      || direction.equalsIgnoreCase("S")
                      || direction.equalsIgnoreCase("W")
                      || direction.equalsIgnoreCase("E")) {
                break;
              } else {
                append("Enter valid move\n");
                continue;
              }
            }
            append("Enter distance\n");
            while (flagCondition2) {
              distance = scan.next();
              if (Integer.parseInt(distance) < 0) {
                append("Enter valid distance\n");
                continue;
              } else {
                break;
              }
            }
            try {
              if (direction.equalsIgnoreCase("N")) {
                shootFlag = d.shootArrow(Directions.NORTH, Integer.parseInt(distance));
              } else if (direction.equalsIgnoreCase("S")) {
                shootFlag = d.shootArrow(Directions.SOUTH, Integer.parseInt(distance));
              } else if (direction.equalsIgnoreCase("E")) {
                shootFlag = d.shootArrow(Directions.EAST, Integer.parseInt(distance));
              } else if (direction.equalsIgnoreCase("W")) {
                shootFlag = d.shootArrow(Directions.WEST, Integer.parseInt(distance));
              } else {
                throw new InputMismatchException("Invalid input");
              }
            } catch (InputMismatchException ie) {
              append("Invalid input!  Choose again please !\n");
            } catch (NoSuchElementException noe) {
              append("There is no next input!");
            } catch (NumberFormatException nfe) {
              append("Distance should be a number\n");
            }
            if (shootFlag == 1) {
              append("You have slayed the otyugh!\n");
            } else if (shootFlag == 2) {
              append("You have damaged the otyugh! Shoot wisely!\n");
              flag = 100;
            } else if (shootFlag == 3) {
              append("Arrow misses the monster\n");
            } else if (shootFlag == 0) {
              append("No more arrows left to shoot :(\n");
            } else if (shootFlag == -1) {
              append("Wrong direction. Arrow wasted :(\n");
            }
            break;
          default:
            append(String.format("Unknown command %s\n", in));
            break;
        }
      } catch (NoSuchElementException ne) {
        append("No such element\n");
      }
    }

    if (d.isGameOver()) {
      append("Game is over");
      return;
    }
    scan.close();
  }

  private void append(String st) {
    try {
      out.append(st);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}

