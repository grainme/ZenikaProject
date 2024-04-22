package grainme.dev.ZenikaProject.booking;

import grainme.dev.ZenikaProject.rooms.Room;
import grainme.dev.ZenikaProject.rooms.RoomsServices;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingServiceTest {

    @Autowired
    BookingService bookingService;
    @Autowired
    RoomsServices roomsServices;

    @Test
    void BookingServices_getAllBookings_ReturnListOfRooms(){
        // Arrange

        // Act
        List<Booking> listOfBookings = bookingService.getAllBookings();
        final int EXACT_NUMBER_OF_BOOKINGS = 18;

        // Assert
        Assertions.assertThat(listOfBookings).isNotNull();
        Assertions.assertThat(listOfBookings.size()).isEqualTo(EXACT_NUMBER_OF_BOOKINGS);
    }

    @Test
    public void findByAllFactors_shouldReturnRoom_whenRoomsAreAvailable() {
        // Arrange
        String meetingType = "VC";
        int capacity = 5;
        Time startTime = Time.valueOf("09:00:00");
        Time endTime = Time.valueOf("10:00:00");

        // Assuming you have rooms in your database that match the criteria
        // Add rooms to your database that match the criteria
        Room room1 = roomsServices.findByName("E3001");

        // Act
        Room result = bookingService.findByAllFactors(meetingType, capacity, startTime, endTime);

        // Assert
        Assertions.assertThat(room1).isNotNull();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo("E3001");
        Assertions.assertThat(result.getCapacity()).isEqualTo(13);
    }


}
