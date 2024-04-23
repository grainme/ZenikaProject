package grainme.dev.ZenikaProject.booking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookingRepositoryTest {
    @Autowired
    BookingRepository bookingRepository;

    @Test
    void RoomsRepository_GetAll_ReturnRoomSaved(){

        // Arrange
        Booking booking = Booking.builder()
                .name("Reunion 47")
                .meetingType("VC")
                .nbrPersonnes(10)
                .build();

        Booking booking1 = Booking.builder()
                .name("Reunion 48")
                .meetingType("RS")
                .nbrPersonnes(30)
                .build();

        // Act
        Booking savedBooking = bookingRepository.save(booking);
        Booking savedBooking1 = bookingRepository.save(booking1);
        List<Booking> listOfBookings =bookingRepository.findAll();

        // Assert
        Assertions.assertThat(listOfBookings).isNotNull();
        Assertions.assertThat(listOfBookings.size()).isEqualTo(2);
    }

    @Test
    void RoomsRepository_FindByName_ReturnRoomSaved(){
        // Arrange
        Booking booking = Booking.builder()
                .name("Reunion 47")
                .meetingType("VC")
                .nbrPersonnes(10)
                .build();

        bookingRepository.save(booking);

        // Act
        Booking bookingFound = bookingRepository.findByName("Reunion 47");

        // Assert
        Assertions.assertThat(bookingFound).isNotNull();
    }

}
