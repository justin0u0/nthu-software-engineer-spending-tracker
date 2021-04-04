package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecordUnitTest {
  @BeforeEach
  void setUp() {
    System.out.println(System.currentTimeMillis() + " - [BeforeEach]");
  }

  @AfterEach
  void tearDown() {
    System.out.println(System.currentTimeMillis() + " - [AfterEach]");
  }

  /**
   * Test method: Record.toString()
   * Test data:
   *  - r1{"0", 2021, 1, 1, 100}
   *  - r2{"1234", 2021, 12, 31, 10000}
   */
  @Test
  void testToString() {
    Record r1 = new Record("0", 2021, 1, 1, 100);
    Record r2 = new Record("1234", 2021, 12, 31, 10000);
    assertEquals(r1.toString(),
      "Record{username:               0, year: 2021, month:  1, date:  1, spending:        100}");
    assertEquals(r2.toString(),
      "Record{username:            1234, year: 2021, month: 12, date: 31, spending:      10000}");
  }
}
