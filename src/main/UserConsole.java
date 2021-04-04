package main;
import java.util.Scanner;
import java.util.LinkedHashMap;

public class UserConsole {
  private Scanner scanner;
  private String username = "";
  private Database database;

  /**
   * UserConsole constructor.
   *
   * @param username - To uniquely identify spending record
   * @param database - A Database instance
   * Time estimate: O(1)
   */
  public UserConsole(String username, Database database) {
    this.scanner = new Scanner(System.in);
    this.username = username;
    this.database = database;
  }

  /**
   * Create and return all available commands with descriptions.
   *
   * @return a linked hash map, key is the command and value is the description of the command
   * Time estimate: O(1)
   */
  private static LinkedHashMap<String, String> getAllCommands() {
    LinkedHashMap<String, String> allCommands = new LinkedHashMap<String, String>();
    allCommands.put("SHOW", "Show total expenses.");
    allCommands.put("LIST", "List all spending records.");
    allCommands.put("DATE", "Show total expenses on a specific date. Usage: 'DATE YYYY MM DD'");
    allCommands.put("AVRG", "Show average spending in a specific month. Usage: 'AVRG YYYY MM'");
    allCommands.put("EXIT", "Logout and exit.");
    return allCommands;
  }

  /**
   * Ask user to enter user name for login.
   *
   * Time estimate: O(1)
   */
  private void login() {
    System.out.print("Please enter your username to login: ");
    try {
      username = scanner.nextLine();
    } catch (Exception e) {
      System.err.println("Invalid username input " + e.getMessage());
    }
  }

  /**
   * Execute a specific command with arguments.
   *
   * @param command - Can be one of the available commands from 'getAllCommands' method.
   * @param args - Additional arguments for some command, for example: 'DATE'.
   * Time estimate: O(N)
   */
  private void executeCommand(String command, String[] args) {
    int year, month, date;
    try {
      System.out.println("============================================================");
      switch (command) {
      case "SHOW":
        System.out.println("Total expenses = " + database.getTotalSpending(username));
        break;
      case "LIST":
        System.out.println("All spending records =");
        System.out.println(String.join("\n", database.findAll(username)));
        break;
      case "DATE":
        if (args.length < 4) {
          throw new Exception("Command 'DATE' should contains 3 arguments.");
        }
        year = Integer.parseInt(args[1]);
        month = Integer.parseInt(args[2]);
        date = Integer.parseInt(args[3]);
        System.out.format("Total expenses on date %d/%02d/%02d = %s\n",
            year, month, date, database.getTotalSpending(username, year, month, date));
        break;
      case "AVRG":
        if (args.length < 3) {
          throw new Exception("Command 'AVRG' should contains 2 arguments.");
        }
        year = Integer.parseInt(args[1]);
        month = Integer.parseInt(args[2]);
        System.out.format(
          "Average expense in month %d/%02d = %s\n",
          year, month, database.getAverageSpending(username, year, month));
        break;
      default:
      }
      System.out.println("============================================================\n");
    } catch (Exception e) {
      System.err.println("Failed to execute command: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Start a UserConsole
   *  1. Call 'login' method if user is not logged in.
   *  2. After user logged in,
   *      print all available commands and ask user to enter a command then execute.
   *
   * Time estimate: O(1)
   */
  public void start() throws Exception {
    while (username.equals("")) {
      login();
    }

    if (!database.isUserExist(username)) {
      throw new Exception("No records of user " + username);
    }

    System.out.println("");
    System.out.println("Welcome " + username + "!");

    String userCommand = "";
    do {
      LinkedHashMap<String, String> allCommands = UserConsole.getAllCommands();
      System.out.println("Please input a command:");
      allCommands.forEach((command, description) -> {
        System.out.println("- " + command + ": " + description);
      });
      try {
        String[] userCommands = scanner.nextLine().split("\\s+");
        userCommand = userCommands[0];
        if (allCommands.get(userCommand) == null) {
          throw new Exception("Command not found.");
        }
        executeCommand(userCommand, userCommands);
      } catch (Exception e) {
        System.err.println("Invalid command input: " + e.getMessage());
      }
    } while (!userCommand.equals("EXIT"));
    System.out.println("Goodbye!");
  }
  
  /**
   * Main function, start program here.
   *
   * @param args - arguments pass to main program
   * @throws Exception throw error when failed to load file
   * Time estimate O(1)
   */
  public static void main(String[] args) throws Exception {
    // Default: load file from "input.txt"
    String inputFile = "input.txt";
    String username = "";
    Database database = new Database();
    UserConsole userConsole = new UserConsole(username, database);
    database.loadFromFile(inputFile);
    userConsole.start();
  }
}
