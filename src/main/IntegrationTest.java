package main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationTest {
  Database database = null;
  
  PrintStream stdout = System.out;
  PrintStream stderr = System.err;
  InputStream stdin = System.in;

  @BeforeEach
  void setUp() throws Exception {
    System.out.println(System.currentTimeMillis() + " - [BeforeEach]");
    database = new Database();
    database.loadFromFile("test1.txt");
  }

  @AfterEach
  void tearDown() throws Exception {
    System.setOut(stdout);
    System.setErr(stderr);
    System.setIn(stdin);
    database = null;
    System.out.println(System.currentTimeMillis() + " - [AfterEach]");
  }

  /**
   * 1. Load file from test1.txt file,
   * 2. Login to user 10000001,
   * 3. Execute commands SHOW, LIST, DATE, AVRG then EXIT
   * Should get correct results.
   */
  @Test
  void testPassAllCommands() {
    String mockInput = "10000001\nSHOW\nLIST\nDATE 1998 02 28\nAVRG 1998 02\nEXIT\n";
    String expectedOutput = "Please enter your username to login: \n"
      + "Welcome 10000001!\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "Total expenses = 27264\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "All spending records =\n"
      + "Record{username:        10000001, year: 1996, month: 10, date:  1, spending:        239}\n"
      + "Record{username:        10000001, year: 1998, month:  2, date: 28, spending:          1}\n"
      + "Record{username:        10000001, year: 2000, month:  1, date: 23, spending:       1024}\n"
      + "Record{username:        10000001, year: 2004, month:  2, date: 29, spending:       4000}\n"
      + "Record{username:        10000001, year: 1998, month:  2, date: 28, spending:      22000}\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "Total expenses on date 1998/02/28 = 22001\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "Average expense in month 1998/02 = 785.75\n"
      + "============================================================\n"
      + "\n"
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
    
    UserConsole userConsole = new UserConsole("", database);
    try {
      userConsole.start();
      assertEquals(expectedOutput, outStream.toString());
    } catch (Exception e) {
      fail("Assert unreachable.");
    }
  }
  
  /**
   * 1. Load file from test1.txt file,
   * 2. Login to user 10000001,
   * 3. Execute commands SHOW, LIST, DATE, AVRG, WHAT then EXIT
   * Should get correct results from SHOW, LIST, DATE and AVRG commands,
   * and get error messages from WHAT command.
   */
  @Test
  void testFailOneCommand() {
    String mockInput = "17111563\nSHOW\nLIST\nDATE 2021 01 23\nAVRG 2021 01\nWHAT\nEXIT\n";
    String expectedOutput = "Please enter your username to login: \n"
      + "Welcome 17111563!\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "Total expenses = 1002995\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "All spending records =\n"
      + "Record{username:        17111563, year: 2021, month:  1, date: 24, spending:         87}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date:  2, spending:        215}\n"
      + "Record{username:        17111563, year: 2021, month: 10, date:  2, spending:    1000000}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date:  2, spending:        123}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date: 23, spending:        449}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date:  1, spending:         20}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date: 30, spending:        999}\n"
      + "Record{username:        17111563, year: 2021, month: 11, date:  2, spending:        102}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date:  4, spending:        875}\n"
      + "Record{username:        17111563, year: 2021, month:  1, date: 24, spending:        125}\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "Total expenses on date 2021/01/23 = 449\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
      + "============================================================\n"
      + "Average expense in month 2021/01 = 93.32258\n"
      + "============================================================\n"
      + "\n"
      + "Please input a command:\n"
      + "- SHOW: Show total expenses.\n"
      + "- LIST: List all spending records.\n"
      + "- DATE: Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'\n"
      + "- AVRG: Show average spending in a specific month. Usage: 'AVRG YYYY MM'\n"
      + "- EXIT: Logout and exit.\n"
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
    String expectedError = "Invalid command input: Command not found.\n";
    
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    System.setIn(new ByteArrayInputStream(mockInput.getBytes()));
    
    UserConsole userConsole = new UserConsole("", database);
    try {
      userConsole.start();
      assertEquals(expectedOutput, outStream.toString());
      assertEquals(expectedError, errStream.toString());
    } catch (Exception e) {
      fail("Assert unreachable.");
    }
  }
}
