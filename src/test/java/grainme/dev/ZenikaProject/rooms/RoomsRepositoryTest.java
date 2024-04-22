package grainme.dev.ZenikaProject.rooms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.sql.Time;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomsRepositoryTest {

    @Autowired
    private RoomsRepository roomsRepository;

    @Test
    void RoomsRepository_SaveAll_ReturnRoomSaved(){

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
        Assertions.assertThat(savedRoom.id).isGreaterThan(0);
    }


}