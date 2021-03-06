package server.model.mediator;

import javafx.beans.property.StringProperty;
import org.postgresql.core.SqlCommand;
import server.model.domain.Date;
import server.model.domain.Time;
import server.model.domain.User;
import server.model.domain.Vehicle;

import java.sql.*;

/**
 * A class that represents systems communication with database.
 */
public class DatabaseManager implements ParkingDatabase
{
  private Connection connection;

  /**
   * Constructor for database.
   */
  public DatabaseManager()
  {
  }

  /**
   * A method that creates and adds user to database.
   * @param username users username.
   * @param password users password.
   * @return user.
   * @throws SQLException throwing sql exception.
   */
  public User addUserDB(String username, String password) throws SQLException {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO user_parking(username,password) VALUES(?,?);");
      statement.setString(1,username);
      statement.setString(2,password);
      statement.executeUpdate();
      return new User(username,password);
    }
  }

  /**
   * A method that exports user from database.
   * @param username users username.
   * @param password users password.
   * @return user
   * @throws SQLException throwing sql exception.
   */
  @Override public User getUserDB(String username, String password) throws SQLException
  {
    try(Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_parking WHERE username = ? AND password = ?;");
      statement.setString(1, username);
      statement.setString(2, password);

      ResultSet resultSet = statement.executeQuery();
      String password1 = null;
      String username1 = null;
      while(resultSet.next())
      {
        username1 = resultSet.getString("username");
        password1 = resultSet.getString("password");
      }
      System.out.println(username1 + " " + password1);
      return new User(username1, password1);
    }
  }

  /**
   * A method that exports users vehicle from database.
   * @param username users username
   * @return vehicle.
   * @throws SQLException throwing sql exception.
   */
  @Override public Vehicle getCarDB(String username) throws SQLException
  {
    try(Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM car_info WHERE username = ?;");
      statement.setString(1, username);

      ResultSet resultSet = statement.executeQuery();
      String username1 = null;
      String licenseNo = null;
      String carBrand = null;
      String color = null;
      while(resultSet.next())
      {
        username1 = resultSet.getString("username");
        licenseNo = resultSet.getString("license_no");
        carBrand = resultSet.getString("car_brand");
        color = resultSet.getString("color");
      }
      if(licenseNo != null)
      {
        return new Vehicle(licenseNo, color, carBrand);
      }
      return null;
    }
  }

  /**
   * A method that imports log to database.
   * @param username users username.
   * @param currentDate logs date.
   * @param currentTime logs time.
   * @param parkingSpace name of parking space.
   * @throws SQLException throwing sql exception.
   */
  @Override public void addLog(String username, Date currentDate, Time currentTime,
      String parkingSpace) throws SQLException
  {
    try(Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO logs(username,license_no, car_brand, color, date, time, parking_space) VALUES(?,?,?,?,?,?,?);");
      statement.setString(1, username);
      statement.setString(2, getCarDB(username).getLicenseNo());
      statement.setString(3, getCarDB(username).getCarBrand());
      statement.setString(4, getCarDB(username).getColor());
      statement.setString(5, currentDate.toString());

      statement.setString(6, currentTime.toString());
      statement.setString(7, parkingSpace);
      statement.executeUpdate();
    }
  }

  /**
   * A method that imports users first and last names into the database.
   * @param firstName users first name.
   * @param lastName users last name.
   * @param username users username.
   * @throws SQLException throwing sql exception.
   */
  public void addUserNamesDB(String firstName, String lastName, String username) throws SQLException {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE user_parking SET (f_name, l_name) = (?,?) WHERE username = ?;");
      statement.setString(3,username);
      statement.setString(1,firstName);
      statement.setString(2,lastName);
      statement.executeUpdate();


      ResultSet resultSet = statement.executeQuery();
      String f_name = null;
      String l_name = null;
      while(resultSet.next())
      {
        f_name = resultSet.getString("f_name");
        l_name = resultSet.getString("l_name");
      }
      System.out.println(f_name + " " + l_name);
    }
  }

  /**
   * A method that imports users vehicle to database.
   * @param username users username.
   * @param licenseNo users vehicle license number.
   * @param carBrand user vehicle brand.
   * @param color user vehicle color.
   * @throws SQLException throwing sql exception.
   */
  public void addCarDB(String username, String licenseNo, String carBrand, String color) throws SQLException {
    try(Connection connection = getConnection()) {
      if(getCarDB(username) == null)
      {
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO car_info (username, license_no, car_brand, color) VALUES (?,?,?,?)");
        statement.setString(1, username);
        statement.setString(2, licenseNo);
        statement.setString(3, carBrand);
        statement.setString(4, color);
        statement.executeUpdate();
      }

      else
      {
        PreparedStatement statement = connection.prepareStatement("UPDATE car_info SET (license_no, car_brand, color) = (?,?,?) WHERE username = ?;");
        statement.setString(4,username);
        if(licenseNo!=null)
        {
          statement.setString(1, licenseNo);
          statement.setString(2, color);
          statement.setString(3, carBrand);
        }
        statement.executeUpdate();
      }
    }
  }

  /**
   * A method that exports user from database.
   * @param username users username.
   * @return user.
   * @throws SQLException throwing sql exception.
   */
  @Override public User getUserDB(String username) throws SQLException
  {
    try(Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_parking WHERE username = ?;");
      statement.setString(1, username);

      ResultSet resultSet = statement.executeQuery();
      String username1 = null;
      String firstName = null;
      String lastName = null;
      while(resultSet.next())
      {
        username1 = resultSet.getString("username");
        if(!(resultSet.getString("f_name")==null))
        {
          firstName = resultSet.getString("f_name");
          lastName = resultSet.getString("l_name");
        }
      }
      System.out.println(username1);
      if(username1 != null)
      {
        User user = new User(username1);
        if(firstName != null)
        {
          user.setFirstname(firstName);
          user.setLastname(lastName);
        }
        return user;
      }

      return null;
    }
  }

  /**
   * A method that connects java application with postgresql database.
   * @return connection.
   * @throws SQLException throwing sql exception.
   */
  private Connection getConnection() throws SQLException{

    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=parking_lot", "postgres","1");
  }

}
