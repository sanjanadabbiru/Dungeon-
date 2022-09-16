package dungeon;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class constructs the graph of edges and vertices
 * according to kruskal's algorithm. Connects location objects
 * through edges and lists out possible edges between a set of caves and
 * makes sure there are no cycles in the graph.
 *
 * @citation
 */
public final class Graph {
  private final HashSet<Edge> leftover;
  private final List<Edge> result;
  private final Random rand;

  /**
   * Class for specifying the edge of a graph
   * using 2 caves. The 2 caves are considered as
   * vertices.
   */
  public class Edge implements Comparable<Edge> {
    Location cave1;
    Location cave2;

    /**
     * Constructs an edge that takes in two caves as
     * input to make an edge.
     *
     * @param cave1 cave 1
     * @param cave2 cave 2
     */
    Edge(Location cave1, Location cave2) {
      this.cave1 = cave1;
      this.cave2 = cave2;
    }

    @Override
    public int compareTo(Edge o) {
      if (cave1.getX() < o.cave1.getX()) {
        return -1;
      } else if (cave1.getX() > o.cave1.getX()) {
        return 1;
      } else if (cave1.getY() < o.cave1.getY()) {
        return -1;
      } else if (cave1.getY() > o.cave1.getY()) {
        return 1;
      } else if (cave2.getX() < o.cave2.getX()) {
        return -1;
      } else if (cave2.getX() > o.cave2.getX()) {
        return 1;
      } else if (cave2.getY() < o.cave2.getY()) {
        return -1;
      } else if (cave2.getY() > o.cave2.getY()) {
        return 1;
      }
      return 0;
    }
  }

  /**
   * Class Subset used for path compression
   * and rank.
   */
  class Subset {
    Location parent;
    int rank;
  }


  int v;
  int e;
  List<Edge> potentialPaths = new ArrayList<>();

  /**
   * Graph class that is constructed through sending vertices and edges
   * as well as random number to randomise the edge creation.
   *
   * @param v    vertices
   * @param e    edges
   * @param rand random number
   */
  Graph(int v, int e, Random rand) {
    leftover = new HashSet<>();
    result = new ArrayList<>();
    this.v = v;
    this.e = e;
    this.rand = rand;
  }

  /**
   * Method to determine which Subset a particular element is in.
   * This can be used for determining if two elements are in the same Subset.
   *
   * @param subsets number of Subsets
   * @param i       location object
   * @return Location object
   */

  Location find(Map<Location, Subset> subsets, Location i) {

    if (subsets.get(i).parent != i) {
      subsets.get(i).parent
              = find(subsets, subsets.get(i).parent);
    }


    return subsets.get(i).parent;
  }

  /**
   * Union: Join two Subsets into a single Subset.
   * A union-find algorithm is an algorithm that performs two
   * useful operations on such a data structure
   *
   * @param subsets keeps track of a set of elements
   *                partitioned into a number of disjoint (non-overlapping) Subsets
   * @param x       x vertex
   * @param y       y vertex
   */
  void union(Map<Location, Subset> subsets, Location x, Location y) {
    Location xroot = find(subsets, x);
    Location yroot = find(subsets, y);

    if (subsets.get(xroot).rank
            < subsets.get(yroot).rank) {
      subsets.get(xroot).parent = yroot;
    } else if (subsets.get(xroot).rank
            > subsets.get(yroot).rank) {
      subsets.get(yroot).parent = xroot;
    } else {
      subsets.get(yroot).parent = xroot;
      subsets.get(xroot).rank++;
    }
  }

  /**
   * Kruskal's algorithm finds a minimum spanning forest of an undirected edge-weighted graph.
   * If the graph is connected, it finds a minimum spanning tree.
   * The algorithm is a Greedy Algorithm. The Greedy Choice is to pick the smallest
   * weight edge that does not cause a cycle in the MST constructed so far.
   */

  void kruskalMst() {


    int i = 0;

    Map<Location, Subset> subsets = new HashMap<>();
    for (Edge edge : potentialPaths) {
      subsets.put(edge.cave1, new Subset());
      subsets.put(edge.cave2, new Subset());
    }
    for (Map.Entry<Location, Subset> entry : subsets.entrySet()) {
      entry.getValue().parent = entry.getKey();
      entry.getValue().rank = 0;
    }


    i = 0;
    int e = 0;

    while (e < v - 1) {

      HashSet<Integer> set = new HashSet<>();
      int index = rand.nextInt(potentialPaths.size());
      while (set.contains(index)) {
        index = rand.nextInt(potentialPaths.size());
      }
      set.add(index);
      Edge next_edge = potentialPaths.get(index);

      Location x = find(subsets, next_edge.cave1);
      Location y = find(subsets, next_edge.cave2);

      if (x != y) {
        e++;
        result.add(next_edge);
        union(subsets, x, y);
      }

    }


    for (Edge edge : potentialPaths) {
      leftover.add(edge);
    }
    for (int j = 0; j < result.size(); j++) {
      if (leftover.contains(result.get(j))) {
        leftover.remove(result.get(j));
      }

    }

  }

  /**
   * Sets the list of potential paths that contain the edges
   * in a graph. The potential edges of a graph are all connected.
   *
   * @param potential paths
   */
  public void setPotentialPaths(List<Edge> potential) {
    this.potentialPaths = potential;
  }

  /**
   * Gets the leftover edges used for getting
   * the interconnected edges. Gives a unique set of edges to pick from.
   *
   * @return Set of edges that are unique
   */
  public HashSet<Edge> getLeftover() {
    return leftover;
  }

  /**
   * Gets the list of edges that represent all the connected
   * edges of a graph. Gives the result of the entire graph.
   *
   * @return
   */
  public List<Edge> getResult() {
    return result;
  }

}