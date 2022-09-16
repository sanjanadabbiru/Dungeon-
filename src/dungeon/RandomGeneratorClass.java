package dungeon;

import java.util.Random;

/**
 * This class implements the RandomGenerator that uses the
 * java.util.Random class and return a random number within
 * the range specified by the user.
 */
public final class RandomGeneratorClass implements RandomGenerator {

  private final Random rand;

  /**
   * Constructs a random generator that initialises the
   * random variable to Random().
   */
  public RandomGeneratorClass() {
    rand = new Random();
  }

  @Override
  public int getRandomNumber(int low, int high) {
    return rand.nextInt(high - low + 1) + low;
  }

}
