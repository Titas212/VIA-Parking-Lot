package server.model.mediator;

import server.model.domain.*;

import java.sql.SQLException;

public class ModelManager implements Model
{
  private ParkingLot parkingLot;
  private DatabaseManager databaseManager;
  private Login login;
  private ParkingDatabase parkingDatabase;

  public ModelManager()
  {
    parkingLot = new ParkingLot();
    login = new Login();
    parkingDatabase = new DatabaseManager();
  }

  @Override public void registerSpace(String userName, ParkingSpace parkingSpace, Time startingTime, Time endingTime, Date date)
  {
    for(int i = 0; i < parkingLot.size(); i++)
    {
      if(parkingLot.getParkingSpace(i).equals(parkingSpace) && !(parkingSpace.getIsOccupied()))
      {
        parkingLot.getParkingSpace(i).setOccupied(true, userName,startingTime,endingTime,date);
      }
    }
  }

  @Override
  public void register(String userName, String password) throws SQLException {
    login.register(userName,password);
    parkingDatabase.addUserDB(userName,password);
  }

  @Override
  public boolean login(String userName, String password) throws SQLException
  {
    //System.out.println(parkingDatabase.getUserDB(userName,password));
    User dummy = new User(userName,password);
    System.out.println(parkingDatabase.getUserDB(userName, password).getPassword());
    if(parkingDatabase.getUserDB(userName, password).getUsername().equals(dummy.getUsername()) &&
        parkingDatabase.getUserDB(userName, password).getPassword().equals(dummy.getPassword()))
    {
      return true;
    }
    return false;
  }

  @Override
  public void registerFirstAndLastName(String firstName, String lastName, String username)
      throws SQLException
  {
    parkingDatabase.addUserNamesDB(firstName, lastName, username);
  }

  @Override
  public User getUserByUserName(String userName) throws SQLException
  {
    return parkingDatabase.getUserDB(userName);
  }

  @Override public void registerVehicle(String username, String licenseNo, String color,
                                        String carBrand)
  {
    try
    {
      parkingDatabase.addCarDB(username, licenseNo, carBrand, color);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public ParkingLot getParkingLot() {
    return parkingLot;
  }
}
