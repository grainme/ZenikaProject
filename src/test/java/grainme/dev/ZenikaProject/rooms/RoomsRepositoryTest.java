package grainme.dev.ZenikaProject.rooms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.sql.Time;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RoomsRepositoryTest {

    @Autowired
    private RoomsRepository roomsRepository;

    @Test
    void RoomsRepository_SaveRoom_ReturnRoomSaved(){

        // Arrange
        Room room = Room.builder()
                .name("E47")
                .capacity(100)
                .lastTimeReserved(Time.valueOf("09:00:00"))
                .build();
        // Act
        Room savedRoom = roomsRepository.save(room);

        // Assert
        Assertions.assertThat(savedRoom).isNotNull();
    }

    @Test
    void RoomsRepository_GetAll_ReturnRoomSaved(){

        // Arrange
        Room room = Room.builder()
                .name("E47")
                .capacity(100)
                .lastTimeReserved(Time.valueOf("09:00:00"))
                .build();
        Room room1 = Room.builder()
                .name("E47")
                .capacity(100)
                .lastTimeReserved(Time.valueOf("09:00:00"))
                .build();
        // Act
        Room savedRoom = roomsRepository.save(room);
        Room savedRoom1 = roomsRepository.save(room1);
        List<Room> listOfRooms = roomsRepository.findAll();

        // Assert
        Assertions.assertThat(listOfRooms).isNotNull();
        Assertions.assertThat(listOfRooms.size()).isEqualTo(2);
    }

    @Test
    void RoomsRepository_FindByName_ReturnRoomSaved(){

        // Arrange
        Room room = Room.builder()
                .name("E47")
                .capacity(100)
                .lastTimeReserved(Time.valueOf("09:00:00"))
                .build();
        // Act
        roomsRepository.save(room);
        Room savedRoom = roomsRepository.findByName("E47");
        // Assert
        Assertions.assertThat(savedRoom).isNotNull();
    }

    @Test
    void RoomsRepository_UpdateRoom_ReturnRoomSaved(){

        // Arrange
        Room room = Room.builder()
                .name("E47")
                .capacity(100)
                .lastTimeReserved(Time.valueOf("09:00:00"))
                .build();
        // Act
        Room savedRoom = roomsRepository.save(room);
        savedRoom.setName("E0047");
        roomsRepository.save(savedRoom);
        // Assert
        Assertions.assertThat(savedRoom).isNotNull();
        Assertions.assertThat(savedRoom.getName()).isEqualTo("E0047");
    }

}