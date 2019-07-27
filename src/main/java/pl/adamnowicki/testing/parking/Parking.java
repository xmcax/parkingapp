package pl.adamnowicki.testing.parking;

import java.util.HashSet;
import java.util.Set;

public class Parking {

  private final int capacity;
  private final Set<String> licensePlateRegistry;
  private final IncidentReporter incidentReporter;

  public Parking(final int capacity, final IncidentReporter incidentReporter) {
    this.capacity = capacity;
    licensePlateRegistry = new HashSet<>();
    this.incidentReporter = incidentReporter;
  }

  public boolean enter(Car car) {
    if (getFreePlacesCount() <= 0) {
      return false;
    }

    licensePlateRegistry.add(car.getLicensePlate());
    return true;
  }

  public boolean leave(Car car) {
    final boolean isRecognized = licensePlateRegistry.remove(car.getLicensePlate());

    if (!isRecognized) {
      incidentReporter.reportIncident(car.getLicensePlate());
    }

    return isRecognized;
  }

  int getFreePlacesCount() {
    return capacity - licensePlateRegistry.size();
  }

  public Set<String> getLicensePlateRegistry() {
    return new HashSet<>(licensePlateRegistry);
  }
}
