package net.ghielmetti.utilities;

import java.util.Comparator;

/**
 * Compares two strings taking care of the contained numbers in a human way.<br>
 * With this comparator "a10" is bigger than "a2" and not less as in usual
 * comparators.
 *
 * @author lghi
 */
public class NaturalComparator implements Comparator<String> {
  @Override
  public int compare(String inString1, String inString2) {
    int len1 = inString1.length();
    int len2 = inString2.length();
    int i1 = 0;
    int i2 = 0;

    while (i1 < len1 && i2 < len2) {
      char c1 = inString1.charAt(i1);
      char c2 = inString2.charAt(i2);

      if (Character.isDigit(c1) && Character.isDigit(c2)) {
        // If we found digits on both strings it means that until here, the other characters are equals elsewhere the
        // routine has already returned.
        int end1 = i1;
        int end2 = i2;

        // Searches for the last digit in the String1
        while (end1 < len1 && Character.isDigit(inString1.charAt(end1))) {
          end1++;
        }

        // Searches for the last digit in the String2
        while (end2 < len2 && Character.isDigit(inString2.charAt(end2))) {
          end2++;
        }

        // Skips leading zeros in string1
        while (end1 - i1 > end2 - i2) {
          if (inString1.charAt(i1) == '0') {
            i1++;
          } else {
            return 1;
          }
        }

        // Skips leading zeros in the string2
        while (end1 - i1 < end2 - i2) {
          if (inString2.charAt(i2) == '0') {
            i2++;
          } else {
            return -1;
          }
        }

        // Here the numeric part is equally long, we can just compare it character by character
        for (; i1 < end1; i1++, i2++) {
          int c = Character.compare(inString1.charAt(i1), inString2.charAt(i2));

          if (c != 0) {
            return c;
          }
        }
      } else if (c1 != c2) {
        // Two different characters, we exit immediately with the correct order.
        return Character.compare(c1, c2);
      } else {
        i1++;
        i2++;
      }
    }

    // if all is equal, shorter is lesser
    return Integer.compare(len1, len2);
  }
}
