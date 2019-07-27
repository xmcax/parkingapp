package pl.adamnowicki.testing.parking;

public class Car {
  private final String licensePlate;

  public Car(final String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public String getLicensePlate() {
    return licensePlate;
  }
}
