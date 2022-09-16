# Project 5 - Graphical Adventure Game


## About/Overview

Each location in the grid represents a location in the dungeon where a player can explore and can be connected to at most four (4) other locations: one to the north, one to the east, one to the south, and one to the west. Notice that in this dungeon locations "wrap" to the one on the other side of the grid. For example, moving to the north from row 0 (at the top) in the grid moves the player to the location in the same column in row 5 (at the bottom). A location can further be classified as tunnel (which has exactly 2 entrances) or a cave (which has 1, 3 or 4 entrances). There is a path from every cave in the dungeon to every other cave in the dungeon and not all dungeons "wrap" from one side to the other.
Additions to the project statement include addings and slaying monsters or Otyughs.
There is always at least one Otyugh in the dungeon located at the specially designated end cave. The actual number is specified on the command line. There is never an Otyugh at the start.
Otyugh only occupy caves and are never found in tunnels. Their caves can also contain treasure or other items.
They can be detected by their smell. In general, the player can detect two levels of smell:
a less pungent smell can be detected when there is a single Otyugh 2 positions from the player's current location detecting a more pungent smell either means that there is a single Otyugh 1 position from the player's current location or that there are multiple Otyughs within 2 positions from the player's current location.


## List of Features
- Both wrapping and non-wrapping dungeons can be created with different degrees of interconnectivity
- Provides support for at least three types of treasure: diamonds, rubies, and sapphires
treasure to be added to a specified percentage of caves. For example, the client asks the model to add treasure to 20% of the caves and the model should add a random treasure to at least 20% of the caves in the dungeon. A cave can have more than one treasure.
- A player enters the dungeon at the start
- Provides a description of the player that, at a minimum, includes a description of what treasure the player has collected
- Provides a description of the player's location that at the minimum includes a description of treasure in the room and the possible moves (north, east, south, west) that the player can make from their current location
- A player moves from their current location
- A player picks up treasure that is located in their same location 
- Player starts with 3 crooked arrows but can find additional arrows in the dungeon with the same frequency as treasure. 
- It takes 2 hits to kill an Otyugh. Players has a 50% chance of escaping if the Otyugh if they enter a cave of an injured Otyugh that has been hit by a single crooked arrow.
- A player that has arrows, can attempt to slay an Otyugh by specifying a direction and distance in which to shoot their crooked arrow.
- Exposes all game settings including the size of the dungeon, the interconnectivity, whether it is wrapping or not, the percentage of caves that have treasure, and the number of Otyughs through one or more items on a JMenu.
- Provide an option for quitting the game, starting a new game with new game settings or a restarting an existing game on a JMenu.
- Displays the dungeon to the screen using graphical representation. The view should begin with a mostly blank screen and display only the pieces of the maze that have been revealed by the user's exploration of the caves and tunnels. Dungeons that are bigger than the area allocated it to the screen should provide the ability to scroll the view.
- Allows the player to move through the dungeon using a mouse click on the screen in addition to the keyboard arrow keys. A click on an invalid space in the game would not advance the player.
- Displays the details of a dungeon location to the screen. For instance, does it have treasure, does it have an arrow, does it smell.
- Provides an option to get the player's description and provides an option for the player to pick up a treasure or an arrow through pressing a key on the keyboard.
- Provides an option for the player to shoot an arrow by pressing a key on the keyboard followed by an arrow key to indicate the direction.
- Provides a clear indication of the results of each action a player takes.
- Additional feature of adding 3 thieves to the dungeon where once the player has encountered the thief, all the treasure collected is lost.


## How to run
Console text based game -> java -jar Project5Final.jar 6 6 1 false 100 1
GUI based adventure game -> java -jar Project5Final.jar 

## How to use the program

