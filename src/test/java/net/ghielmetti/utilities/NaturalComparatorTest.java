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
    List<String> list = createList();

    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < list.size(); j++) {
        String si = list.get(i);
        String sj = list.get(j);
        assertEquals("The strings \"" + si + "\" and \"" + sj + "\" are incorrectly ordered", (int)Math.signum(Integer.compare(i, j)), (int)Math.signum(inc.compare(si, sj)));
      }
    }
  }

  /** Tests {@link NaturalComparator#compare(String, String)} */
  @Test
  public void compare_usedInSortOperations_correctlySortsTheList() {
    List<String> listCorrect = createList();
    List<String> listShuffled = new ArrayList<>(listCorrect);
    Collections.shuffle(listShuffled);
    Collections.sort(listShuffled, inc);

    System.out.println("Correct list:" + listCorrect);
    System.out.println("Sorted list :" + listShuffled);

    // The two lists should be the same
    for (int i = 0; i < listCorrect.size(); i++) {
      assertEquals(listCorrect.get(i), listShuffled.get(i));
    }
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
    r.add("a 1 b");
    r.add("a 12 b");
    r.add("a 26 b 11");
    r.add("a 100 b");
    r.add("a0b");
    r.add("a1b");
    r.add("a01b");
    r.add("a001ba");
    r.add("a001bb");
    r.add("a01c");
    r.add("a1cdef");
    r.add("a01cdef");
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
    r.add("a26b1073c");
    r.add("a26b1073c5");
    r.add("a12525334884473251551122626363352521441273745485584573456626346237458584767363166234374858683563t");
    r.add("a23057823457762636326483583856793486762734826734284578358684586793852676834263847265983457983675d");
    r.add("ab");
    r.add("b");

    return r;
  }
}
