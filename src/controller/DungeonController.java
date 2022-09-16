package controller;

import dungeon.Dungeon;

/**
 * Represents a Controller for Dungeon: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface DungeonController {

  /**
   * Execute a single game of dungeon given a dungeon Model. When the game is over,
   * the playGame method ends.
   *
   * @param d a non-null dungeon Model
   */
  void playGame(Dungeon d);
}
