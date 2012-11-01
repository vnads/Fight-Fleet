using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet
{
  public enum ShipStatus
  {
    Live = 1,
    Sunk = 2
  }

  internal abstract class Ship
  {
    protected Ship()
    {
      NumberOfLives = Size;
    }

    public abstract string Name { get; }
    public abstract int Size { get; }
    public int NumberOfLives { get; set; }

    public ShipStatus Status
    {
      get { return Size <= 0 ? ShipStatus.Sunk : ShipStatus.Live; }
    }

    public ShipStatus Hit()
    {
      NumberOfLives--;
      return Status;
    }
  }

  internal class AircraftCarrier : Ship
  {
    public override string Name
    {
      get { return "AircraftCarrier"; }
    }

    public override int Size
    {
      get { return 5; }
    }
  }

  internal class BattleShip : Ship
  {
    public override string Name
    {
      get { return "BattleShip"; }
    }

    public override int Size
    {
      get { return 4; }
    }
  }

  internal class Submarine : Ship
  {
    public override string Name
    {
      get { return "Submarine"; }
    }

    public override int Size
    {
      get { return 3; }
    }
  }

  internal class Cruiser : Ship
  {
    public override string Name
    {
      get { return "Destroyer"; }
    }

    public override int Size
    {
      get { return 3; }
    }
  }

  internal class Destroyer : Ship
  {
    public override string Name
    {
      get { return "Destroyer"; }
    }

    public override int Size
    {
      get { return 2; }
    }
  }
}
