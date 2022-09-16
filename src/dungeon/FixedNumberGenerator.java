package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the RandomGenerator that returns a fixed
 * middle value for a given range between low and high.
 */
public final class FixedNumberGenerator implements RandomGenerator {


  List<Integer> listInt = new ArrayList<>();

  @Override
  public int getRandomNumber(int low, int high) {
    return (low + high) / 2;
  }


}
