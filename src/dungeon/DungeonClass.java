package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class implements the Dungeon interface
 * and all the methods such as moving the player,
 * picking up treasure from a location, displaying details
 * regarding player's treasure and location. It also has methods such
 * as creating a dungeon grid, assigning treasures and locations.
 */
public final class DungeonClass implements Dungeon {
  private final Map<Location, List<Location>> allEdges;
  private final List<List<Location>> caveList;
  private Map<Location, List<Directions>> visitedState;
  private final int row;
  private final int col;
  private final int numberOfMonsters;
  private final int interconnectivity;
  private final boolean isWrapped;
  private final int x;
  private final Random rand;
  private List<Graph.Edge> result;
  private Player player;
  private final Graph graph;
  private final List<Graph.Edge> potentialPaths;
  private Map<Location, Integer> distance;


  /**
   * Constructs a dungeon with various parameters
   * from the user such as dimensions of grid, whether it's wrapped
   * or not, the player object as well as the percentage of caves having treasure.
   *
   * @param row               value for row
   * @param col               value for column
   * @param interconnectivity value for interconnectivity
   * @param isWrapped         true for wrapped, false for unwrapped
   * @param x                 percentage of caves containing treasure
   * @param player            player object created
   * @param numberOfMonsters  number of monsters
   * @param rand              random number generated
   */
  public DungeonClass(int row, int col, int interconnectivity,
                      boolean isWrapped, int x, Player player, int numberOfMonsters, Random rand) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("row and column cannot be negative");
    }
    if (row < 6 || col < 6) {
      throw new IllegalArgumentException("row and column cannot be less than 6");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("degree of interconnectivity cannot be negative");
    }
    if (x < 0 || x > 100) {
      throw new IllegalArgumentException("percentage of caves holding "
              + "treasure cannot be negative or over 100");
    }
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }

    if (numberOfMonsters < 1) {
      throw new IllegalArgumentException("Number of monsters cannot be less than 1");
    }

    if (rand == null) {
      throw new IllegalArgumentException("Random number cannot be null");
    }
    result = new ArrayList<>();
    caveList = new ArrayList<>();
    this.row = row;
    this.rand = rand;
    this.col = col;
    this.x = x;
    visitedState = new HashMap<>();
    this.numberOfMonsters = numberOfMonsters - 1;
    player.setRowCol(row, col);
    this.allEdges = new HashMap<>();
    this.interconnectivity = interconnectivity;
    this.isWrapped = isWrapped;
    this.player = player;
    int vertices = row * col;
    int edges = 2 * row * col - row - col;
    graph = new Graph(vertices, edges, rand);
    potentialPaths = new ArrayList<>();
    distance = new HashMap<>();
    createDungeon();
    assignLocation();
    assignTreasure();
    assignArrow();
    startOrEndCave();
    assignMonster();
    assignThief();
    detectSmell();

  }

  private void assignTreasure() {
    List<Location> properCave = new ArrayList<>();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        if (cave.isTunnel()) {
          continue;
        } else {
          properCave.add(cave);
        }
      }
    }
    int percentCaves = x * properCave.size();
    int treasureCaves = (percentCaves / 100);
    for (int i = 0; i < treasureCaves; i++) {
      List<Treasure> treasureList = new ArrayList<>();
      Location randomCaves = properCave.get(rand.nextInt(properCave.size()));
      int treasureCapacity = rand.nextInt(3) + 1;
      for (int j = 0; j < treasureCapacity; j++) {
        Treasure treasure = Treasure.values()[rand.nextInt(Treasure.values().length)];
        treasureList.add(treasure);
      }
      randomCaves.setTreasureList(treasureList);
      properCave.remove(randomCaves);
    }
  }

  private void assignArrow() {
    List<Location> expandedCaveList = new ArrayList<>();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        expandedCaveList.add(cave);
      }
    }
    int percentLocations = x * expandedCaveList.size();
    int arrowLocations = (percentLocations / 100);
    for (int i = 0; i < arrowLocations; i++) {
      List<Arrow> arrowList = new ArrayList<>();
      Location randomCaves = expandedCaveList.get(rand.nextInt(expandedCaveList.size()));
      int arrowCapacity = rand.nextInt(3) + 1;
      for (int j = 0; j < arrowCapacity; j++) {
        arrowList.add(Arrow.CROOKED_ARROW);
      }
      randomCaves.setArrowList(arrowList);
      expandedCaveList.remove(randomCaves);
    }

  }

  private void assignMonster() {
    int monsters = this.numberOfMonsters;
    List<Location> properCave = new ArrayList<>();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        if (cave.isTunnel() || cave == getStartCave() || cave == player.getEndCave()) {
          continue;
        } else {
          properCave.add(cave);
        }
      }
    }
    if (this.numberOfMonsters > properCave.size() - 1) {
      throw new IllegalArgumentException("Number of monsters cannot be more than the size of cave");
    }
    for (int i = 0; i < this.numberOfMonsters; i++) {
      Location randomCaves = properCave.get(rand.nextInt(properCave.size()));
      randomCaves.setMonster();
      properCave.remove(randomCaves);
    }
  }

  private void assignThief() {
    List<Location> properCave = new ArrayList<>();
    for (List<Location> list : caveList) {
      for (Location cave : list) {
        properCave.add(cave);
      }
    }
    for (int i = 0; i < 3; i++) {
      Location randomCaves = properCave.get(rand.nextInt(properCave.size()));
      randomCaves.setThief();
      properCave.remove(randomCaves);
    }
  }

  private void createUnwrappedGrid() {
    Graph.Edge edge1 = null;
    Graph.Edge edge2 = null;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (i == row - 1 && j == col - 1) {
          continue;
        } else if (i == row - 1) {
          edge1 = graph.new Edge(caveList.get(i).get(j), caveList.get(i).get(j + 1));
          potentialPaths.add(edge1);
        } else if (j == col - 1) {
          edge2 = graph.new Edge(caveList.get(i).get(j), caveList.get(i + 1).get(j));
          potentialPaths.add(edge2);
        } else {
          edge1 = graph.new Edge(caveList.get(i).get(j), caveList.get(i).get(j + 1));
          edge2 = graph.new Edge(caveList.get(i).get(j), caveList.get(i + 1).get(j));
          potentialPaths.add(edge1);
          potentialPaths.add(edge2);
        }
      }
    }
  }

  /**
   * Returns a list of all potential edges that exist in a
   * graph of particular dimensions.
   *
   * @return a copy of the list of edges
   */
  @Override
  public List<Graph.Edge> getPotentialPaths() {
    List<Graph.Edge> pPaths = this.potentialPaths;
    return pPaths;
  }

  private void createWrappedGrid() {
    Graph.Edge edge1 = null;
    Graph.Edge edge2 = null;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (i == row - 1 && j == col - 1) {
          edge1 = graph.new Edge(caveList.get(i).get(j), caveList.get(row - 1).get(0));
          edge2 = graph.new Edge(caveList.get(i).get(j), caveList.get(0).get(col - 1));
          potentialPaths.add(edge1);
          potentialPaths.add(edge2);
        } else if (i == row - 1) {
          edge2 = graph.new Edge(caveList.get(i).get(j), caveList.get(0).get(j));
          potentialPaths.add(edge2);
        } else if (j == col - 1) {
          edge1 = graph.new Edge(caveList.get(i).get(j), caveList.get(i).get(0));
          potentialPaths.add(edge1);
        } else {
          continue;
        }
      }
    }
  }

  private void createDungeon() {
    for (int i = 0; i < row; i++) {
      List<Location> tempList = new ArrayList<>();
      for (int j = 0; j < col; j++) {
        tempList.add(new Cave(i, j));
      }
      caveList.add(tempList);
    }
    createUnwrappedGrid();
    if (this.isWrapped) {
      createWrappedGrid();
    }
    graph.setPotentialPaths(potentialPaths);
    graph.kruskalMst();
    result = graph.getResult();

    addInterconnectivity();
    Collections.sort(result);
    createAllEdgesList();


  }

  private void addInterconnectivity() {
    HashSet<Graph.Edge> leftover = graph.getLeftover();
    List<Graph.Edge> leftoverList = new ArrayList<>(leftover);
    Collections.sort(leftoverList);

    if (interconnectivity > leftoverList.size()) {
      throw new IllegalArgumentException("Interconnectivity cannot be"
              + " greater than the number of leftover nodes");
    } else {
      for (int i = 0; i < this.interconnectivity; i++) {
        int index = rand.nextInt(leftoverList.size());
        result.add(leftoverList.get(index));
        leftoverList.remove(index);
      }
    }
  }


  private void createAllEdgesList() {
    for (Graph.Edge edge : result) {
      if (allEdges.containsKey(edge.cave1)) {
        List<Location> tempList = allEdges.get(edge.cave1);
        tempList.add(edge.cave2);
        allEdges.put(edge.cave1, tempList);
        List<Location> tList;
        if (allEdges.containsKey(edge.cave2)) {
          tList = allEdges.get(edge.cave2);
        } else {
          tList = new ArrayList<>();
        }
        tList.add(edge.cave1);
        allEdges.put(edge.cave2, tList);
      } else {
        List<Location> tempList = new ArrayList<>();
        tempList.add(edge.cave2);
        allEdges.put(edge.cave1, tempList);
        List<Location> tList;
        if (allEdges.containsKey(edge.cave2)) {
          tList = allEdges.get(edge.cave2);
        } else {
          tList = new ArrayList<>();
        }
        tList.add(edge.cave1);
        allEdges.put(edge.cave2, tList);
      }
    }
  }

  private void startOrEndCave() {
    Location startCave = caveList.get(rand.nextInt(caveList.size())).get(
            rand.nextInt(caveList.get(0).size()));
    Location endCave = caveList.get(rand.nextInt(caveList.size())).get(
            rand.nextInt(caveList.get(0).size()));
    int pathDistance = checkPathDistance(startCave, endCave);
    while (pathDistance < 5 || (startCave.isTunnel() || endCave.isTunnel())) {
      startCave = caveList.get(rand.nextInt(caveList.size())).get(
              rand.nextInt(caveList.get(0).size()));
      endCave = caveList.get(rand.nextInt(caveList.size())).get(
              rand.nextInt(caveList.get(0).size()));
      pathDistance = checkPathDistance(startCave, endCave);
    }
    endCave.setMonster();
    player.setPlayerPosition(startCave);
    player.setEndCave(endCave);
    player.possibleMoves(allEdges);
    visitedState.put(startCave, player.getPossibleMoves());

  }

  private int checkPathDistance(Location startCave, Location endCave) {
    if (startCave == null || endCave == null) {
      throw new IllegalArgumentException("Start and/or end cave cannot be null");
    }
    Map<Location, Location> path = new HashMap<>();
    for (Map.Entry<Location, List<Location>> entry : allEdges.entrySet()) {
      distance.put(entry.getKey(), -1);
    }
    distance.put(startCave, 0);
    path.put(startCave, startCave);

    Queue<Location> q = new LinkedList<>();
    q.add(startCave);

    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        Location cave = q.remove();
        List<Location> adjList = allEdges.get(cave);
        for (Location adjCave : adjList) {
          if (distance.get(adjCave) == -1) {
            distance.put(adjCave, distance.get(cave) + 1);
            path.put(adjCave, cave);
            q.add(adjCave);
          }
        }
      }
    }
    return distance.get(endCave);
  }

  @Override
  public List<List<Location>> getCaveList() {
    List<List<Location>> tempcaveList = this.caveList;
    return tempcaveList;
  }

  @Override
  public Map<Location, List<Location>> getAllEdges() {
    Map<Location, List<Location>> tempAllEdges = this.allEdges;
    return tempAllEdges;
  }

  /**
   * Moves player according to four directions mentioned in the
   * enumeration Directions - NORTH,SOUTH,EAST,WEST.
   *
   * @param direction direction player can move to
   * @return -1 if invalid move
   */
  @Override
  public int movePlayer(Directions direction) {
    int flag = 0;
    if (direction.values() == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    List<Directions> possibleMoves = player.getPossibleMoves();
    List<Location> neighbor = allEdges.get(player.getPlayerPosition());
    for (Directions possibleDirection : possibleMoves) {
      if (possibleDirection == direction) {
        for (Location list : neighbor) {
          flag = movePlayerToAdjacent(list, direction, flag);
          if (flag != 0) {
            return flag;
          }
        }
      }
    }
    flag = -1;
    return flag;
  }

  @Override
  public Player getPlayerObject() {
    return player;
  }


  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  private int movePlayerToAdjacent(Location list, Directions direction, int flag) {
    if (list == null || direction == null) {
      throw new IllegalArgumentException("Location / Direction cannot be null");
    }

    if ((player.getPlayerPosition().getX() - list.getX() == direction.getDirectionX()
            && player.getPlayerPosition().getY() - list.getY() == direction.getDirectionY())
            || (player.getPlayerPosition().getX() - list.getX() == row - 1
            && player.getPlayerPosition().getY() - list.getY() == 0
            && direction == Directions.SOUTH)
            || (player.getPlayerPosition().getX() - list.getX() == -(row - 1)
            && player.getPlayerPosition().getY() - list.getY() == 0
            && direction == Directions.NORTH)
            || (player.getPlayerPosition().getX() - list.getX() == 0
            && player.getPlayerPosition().getY() - list.getY() == col - 1
            && direction == Directions.EAST)
            || (player.getPlayerPosition().getX() - list.getX() == 0
            && player.getPlayerPosition().getY() - list.getY() == -(col - 1)
            && direction == Directions.WEST)) {
      return checkPlayerMove(list, flag);
    }
    return flag;
  }

  private int checkPlayerMove(Location list, int flag) {
    if (list == null) {
      throw new IllegalArgumentException("Location cannot  be null");
    }
    if (list.isMonster()) {
      if (list.getMonsterHealth() == 50) {

        int chance = rand.nextInt(2);
        if (chance == 1) {
          flag = -2;
          player.setPlayerPosition(list);
          player.possibleMoves(allEdges);
          visitedState.put(list, player.getPossibleMoves());
          player = null;

          return flag;
        }
      } else {
        // this flag corresponds to when the player is eaten by the monster
        flag = -2;
        player.setPlayerPosition(list);
        player.possibleMoves(allEdges);
        visitedState.put(list, player.getPossibleMoves());
        player = null;


        return flag;
      }
    }

    if (list.isThief()) {
      player.removeTreasure();
      // this flag corresponds to a thief present in the current location
      flag = 5;
    }

    player.setPlayerPosition(list);
    if (player.getPlayerPosition() == player.getEndCave()) {
      if (flag == 5) {
        // this flag corresponds to a thief present in the end location
        flag = 6;
      } else {
        // this flag corresponds to when the player has reached the end cave
        flag = 1;
      }
    }

    player.possibleMoves(allEdges);
    visitedState.put(list, player.getPossibleMoves());
    detectSmell();
    return flag;
  }

  /**
   * Method that allows the player to pick up treasure and checks whether
   * the location is a cave or a tunnel. Since caves contain treasure and
   * tunnels do not, the conditions are checked here and treasure is added to the
   * player's treasure bag.
   */
  @Override
  public int pickUpTreasure() {
    int flag = 0;
    if (player.getPlayerPosition().isTunnel()) {
      flag = -1;
      return flag;
    } else {
      player.setTreasureList(player.getPlayerPosition().getTreasureList());
      player.getPlayerPosition().setTreasureList(new ArrayList<>());
    }
    return flag;
  }

  @Override
  public int pickUpArrow() {
    int flag = 0;
    if (player.getPlayerPosition().getArrowList().isEmpty()) {
      flag = -1;
      return flag;
    } else {
      player.setArrowList(player.getPlayerPosition().getArrowList());
      player.getPlayerPosition().setArrowList(new ArrayList<>());
    }
    return flag;
  }

  @Override
  public List<Graph.Edge> getResult() {
    List<Graph.Edge> tempResult = this.result;
    return tempResult;
  }

  /**
   * Returns a description of the player's treasure after collecting
   * when it encounters a treasure bag in a cave.
   *
   * @return description of treasure collected
   */
  @Override
  public String playerTreasureCollected() {
    return String.format("Treasure Collected by Player :  %s \n Arrows Collected by Player: %s",
            player.getTreasureList(), player.getArrowList());
  }

  @Override
  public Location getStartCave() {
    return player.getPlayerPosition();
  }

  /**
   * Returns a description of the player's possible moves
   * from the current position of a player. It also displays the
   * treasure in a particular cave if there is treasure present.
   *
   * @return String description of possible moves and treasure present in cave.
   */
  @Override
  public String playerLocationDetails() {
    List<Directions> paths = player.getPossibleMoves();
    String potentialPaths = paths.stream().map(Object::toString)
            .collect(Collectors.joining(", "));
    String smell = "";
    if (player.getSmell() == Smell.LESS_PUNGENT) {
      smell = "You smell something mildly pungent nearby..\n";
    } else if (player.getSmell() == Smell.MORE_PUNGENT) {
      smell = "You smell something horrible nearby..\n";
    } else if (player.getSmell() == Smell.NO_SMELL) {
      smell = "You smell nothing!\n";
    }

    if (player.getPlayerPosition().isTunnel()) {
      String tunnel = "";
      tunnel += smell;
      tunnel += "Location is a tunnel\n";
      String location = String.format("Tunnel location is (%d,%d)\n",
              player.getPlayerPosition().getX(), player.getPlayerPosition().getY());
      tunnel += location;
      if (player.getPlayerPosition().getArrowList().isEmpty()) {
        tunnel += "Tunnel does not have any arrows\n";
      } else {
        tunnel += player.getPlayerPosition().getArrowList().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        tunnel += "\n";
      }
      tunnel += potentialPaths;
      return tunnel;
    }

    String cave = " ";
    cave += smell;
    if (player.getPlayerPosition().getArrowList().isEmpty()) {
      cave += "Cave does not have any arrows\n";
    } else {
      cave += player.getPlayerPosition().getArrowList().stream().map(Object::toString)
              .collect(Collectors.joining(", "));
      cave += "\n";
    }
    if (player.getPlayerPosition().getTreasureList().isEmpty()) {
      cave += "Cave does not have any treasure\n";
    } else {
      cave += player.getPlayerPosition().getTreasureList().stream().map(Object::toString)
              .collect(Collectors.joining(", "));
      cave += "\n";
    }
    String location = String.format("Cave location is (%d,%d)\n",
            player.getPlayerPosition().getX(), player.getPlayerPosition().getY());
    cave += location;
    cave += potentialPaths;
    return cave;
  }

  @Override
  public Smell getSmell() {
    return player.getSmell();
  }

  private void assignLocation() {
    Map<Location, Integer> degree = new HashMap<>();
    for (Graph.Edge edge : result) {
      if (degree.containsKey(edge.cave1)) {
        degree.put(edge.cave1, degree.get(edge.cave1) + 1);
      } else {
        degree.put(edge.cave1, 1);
      }
      if (degree.containsKey(edge.cave2)) {
        degree.put(edge.cave2, degree.get(edge.cave2) + 1);
      } else {
        degree.put(edge.cave2, 1);
      }
    }
    for (Graph.Edge edge : result) {
      if (degree.containsKey(edge.cave1)) {
        if (degree.get(edge.cave1) == 2) {
          edge.cave1.setTunnel();
        }
      }
      if (degree.containsKey(edge.cave2)) {
        if (degree.get(edge.cave2) == 2) {
          edge.cave2.setTunnel();
        }
      }
    }
  }

  @Override
  public void detectSmell() {
    int endDist = checkPathDistance(player.getPlayerPosition(), player.getEndCave());
    int closeMonster = 0;
    int farMonster = 0;
    for (Map.Entry<Location, Integer> entry : distance.entrySet()) {
      if (entry.getValue() == 2 && entry.getKey().isMonster()) {
        farMonster++;
      } else if (entry.getValue() == 1 && entry.getKey().isMonster()) {
        closeMonster++;
        break;
      }
    }
    if (closeMonster == 1 || farMonster > 1) {
      player.setSmell(Smell.MORE_PUNGENT);
    } else if (farMonster == 1) {
      player.setSmell(Smell.LESS_PUNGENT);
    } else {
      player.setSmell(Smell.NO_SMELL);
    }
  }


  @Override
  public int getPlayer() {
    if (player == null) {
      return 0;
    }
    return 1;
  }


  @Override
  public int shootArrow(Directions direction, int distance) {
    int count = 0;
    if (direction == null || distance < 0) {
      throw new IllegalArgumentException("Direction cannot be null / Distance cannot be negative");
    }
    int flag = -1;
    boolean startTunnel = false;
    Location arrowPosition = player.getPlayerPosition();
    if (arrowPosition.isTunnel()) {
      startTunnel = true;
    }
    Directions arrowDirection = direction;
    if (player.getArrowList().isEmpty()) {
      // this flag corresponds to when there are no more arrows left to shoot
      flag = 0;
      return flag;
    }
    player.removeArrow();
    while (distance > 0) {
      count++;
      int endFlag = -1;
      while (arrowPosition.isTunnel()) {
        for (Location tunnel : allEdges.get(arrowPosition)) {
          int newX = tunnel.getX() - arrowPosition.getX();
          int newY = tunnel.getY() - arrowPosition.getY();
          List<Object> result;
          result = shootInsideTunnel(newX, newY, arrowDirection, tunnel,
                  arrowPosition, distance, endFlag);
          if ((int) result.get(0) != -1) {

            arrowDirection = (Directions) result.get(1);
            arrowPosition = (Location) result.get(2);
            distance = (int) result.get(3);
            endFlag = (int) result.get(4);
          }


        }
      }
      if (distance == 0) {
        if (endFlag == -1) {
          return flag;
        }
        if (distance == 0 && arrowPosition.isMonster()) {
          arrowPosition.setMonsterHealth();
          if (arrowPosition.getMonsterHealth() == 0) {
            arrowPosition.removeMonster();
            detectSmell();
            // this flag corresponds to killing the otyugh
            flag = 1;
          } else {
            // this flag corresponds to damaging the otyugh
            flag = 2;
          }
          return flag;
        }
        flag = 3;
        return flag;
      }

      List<Location> neighbor = allEdges.get(arrowPosition);
      for (Location cave : neighbor) {
        int newX = arrowPosition.getX() - cave.getX();
        int newY = arrowPosition.getY() - cave.getY();
        List<Object> result;
        result = shootInsideCave(newX, newY, arrowDirection, cave, arrowPosition,
                distance, endFlag);
        if ((int) result.get(0) == -1) {
          continue;
        }
        arrowPosition = (Location) result.get(1);
        distance = (int) result.get(2);
        endFlag = (int) result.get(3);

      }
      if (endFlag == -1) {
        return flag;
      }
      if (distance == 0 && arrowPosition.isMonster()) {
        arrowPosition.setMonsterHealth();
        if (arrowPosition.getMonsterHealth() == 0) {
          arrowPosition.removeMonster();
          detectSmell();
          flag = 1;
        } else {
          flag = 2;
        }
        return flag;
      }


    }
    flag = 3;
    return flag;
  }

  private List<Object> shootInsideTunnel(int newX, int newY, Directions arrowDirection,
                                         Location tunnel, Location arrowPosition,
                                         int distance, int endFlag) {
    if (arrowDirection == null || tunnel == null || arrowPosition == null || distance < -1) {
      throw new IllegalArgumentException("Location cannot be null / Distance cannot be negative");

    }
    List<Object> result = new ArrayList<>();
    if (((newX == 1 && newY == 0)) || ((newX == -(row - 1) && newY == 0))) {
      result = moveArrow(arrowDirection, arrowPosition, tunnel, distance, endFlag,
              Directions.SOUTH, Directions.NORTH);

    } else if (((newX == -1 && newY == 0)) || ((newX == (row - 1) && newY == 0))) {
      result = moveArrow(arrowDirection, arrowPosition, tunnel, distance, endFlag,
              Directions.NORTH, Directions.SOUTH);

    } else if (((newX == 0 && newY == -1)) || ((newX == 0 && newY == -(col - 1)))) {
      result = moveArrow(arrowDirection, arrowPosition, tunnel, distance, endFlag,
              Directions.WEST, Directions.EAST);

    } else if (((newX == 0 && newY == 1)) || (newX == 0 && newY == (col - 1))) {
      result = moveArrow(arrowDirection, arrowPosition, tunnel, distance, endFlag,
              Directions.EAST, Directions.WEST);
    } else {
      result.add(-1);
    }
    return result;
  }

  private List<Object> moveArrow(Directions arrowDirection, Location arrowPosition,
                                 Location tunnel, int distance, int endFlag,
                                 Directions toDirection, Directions fromDirection) {
    if (arrowDirection == null || tunnel == null || arrowPosition == null || distance < -1) {
      throw new IllegalArgumentException("Location cannot be null / Distance cannot be negative");

    }

    List<Object> result = new ArrayList<>();
    if (arrowDirection != fromDirection) {
      arrowDirection = toDirection;
      arrowPosition = tunnel;
      if (!arrowPosition.isTunnel()) {
        distance--;
      }
      endFlag = 0;
    } else {
      result.add(-1);
      return result;
    }

    result.add(1);
    result.add(arrowDirection);
    result.add(arrowPosition);
    result.add(distance);
    result.add(endFlag);
    return result;
  }

  private List<Object> shootInsideCave(int newX, int newY, Directions arrowDirection,
                                       Location cave, Location arrowPosition, int distance,
                                       int endFlag) {
    if (arrowDirection == null || cave == null || arrowPosition == null || distance < -1) {
      throw new IllegalArgumentException("Location cannot be null / Distance cannot be negative");

    }
    List<Object> result = new ArrayList<>();

    if ((newX == arrowDirection.getDirectionX() && newY == arrowDirection.getDirectionY())
            || (newX == -(row - 1) && newY == 0 && arrowDirection == Directions.NORTH)
            || (newX == (row - 1) && newY == 0 && arrowDirection == Directions.SOUTH)
            || (newX == 0 && newY == (col - 1) && arrowDirection == Directions.EAST)
            || (newX == 0 && newY == -(col - 1) && arrowDirection == Directions.WEST)) {
      result = moveArrowCave(arrowPosition, cave, distance, endFlag);
    } else {
      result.add(-1);
    }
    return result;
  }

  private List<Object> moveArrowCave(Location arrowPosition, Location cave,
                                     int distance, int endFlag) {
    if (arrowPosition == null || cave == null || distance < -1) {
      throw new IllegalArgumentException("Location cannot be null / Distance cannot be negative");
    }
    arrowPosition = cave;
    if (!arrowPosition.isTunnel()) {
      distance--;
    }
    List<Object> result = new ArrayList<>();
    endFlag = 0;
    result.add(1);
    result.add(arrowPosition);
    result.add(distance);
    result.add(endFlag);
    return result;
  }

  @Override
  public boolean isGameOver() {
    return (player == null) || (player.getPlayerPosition() == player.getEndCave()
            && !player.getEndCave().isMonster());
  }

  @Override
  public Map<Location, List<Directions>> getState() {
    Map<Location, List<Directions>> copyState = new HashMap<>();
    for (Map.Entry<Location, List<Directions>> entry : visitedState.entrySet()) {
      List<Directions> copyDirectionList = new ArrayList<>(entry.getValue());
      copyState.put(entry.getKey(), copyDirectionList);
    }
    return copyState;
  }

}
