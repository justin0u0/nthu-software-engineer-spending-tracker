package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Database {
  private ArrayList<Record> records = new ArrayList<Record>();

  /**
   * Default Database constructor
   *
   * Time estimate: O(1)
   */
  Database() {}

  // Getters/Setters only for testing
  /**
   * Set value for 'records'
   *
   * @param newRecords - new 'records' value
   * Time estimate: O(1)
   */
  public void setRecords(ArrayList<Record> newRecords) {
    records = newRecords;
  }
  /**
   * Get value for 'records'
   *
   * @return value of variable 'records'
   * Time estimate: O(1)
   */
  public ArrayList<Record> getRecords() {
    return records;
  }
 

  /**
   * Get total dates in specific year and month.
   *
   * @param year - the given year
   * @param month - the given month
   * @return total dates in the given year and month
   * Time estimate: O(1)
   */
  private int getTotalDates(int year, int month) {
    boolean isLeapYear = ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
    if (month == 4 || month == 6 || month == 9 || month == 11)
      return 30;
    else if (month == 2 && !isLeapYear)
      return 28;
    else if (month == 2 && isLeapYear)
      return 29;
    return 31;
  }

  /**
   * 
   * @param username - the user of this record
   * @param datestamp - the created year, month and date of this record, the format should be 'yyyymmdd'
   * @param spending - the spending of this record
   * @throws Exception
   *  Throw exception if:
   *  1. 'datestamp' is not in format 'yyyymmdd'
   *  2. 'spending' is not a positive number
   * Time estimate: O(1)
   */
  private void addRecord(String username, String datestamp, int spending) throws Exception {
    if (datestamp.length() != 8) {
      throw new Exception("Invalid record format, datestamp must be 'yyyymmdd'.");
    }
    if (spending <= 0) {
      throw new Exception("Invalid record format, spending must greater than 0.");
    }
    int year = Integer.parseInt(datestamp.substring(0, 4));
    int month = Integer.parseInt(datestamp.substring(4, 6));
    int date = Integer.parseInt(datestamp.substring(6, 8));
    if (month < 1 || month > 12) {
      throw new Exception("Invalid record format, month must between 1 and 12.");
    }
    if (date < 1 || date > getTotalDates(year, month)) {
      throw new Exception("Invalid record format, month " + month
        + " date must between 1 and " + getTotalDates(year, month));
    }
    Record newRecord = new Record(username, year, month, date, spending);
    records.add(newRecord);
  }

  /**
   * Load records from a file.
   *
   * @param inputFile - the input file absolute path
   * @throws Exception - throw error when failed to load file
   * Time estimate: O(N)
   */
  public void loadFromFile(String inputFile) throws Exception {
    try {
      File file = new File(inputFile);
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String[] record = scanner.nextLine().split("\\s+");
        if (record.length != 3) {
          scanner.close();
          throw new Exception("Invalid record format, record must contains username, datestamp and spending.");
        }
        addRecord(record[0], record[1], Integer.parseInt(record[2]));
      }
      scanner.close();
    } catch (Exception e) {
      System.err.println("Failed to load from file: " + e.getMessage());
      throw e;
    }
  }

  /**
   * Check if a given username exist in all records
   *
   * @param username - the given username
   * @return true if username exist and otherwise false
   * Time estimate: O(N)
   */
  public boolean isUserExist(String username) {
    for (Record record : records) {
      if (username.equals(record.username))
        return true;
    }
    return false;
  }

  /**
   * Get total spending of a specific user
   *
   * @param username - the username to filter all records
   * @return total spending of a specific user
   * Time estimate: O(N)
   */
  public long getTotalSpending(String username) {
    long totalSpendings = 0;
    for (Record record : records) {
      if (username.equals(record.username)) {
        totalSpendings += record.spending;
      }
    }
    return totalSpendings;
  }

  /**
   * Get total spending of a specific user in the given year and month
   *
   * @param username - the username to filter all records
   * @param year - the given year to filter all records
   * @param month - the given month to filter all records
   * @return total spending of a specific user in the given year and month
   * Time estimate: O(N)
   */
  public long getTotalSpending(String username, int year, int month) {
    long totalSpendings = 0;
    for (Record record : records) {
      if (username.equals(record.username) && record.year == year && record.month == month) {
        totalSpendings += record.spending;
      }
    }
    return totalSpendings;
  }

  /**
   * Get total spending of a specific user on the given year, month and date
   *
   * @param username - the username to filter all records
   * @param year - the given year to filter all records
   * @param month - the given month to filter all records
   * @param date - the given date to filter all records
   * @return total spending of a specific user in the given year, month and date
   * Time estimate: O(N)
   */
  public long getTotalSpending(String username, int year, int month, int date) {
    long totalSpendings = 0;
    for (Record record : records) {
      if (username.equals(record.username)
        && record.year == year
        && record.month == month
        && record.date == date) {
        totalSpendings += record.spending;
      }
    }
    return totalSpendings;
  }

  /**
   * Get average spending of a specific user in the given year and month
   *
   * @param username - the username to filter all records
   * @param year - the given year to filter all records
   * @param month - the given month to filter all records
   * @return average spending of a specific user in the given year and month
   * Time estimate: O(N)
   */
  public float getAverageSpending(String username, int year, int month) {
    long totalSpendings = getTotalSpending(username, year, month);
    int totalDates = getTotalDates(year, month);
    return 1.0f * totalSpendings / totalDates;
  }

  /**
   * Find all records with a specific user
   *
   * @param username - the username to filter all records
   * @return an ArrayList contains all records of the specific user
   * Time estimate: O(N)
   */
  public ArrayList<String> findAll(String username) {
    ArrayList<String> allRecords = new ArrayList<String>();
    for (Record record : records) {
      if (username.equals(record.username)) {
        allRecords.add(record.toString());
      }
    }
    return allRecords;
  }
}
