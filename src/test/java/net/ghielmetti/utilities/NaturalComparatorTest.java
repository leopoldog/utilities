package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Tests the {@link NaturalComparator} class.
 *
 * @author lghi
 */
public class NaturalComparatorTest {
  private NaturalComparator inc = new NaturalComparator();

  /** Tests {@link NaturalComparator#compare(String, String)} */
  @Test
  public void compare_twoStrings_returnsTheCorrectStringOrderTakingCareOfNumbers() {
    List<String> listCorrect = createList();
    List<String> listShuffled = new ArrayList<>(listCorrect);
    Collections.shuffle(listShuffled);
    Collections.sort(listShuffled, inc);
    // The two lists should be the same
    assertEquals(listCorrect, listShuffled);
  }

  /**
   * Creates a test list already in the correct order.
   *
   * @return A {@link List} with all the test strings.
   */
  public List<String> createList() {
    List<String> r = new ArrayList<>();

    r.add("");
    r.add("a");
    r.add("a1b");
    r.add("a1c");
    r.add("a2b");
    r.add("a2c1");
    r.add("a2c2");
    r.add("a2c3");
    r.add("a2c15");
    r.add("a2c21");
    r.add("a2c42");
    r.add("a10b");
    r.add("a12b");
    r.add("a26b12");
    r.add("a26b113");
    r.add("a26b113");
    r.add("a26b1073c");
    r.add("a26b1073c5");
    r.add("b");

    return r;
  }
}
