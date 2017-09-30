package net.ghielmetti.utilities.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Color;

import javax.swing.JComponent;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link Interval} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class IntervalTest {
  private Interval<Integer> interval;
  private Interval<Integer> ownedInterval;
  private JComponent        owner;

  /** Tests {@link Interval#Interval()}. */
  @Test
  public void constructor_always_createsAnObject() {
    assertNotNull(new Interval<>());
  }

  /** Tests {@link Interval#Interval(Number, Number)}. */
  @Test
  public void constructor_withMinimumAndMaximum_createsAnObject() {
    assertNotNull(new Interval<>(Integer.valueOf(0), Integer.valueOf(100)));
  }

  /** Tests {@link Interval#getBackground()}. */
  @Test
  public void getBackground_always_returnsTheDefinedBackgroundColor() {
    interval.setBackground(Color.BLACK);
    assertEquals(Color.BLACK, interval.getBackground());
    interval.setBackground(Color.RED);
    assertEquals(Color.RED, interval.getBackground());
  }

  /** Tests {@link Interval#getBackgroundValueColor()}. */
  @Test
  public void getBackgroundValueColor_always_returnsTheDefinedBackgroundColor() {
    interval.setBackgroundValueColor(Color.BLACK);
    assertEquals(Color.BLACK, interval.getBackgroundValueColor());
    interval.setBackgroundValueColor(Color.RED);
    assertEquals(Color.RED, interval.getBackgroundValueColor());
  }

  /** Tests {@link Interval#getForeground()}. */
  @Test
  public void getForeground_always_returnsTheDefinedBackgroundColor() {
    interval.setForeground(Color.BLACK);
    assertEquals(Color.BLACK, interval.getForeground());
    interval.setForeground(Color.RED);
    assertEquals(Color.RED, interval.getForeground());
  }

  /** Tests {@link Interval#getForegroundValueColor()}. */
  @Test
  public void getForegroundValueColor_always_returnsTheDefinedBackgroundColor() {
    interval.setForegroundValueColor(Color.BLACK);
    assertEquals(Color.BLACK, interval.getForegroundValueColor());
    interval.setForegroundValueColor(Color.RED);
    assertEquals(Color.RED, interval.getForegroundValueColor());
  }

  /** Tests {@link Interval#getMaximum()}. */
  @Test
  public void getMaximum_always_returnsTheMaximumValue() {
    interval.setMaximum(Integer.valueOf(100));
    assertEquals(Integer.valueOf(100), interval.getMaximum());
    interval.setMaximum(Integer.valueOf(200));
    assertEquals(Integer.valueOf(200), interval.getMaximum());
  }

  /** Tests {@link Interval#getMinimum()}. */
  @Test
  public void getMinimum_always_returnsTheMinimumValue() {
    interval.setMinimum(Integer.valueOf(100));
    assertEquals(Integer.valueOf(100), interval.getMinimum());
    interval.setMinimum(Integer.valueOf(200));
    assertEquals(Integer.valueOf(200), interval.getMinimum());
  }

  /** Tests {@link Interval#getOwner()}. */
  @Test
  public void getOwner_forAnOwnedInterval_returnsTheOwner() {
    assertEquals(owner, ownedInterval.getOwner());
  }

  /** Tests {@link Interval#getOwner()}. */
  @Test
  public void getOwner_forAnUnownedInterval_returnsNull() {
    assertNull(interval.getOwner());
  }

  /** Tests {@link Interval#getValue()}. */
  @Test
  public void getValue_always_returnsTheValue() {
    interval.setValue(Integer.valueOf(100));
    assertEquals(Integer.valueOf(100), interval.getValue());
    interval.setValue(Integer.valueOf(200));
    assertEquals(Integer.valueOf(200), interval.getValue());
  }

  /** Tests {@link Interval#hasValidValue()}. */
  @Test
  public void hasValidValue_aCorrectValue_returnsTrue() {
    interval.setValue(Integer.valueOf(50));
    assertTrue(interval.hasValidValue());
  }

  /** Tests {@link Interval#hasValidValue()}. */
  @Test
  public void hasValidValue_aNullValue_returnsFalse() {
    assertFalse(new Interval<>().hasValidValue());
  }

  /** Tests {@link Interval#isVisible()}. */
  @Test
  public void isVisible_always_returnsTrueIfTheIntervalIsVisible() {
    interval.setVisible(true);
    assertTrue(interval.isVisible());
    interval.setVisible(false);
    assertFalse(interval.isVisible());
  }

  /** Tests {@link Interval#setBackground(Color)}. */
  @Test
  public void setBackground_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setBackground(Color.GREEN);
    verify(owner).invalidate();
  }

  /** Tests {@link Interval#setBackgroundValueColor(Color)}. */
  @Test
  public void setBackgroundValueColor_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setBackgroundValueColor(Color.GREEN);
    verify(owner).invalidate();
  }

  /** Tests {@link Interval#setForeground(Color)}. */
  @Test
  public void setForeground_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setForeground(Color.GREEN);
    verify(owner).invalidate();
  }

  /** Tests {@link Interval#setForegroundValueColor(Color)}. */
  @Test
  public void setForegroundValueColor_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setForegroundValueColor(Color.GREEN);
    verify(owner).invalidate();
  }

  /** Tests {@link Interval#setMaximum(Number)}. */
  @Test
  public void setMaximum_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setMaximum(Integer.valueOf(100));
    verify(owner).invalidate();
  }

  /** Tests {@link Interval#setMaximum(Number)}. */
  @Test
  public void setMaximum_valueBiggerThanTheValue_theValueIsUnchanged() {
    interval.setValue(Integer.valueOf(100));
    interval.setMaximum(Integer.valueOf(150));
    assertEquals(Integer.valueOf(100), interval.getValue());
  }

  /** Tests {@link Interval#setMaximum(Number)}. */
  @Test
  public void setMaximum_valueLesserThanTheMinimum_movesTheMinimum() {
    interval.setMaximum(Integer.valueOf(-500));
    assertEquals(Integer.valueOf(-500), interval.getMinimum());
  }

  /** Tests {@link Interval#setMaximum(Number)}. */
  @Test
  public void setMaximum_valueLesserThanTheValue_theValueIsSetToNull() {
    interval.setValue(Integer.valueOf(100));
    interval.setMaximum(Integer.valueOf(50));
    assertNull(interval.getValue());
  }

  /** Tests {@link Interval#setMinimum(Number)}. */
  @Test
  public void setMinimum_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setMinimum(Integer.valueOf(100));
    verify(owner).invalidate();
  }

  /** Tests {@link Interval#setMinimum(Number)}. */
  @Test
  public void setMinimum_valueBiggerThanTheMaximum_movesTheMaximum() {
    interval.setMinimum(Integer.valueOf(500));
    assertEquals(Integer.valueOf(500), interval.getMaximum());
  }

  /** Tests {@link Interval#setMinimum(Number)}. */
  @Test
  public void setMinimum_valueBiggerThanTheValue_theValueIsSetToNull() {
    interval.setValue(Integer.valueOf(100));
    interval.setMinimum(Integer.valueOf(150));
    assertNull(interval.getValue());
  }

  /** Tests {@link Interval#setMinimum(Number)}. */
  @Test
  public void setMinimum_valueLesserThanTheValue_theValueIsUnchanged() {
    interval.setValue(Integer.valueOf(100));
    interval.setMinimum(Integer.valueOf(50));
    assertEquals(Integer.valueOf(100), interval.getValue());
  }

  /** Initializes the tests. */
  @Before
  public void setUp() {
    interval = new Interval<>(Integer.valueOf(0), Integer.valueOf(200));
    ownedInterval = new Interval<>();
    owner = mock(JComponent.class);
    ownedInterval.setOwner(owner);
  }

  /** Tests {@link Interval#setValue(Number)}. */
  @Test
  public void setValue_anInvalidValue_setsTheValueToNull() {
    interval.setValue(Integer.valueOf(500));
    assertNull(interval.getValue());
  }

  /** Tests {@link Interval#setValue(Number)}. */
  @Test
  public void setValue_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setMinimum(Integer.valueOf(0));
    verify(owner, times(1)).invalidate();
    ownedInterval.setMaximum(Integer.valueOf(100));
    verify(owner, times(2)).invalidate();

    ownedInterval.setValue(Integer.valueOf(50));

    verify(owner, times(3)).invalidate();
  }

  /** Tests {@link Interval#setValue(Number)}. */
  @Test(expected = NullPointerException.class)
  public void setValue_notMinimumMaximumDefined_throwsANullPointerException() {
    new Interval<>().setValue(Integer.valueOf(100));
  }

  /** Tests {@link Interval#setVisible(boolean)}. */
  @Test
  public void setVisible_anOwnerIsDefined_invalidatesTheOwner() {
    ownedInterval.setVisible(true);
    verify(owner).invalidate();
  }
}
