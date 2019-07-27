package pl.adamnowicki.testing.parking;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ParkingTest {

  private static final int CAPACITY = 2;
  private static final Car CAR_1 = new Car("GD123");
  private static final Car CAR_2 = new Car("GD124");
  private static final Car CAR_3 = new Car("GD125");

  private Parking parking;
  private IncidentReporter incidentReporterMock;

  @Before
  public void init(){
    incidentReporterMock = Mockito.mock(IncidentReporter.class);
    parking = new Parking(CAPACITY, incidentReporterMock);
  }

  @Test
  public void shouldAllowToEnterIfParkingIsNotFull() {
    // when
    final boolean hasEntered = parking.enter(CAR_1);

    // then
    assertThat(hasEntered, is(true));
  }

  @Test
  public void shouldNotAllowToEnterIfParkingIsFull() {
    // given
    parking.enter(CAR_1);
    parking.enter(CAR_2);

    // when
    final boolean hasEntered = parking.enter(CAR_3);

    // then
    assertThat(hasEntered, is(false));
  }

  @Test
  public void shouldAllowToLeaveTheParking() {
    // given
    parking.enter(CAR_1);

    // when
    boolean isAllowedToLeave = parking.leave(CAR_1);

    // then
    assertThat(isAllowedToLeave, is(true));
  }

  @Test
  public void shouldCountPlacesLeft() {
    // when
    parking.enter(CAR_1);
    final int freePlacesCount = parking.getFreePlacesCount();

    // then
    assertThat(freePlacesCount, is(CAPACITY - 1));
  }

  @Test
  public void shouldDisplayLicensePlatesOfCarThatEntered() {
    // given
    parking.enter(CAR_1);

    // when
    Set<String> licensePlateRegistry = parking.getLicensePlateRegistry();

    // then
    assertThat(licensePlateRegistry, CoreMatchers.hasItem(CAR_1.getLicensePlate()));
  }

  @Test
  public void shouldNotAllowToLeaveUnrecognizedCar() {
    // given
    parking.enter(CAR_1);

    // when
    boolean isAllowedToLeave = parking.leave(CAR_2);

    // then
    assertThat(isAllowedToLeave, is(false));
  }

  @Test
  public void shouldReportUnrecognizedCar() {
    // when
    parking.leave(CAR_2);

    // then
    Mockito.verify(incidentReporterMock).reportIncident(CAR_2.getLicensePlate());
  }

}