This project works both as a text based console game as well as a GUI based game as well. If the user wants to play the console game, they have to enter the game settings beside the jar command and hit enter. If the user wants to play the GUI based game, then the user has to just run the jar file without entering any game settings.
Menu-driven program that takes in user input:
Press 1 -  Move
Press 2 -  Pick up treasure
Press 3 -  Pick up arrows
Press 4 -  Shoot arrows
Press q/quit - Quit the game
After pressing 1 , the player should press "n/N/w/W/e/E/s/S" to move to a valid direction. If the player attempts to press any other input, the player gets an invalid choice. Any other input is invalid and will wait for the user to input a right input to continue.
After pressing 4, the player must give a direction of ""n/N/w/W/e/E/s/S"" and a distance in positive integers. Any other input is invalid and will wait for the user to input a right input to continue. To move after shooting or picking up treasure or arrows, press 1 again to move.

GUI based Adventure Game
1. Once the GUI opens, you are presented with a random mock dungeon. The menu on the taskbar contains options to start a new game by entering the parameters, to quit the game , instructions to play the game and the controls and to restart the existing game.
2. Click on start new game to enter new game settings in a dialog box which accepts input for all the game parameters. The user can then enter their input(except the radio button all accepted inputs are number). If the user enters a wrong input in the textfield, then an error message pops up and the user has to enter a valid input. Once all the values are confirmed, click on submit and then click on yes to exit the dialog box to play a new game. If you do not want to change any game settings then directly press no(without clicking on submit) and it returns to the original dungeon.
3. To move, press the up,down,right or left arrow key or even click on the direction you want to move towards on the dungeon.
4. To shoot a monster, first press 's' then mention the direction you want to shoot by pressing up,down,right or left arrow key. Then enter the distance you want to shoot your arrow at by entering the number in a textbox.
5. To pick up treasure, press 't' on the keyboard.
6. To pick up arrows, press 'a' on the keyboard.
7. If you choose to quit the game, choose quit game in the menu option.
8. You can also exit the game by pressing the 'x'(exit) button on top.
9. The player details on the top panel include type of smell,Cave/Tunnel location and coordinates, list of arrows and treasure, possible direction from the current location.
10. To restart the game with the same dungeon settings, choose Restart game in the menu option.
11. To check whether the treasure and arrows has been updated after picking up, move the player to see the updated changes.
12. Three thieves are present in the dungeon randomly and they steal your treasure when encountered.
Run Main.java

## Description of examples
``output1.png``
- This screenshot displays the input dialog box that takes in game settings. The game settings include row,column,interconnectivity,wrapped/unwrapped,number of monsters and percentage of treasure and arrows. The user has to press submit and yes to confirm inputs. If the user wants to remain with the existing dungeon, the user has to directly press no.

``output2.png``
- This screenshot displays a situation when the monster kills the player. The monster image is displayed at its cave and a message which states that the player has been eaten by monster. The player can no longer make a move after the game is over and that is mentioned in an alert box.

``output3.png``
- When the player wants to shoot, will have to press 's' then an arrow key for direction and a textbox will appear to enter the distance the arrow should travel to. The player then has to enter a valid number and press ok to confirm the distance to shoot.

``output4.png``
- This screenshot displays when the player has shot an arrow at correct distance and direction to hit the monster once. This shows that the player has a 50% chance at surviving or dying if it enters the cave of the damaged otyugh. The player can also smell a pungent smell indicated by the strong green colour of the odour. 

``output5.png``
- This screenshot displays when the player has given an incorrect input of any game settings such as negative values or string inputs. An error message pops up and handles the error and prompts the user to enter the correct value again.

``output6.png``
- This screenshot displays when the player has shot an arrow at correct distance and direction to hit the monster twice. This shows that the player has slain the monster and can enter the cave without being eaten. The smell is eliminated as the monster is now dead.

``output7.png``
- This screenshot displays a situation where the player has won by reaching the end cave alive  i.e slaying the monster at the end cave. A congratulatory message is displayed and the player can no longer move from the current position.

``output8.png``
- This screenshot displays when the player has shot an arrow at a wrong direction and/or the wrong distance. This means that the arrow has not struck a monster and therefore misses it.The arrow count automatically decreases.

