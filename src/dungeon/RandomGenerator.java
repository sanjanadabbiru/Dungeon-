package dungeon;

/**
 * This interface has a method that randomly generates
 * numbers within a range. 2 classes implement this interface
 * one which generates randomly generated numbers and one
 * which returns a fixed value for testing.
 */
public interface RandomGenerator {
  /**
   * Constructs a random with a range.
   *
   * @param low  lowest number in range
   * @param high highest number in range
   * @return randomly generated number
   */
  int getRandomNumber(int low, int high);
}
