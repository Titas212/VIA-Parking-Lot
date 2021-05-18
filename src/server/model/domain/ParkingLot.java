package server.model.domain;

import java.util.ArrayList;

public class ParkingLot
{
  private ArrayList<ParkingSpace> parkingSpaces;

  public ParkingLot()
  {
    this.parkingSpaces = new ArrayList<>();
    //Adding all the parking spaces to the parking lot
    for(int i = 0; i < 6; i++)
    {
      ParkingSpace aRow = new ParkingSpace("A" + i);
      parkingSpaces.add(aRow);
      if(i<5)
      {
        ParkingSpace bRow = new ParkingSpace("B" + i);
        ParkingSpace cRow = new ParkingSpace("C" + i);
        parkingSpaces.add(bRow);
        parkingSpaces.add(cRow);
      }
      ParkingSpace dRow = new ParkingSpace("D" + i);
      parkingSpaces.add(dRow);
    }
  }

  public ArrayList<ParkingSpace> getParkingSpaces()
  {
    return parkingSpaces;
  }

  //Get parking space by the name of it
  public ParkingSpace getParkingSpaceByName(String name)
  {
    for(int i = 0; i<parkingSpaces.size(); i++)
    {
      if(parkingSpaces.get(i).getNameOfParkingSpace().equals(name))
      {
        return parkingSpaces.get(i);
      }
    }
    return null;
  }

  public int size()
  {
    return parkingSpaces.size();
  }

  public ParkingSpace getParkingSpace(int index)
  {
    return parkingSpaces.get(index);
  }

  public boolean isOccupiedBySpaceName(String parkingSpaceName)
  {
    for(int i = 0; i< parkingSpaces.size(); i++)
    {
      if(parkingSpaces.get(i).getNameOfParkingSpace().equals(parkingSpaceName) && parkingSpaces.get(i).getIsOccupied())
      {
        return true;
      }
    }
    return false;
  }

  public boolean isOccupied(int index)
  {
    return parkingSpaces.get(index).getIsOccupied();
  }

  //Get all the not occupied parking spaces
  public ArrayList<ParkingSpace> notOccupiedParkingSpaces()
  {
    ArrayList<ParkingSpace> notOccupied = new ArrayList<>();
    for(int i = 0; i<parkingSpaces.size(); i++)
    {
      if(!(parkingSpaces.get(i).getIsOccupied()))
      {
        notOccupied.add(parkingSpaces.get(i));
      }
    }
    return notOccupied;
  }

  public ParkingSpace getParkingSpaceByUser(User user)
  {
    for(int i = 0; i< parkingSpaces.size(); i++)
    {
      if(parkingSpaces.get(i).getUser().equals(user))
      {
        return parkingSpaces.get(i);
      }
    }
    return null;
  }

  //TODO: 05/05/21  ADD A QUEUE ELEMENT OR DECIDE TO USE QUEUEADT INSTEAD
}