``output9.png``
- This screenshot displays the overall layout of the game. The game has 2 main panels apart from a menu bar fixated on top. The top most panel contains details on the player location details such as whether the player smells something(no smell, mildly pungent or strongly pungent), the location coordinates and details, how many arrows and how much of treasure is there, the possible directions from the current location. Also displays how many arrows the player has or how much treasure is there with them currently. It also displays status about whether an arrow missed a monster, whether a monster was slayed or damaged. 

``output10.png``
- This screenshot displays a situation where the player has used up all their arrows and has no more arrows left to shoot and the appropriate message is displayed.

``output11.png``
- This screenshot displays an error message pop up when the distance entered is negative or a text which is considered invalid.

``output12.png``
- This screenshot displays an instructions pop up when the player clicks on instructions to play tab on the menu. The instructions to play mention how to play the game.

``output13.png``
- This screenshot displays a situation where the player is robbed of treasure when the player encounters a thief in the location. The thief becomes visible, the treasure is decreased to 0 and a message which states that the player has been robbed is displayed.

## Design/Model Changes
- The original design did not have an enumeration for directions however the new design has a separate enumeration for direction that indicate where the player is moving towards.
- The original design had an abstract class which subclassed two classes: Cave and Tunnel. However the new design has only one class called Cave which also takes care of the situtation that a tunnel is a special type of cave.
- The original design had an abstract class for dungeon to show wrapping and non wrapping aspects of the dungeon however the new model has only one class DungeonClass which implements both kinds of grids be it wrapping or non wrapping.
- The original design had no enumeration for smell but the new design has an enumeration for smell to represent pungent or less pungent smell.
- The original design had no classes to accomodate a monster into the dungeon which the new design does and associates a health to it
- The new design has more additional methods to accomodate the removing of a monster in a dungeon or shooting arrows.
- The old design did not accomodate the view aspect of the project and so a new class and interface called DungeonView and DungeonViewClass have been added which in turn have 2 classes called DungeonPanel and PlayerDetailsPanel which correspond to the grid panel and panel which displays the player details. 
- Both these panels extend JPanel and the View class extends Jframe. 
- There is a new addition of a readonly model interface that the main model extends. It contains those methods that the view requires.
- There is also addition of removeTreasure method and setting a thief in a dungeon for the extra credit requirements.

## Assumptions
- Game is over when the player reaches the end cave.
- One monster per cave.
- Row and Column limit is 6 for a grid.
- In a wrapping dungeon all edges are not wrapped. Only a random few.
- There is no game over condition. The player may move even after the end cave has been reached.
- The user can use the pick up treasure option to pick up treasure at their current location.
- The user stays in the same place if the user gives an invalid move position.
- The user must press 7 after every turn to see the possible moves.
- A mock dungeon is first visible to the player. A player can start a new fresh game by clicking on start new game on the menu and entering new game settings.


## Limitations
- The dungeon cannot be constructed for rows and columns less than 6. 
- The player can be stuck between 2 directions if the user doesn't use the information of possible moves correctly when the user keeps flitting between 2 directions.
- The player can quit the game only when the menu option is provided.
- The restart option works after the player starts a new game and not with the first inital mock dungeon as that is just an entry point to the game. After the player starts a new game with new game settings and player wants to restart then restarts works accordingly.



## Citations
- https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
- https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
- https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=7b3154e5-7740-4130-954f-adc201647fc8&start=1.230161
- https://northeastern.instructure.com/courses/90366/pages/8-dot-5-controller-for-turtle-example?module_item_id=6535610
- https://northeastern.instructure.com/courses/90366/pages/8-dot-3-testing-the-controller-in-isolation?module_item_id=6535608
- https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
- https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
- https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
- https://stackoverflow.com/questions/2623995/swings-keylistener-and-multiple-keys-pressed-at-the-same-time
- https://www.tutorialspoint.com/java_dip/java_buffered_image.htm










