package main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseUnitTest {
  Database database = new Database();
  ArrayList<Record> mockRecords = new ArrayList<Record>();

  @BeforeEach
  void setUp() throws Exception {
    System.out.println(System.currentTimeMillis() + " - [BeforeEach]");
    mockRecords.add(new Record("0", 2020, 2, 1, 50));
    mockRecords.add(new Record("0", 2021, 1, 1, 1));
    mockRecords.add(new Record("0", 2021, 1, 1, 99));
    mockRecords.add(new Record("0", 2021, 1, 2, 200));
    mockRecords.add(new Record("0", 2021, 2, 1, 300));
    mockRecords.add(new Record("1", 2021, 1, 1, 150));
    mockRecords.add(new Record("1", 2021, 1, 31, 250));
    database.setRecords(mockRecords);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockRecords.clear();
    System.out.println(System.currentTimeMillis() + " - [AfterEach]");
  }

  /**
   * Test method: Database.loadFromFile(String inputFile)
   * Test data: test1.txt / test2.txt / test3.txt
   */
  @Test
  void testLoadFromFile() {
    // Clear all mock records first
    mockRecords.clear();
    database.setRecords(mockRecords);
    
    assertDoesNotThrow(() -> database.loadFromFile("test1.txt"));
    assertThrows(Exception.class, () -> database.loadFromFile("test2.txt"));
    assertThrows(Exception.class, () -> database.loadFromFile("test3.txt"));
  }

  /**
   * Test method: Database.getTotalSpending(String username)
   * Test data:
   *  - User "0":
   *    - 2020/02/01 50
   *    - 2021/01/01 1
   *    - 2021/01/01 99
   *    - 2021/01/02 200
   *    - 2021/02/01 300
   *  - User "1"
   *    - 2021/01/01 150
   *    - 2021/01/31 250
   */
  @Test
  void testGetTotalSpendingByUsername() {
    assertEquals(650l, database.getTotalSpending("0"));
    assertEquals(400l, database.getTotalSpending("1"));
    assertEquals(0l, database.getTotalSpending("2"));
  }
  
  /**
   * Test method: Database.getTotalSpending(String username, int year, int month)
   * Test data:
   *  - User "0":
   *    - 2020/02/01 50
   *    - 2021/01/01 1
   *    - 2021/01/01 99
   *    - 2021/01/02 200
   *    - 2021/02/01 300
   *  - User "1"
   *    - 2021/01/01 150
   *    - 2021/01/31 250
   */
  @Test
  void testGetTotalSpendingByUsernameYearAndMonth() {
    assertEquals(50l, database.getTotalSpending("0", 2020, 2));
    assertEquals(300l, database.getTotalSpending("0", 2021, 1));
    assertEquals(300l, database.getTotalSpending("0", 2021, 2));
    assertEquals(400l, database.getTotalSpending("1", 2021, 1));
    assertEquals(0l, database.getTotalSpending("1", 2021, 2));
  }
  
  /**
   * Test method: Database.getTotalSpending(String username, int year, int month, int date)
   * Test data:
   *  - User "0":
   *    - 2020/02/01 50
   *    - 2021/01/01 1
   *    - 2021/01/01 99
   *    - 2021/01/02 200
   *    - 2021/02/01 300
   *  - User "1"
   *    - 2021/01/01 150
   *    - 2021/01/31 250
   */
  @Test
  void testGetTotalSpendingByUsernameYearMonthAndDate() {
    assertEquals(100l, database.getTotalSpending("0", 2021, 1, 1));
    assertEquals(150l, database.getTotalSpending("1", 2021, 1, 1));
    assertEquals(0l, database.getTotalSpending("1", 2021, 1, 2));
  }

  /**
   * Test method: Database.getAverageSpending(String username, int year, int month)
   * Test data:
   *  - User "0":
   *    - 2020/02/01 50
   *    - 2021/01/01 1
   *    - 2021/01/01 99
   *    - 2021/01/02 200
   *    - 2021/02/01 300
   *  - User "1"
   *    - 2021/01/01 150
   *    - 2021/01/31 250
   */
  @Test
  void testGetAverageSpending() {
    assertEquals(1.0f * 50 / 29, database.getAverageSpending("0", 2020, 2));
    assertEquals(1.0f * 300 / 31, database.getAverageSpending("0", 2021, 1));
    assertEquals(1.0f * 300 / 28, database.getAverageSpending("0", 2021, 2));
    assertEquals(1.0f * 400 / 31, database.getAverageSpending("1", 2021, 1));
    assertEquals(0f, database.getAverageSpending("2", 2021, 1));
  }

  /**
   * Test method: Database.findAll()
   * Test data:
   *  - User "0":
   *    - 2020/02/01 50
   *    - 2021/01/01 1
   *    - 2021/01/01 99
   *    - 2021/01/02 200
   *    - 2021/02/01 300
   *  - User "1"
   *    - 2021/01/01 150
   *    - 2021/01/31 250
   */
  @Test
  void testFindAll() {
    String template = "Record{username: %15s, year: %d, month: %2d, date: %2d, spending: %10d}";
    ArrayList<String> user0 = new ArrayList<String>();
    user0.add(String.format(template, "0", 2020, 2, 1, 50));
    user0.add(String.format(template, "0", 2021, 1, 1, 1));
    user0.add(String.format(template, "0", 2021, 1, 1, 99));
    user0.add(String.format(template, "0", 2021, 1, 2, 200));
    user0.add(String.format(template, "0", 2021, 2, 1, 300));
    
    ArrayList<String> user1 = new ArrayList<String>();
    user1.add(String.format(template, "1", 2021, 1, 1, 150));
    user1.add(String.format(template, "1", 2021, 1, 31, 250));
    
    ArrayList<String> user2 = new ArrayList<String>();
    
    assertIterableEquals(user0, database.findAll("0"));
    assertIterableEquals(user1, database.findAll("1"));
    assertIterableEquals(user2, database.findAll("2"));
  }
}
