package net.ghielmetti.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compares two strings taking care of the contained numbers in a human way.<br>
 * With this comparator "a10" is bigger than "a2" and not less as in usual
 * comparators.
 *
 * @author lghi
 */
public class NaturalComparator implements Comparator<String> {
  private Pattern splitter = Pattern.compile("(\\d+|\\D+)");

  @Override
  public int compare(String inString1, String inString2) {
    // We split each string as runs of number/non-number strings
    ArrayList<String> array1 = split(inString1);
    ArrayList<String> array2 = split(inString2);
    int index = 0;
    String part1 = "";
    String part2 = "";
    int size = Math.min(array1.size(), array2.size());

    // Compare beginning of string
    for (; index < size; index++) {
      part1 = array1.get(index);
      part2 = array2.get(index);

      if (!part1.equals(part2)) {
        // Try to convert the different run of characters to number
        try {
          // Here, the strings differ on a number
          return Integer.compare(Integer.parseInt(part1), Integer.parseInt(part2));
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
          // Strings differ on a non-number
          return inString1.compareTo(inString2);
        }
      }
    }

    // No differences and the sizes are the same
    if (array1.size() == array2.size()) {
      return 0; // Same strings!
    }

    // The longest string is after the shortest
    return Integer.compare(array1.size(), array2.size());
  }

  /**
   * Splits a string between numbers parts and not numbers part.
   *
   * @param inString
   *          The string to split
   * @return An array with the string split on all parts.
   */
  private ArrayList<String> split(String inString) {
    ArrayList<String> r = new ArrayList<>();
    Matcher matcher = splitter.matcher(inString);

    while (matcher.find()) {
      String m = matcher.group(1);
      r.add(m);
    }

    return r;
  }
}
