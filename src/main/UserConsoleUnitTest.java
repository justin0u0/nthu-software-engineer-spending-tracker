package main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserConsoleUnitTest {
  PrintStream stdout = System.out;
  InputStream stdin = System.in;
  
  Database database = new Database();
  ArrayList<Record> records = new ArrayList<Record>();

  @BeforeEach
  void setUp() throws Exception {
    System.out.println(System.currentTimeMillis() + " - [BeforeEach]");
    records.add(new Record("0", 2021, 1, 1, 31));
    database.setRecords(records);
  }

  @AfterEach
  void tearDown() throws Exception {
    System.setOut(stdout);
    System.setIn(stdin);
    records.clear();
    System.out.println(System.currentTimeMillis() + " - [AfterEach]");
  }

  /**
   * Test method: UserConsole.start()
   * Test data: Provide with username and login successfully
   */
  @Test
  void testStartLoginSuccessfully() throws Exception {
    String username = "0";
    String mockInput = "EXIT\n";
    String expectedOutput = "\n"
      + "Welcome 0!\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "============================================================\n"
      + "\n"
      + "Goodbye!\n";
    
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));
    System.setIn(new ByteArrayInputStream(mockInput.getBytes()));

    UserConsole userConsole = new UserConsole(username, database);
    try {
      userConsole.start();
      assertEquals(expectedOutput, outStream.toString());
    } catch (Exception e) {
      fail("Assert unreachable.");
    }
  }

  /**
   * Test method: UserConsole.start()
   * Test data: Provide with username and login unsuccessfully
   */
  @Test
  void testStartLoginUnsuccessfully() {
    String username = "1";
    String mockInput = "EXIT\n";

    System.setIn(new ByteArrayInputStream(mockInput.getBytes()));
    
    UserConsole userConsole = new UserConsole(username, database);
    assertThrows(Exception.class, () -> userConsole.start());
  }
}
