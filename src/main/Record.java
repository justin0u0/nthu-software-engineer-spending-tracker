package main;

public class Record {
  String username;
  int year;
  int month;
  int date;
  int spending;
  
  /**
   * Record constructor.
   * Each Record record that a {username} spent {spending} on {year/month/date}
   * 
   * @param username - Specific that the record is created by which user
   * @param year - The record created year
   * @param month - The record created month
   * @param date - The record created date
   * @param spending - How much money spend
   * Time estimate: O(1)
   */
  Record(String username, int year, int month, int date, int spending) {
    this.username = username;
    this.year = year;
    this.month = month;
    this.date = date;
    this.spending = spending;
  }
  
  /**
   * Implement 'toString' method so that we can easily output a Record instance.
   * 
   * @return a string representing the record
   * Time estimate: O(1)
   */
  public String toString() {
    return String.format(
      "Record{username: %15s, year: %d, month: %2d, date: %2d, spending: %10d}",
      username, year, month, date, spending
    );
  }
}
