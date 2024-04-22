package grainme.dev.ZenikaProject.rooms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.List;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class RoomsServicesTest {

    @Autowired
    private RoomsServices roomsServices;

    @Test
    void RoomsServices_FindByName_ReturnRoom(){
        // Arrange
        String roomName = "E3001";

        // Act
        Room room = roomsServices.findByName(roomName);

        // Assert
        Assertions.assertThat(room).isNotNull();
        Assertions.assertThat(room.getName()).isEqualTo("E3001");
        Assertions.assertThat(room.getCapacity()).isEqualTo(13);
    }

    @Test
    void RoomsServices_FindAll_ReturnListOfEquipments(){

        // Arrange
        Room room = roomsServices.getListOfRooms().get(0);

        // Act
        List<String> listOfEquipments = roomsServices.getAllEquipments(room.getName());

        // Assert
        Assertions.assertThat(listOfEquipments).isNotNull();
        Assertions.assertThat(listOfEquipments.size()).isGreaterThan(0);

    }

    @Test
    void RoomsServices_FindByMeetingType_ReturnListOfRooms(){

        // Arrange
        String meetingType = "VC";

        // Act
        List<Room> listOfRoomsByMeetingType = roomsServices.findByMeetingType(meetingType);

        // Assert
        Assertions.assertThat(listOfRoomsByMeetingType).isNotNull();
        // should have 3 items inside : Ecran, Pieuvre, Webcam
        Assertions.assertThat(listOfRoomsByMeetingType.get(0).getEquipementList().size()).isEqualTo(3);
    }

    @Test
    void RoomsServices_FindByCapacity_ReturnListOfRooms(){

        // Arrange
        int numberOfParticipants = 13;

        // Act
        List<Room> listOfRoomsByCapacity = roomsServices.findByCapacity(numberOfParticipants);

        // Assert
        Assertions.assertThat(listOfRoomsByCapacity).isNotNull();
        Assertions.assertThat(listOfRoomsByCapacity.size()).isGreaterThan(0);
    }

    @Test
    void RoomsServices_FindByTimeAvailability_ReturnListOfRooms(){

        // Arrange
        Time startTime = Time.valueOf("09:00:00");
        Time finishTime = Time.valueOf("10:00:00");
        final int TOTAL_NUMBER_ROOMS = 12;

        // Act
        List<Room> listOfRoomsByCapacity = roomsServices.findAvailableRooms(startTime, finishTime);

        // Assert
        Assertions.assertThat(listOfRoomsByCapacity).isNotNull();
        Assertions.assertThat(listOfRoomsByCapacity.size()).isGreaterThan(0);
        Assertions.assertThat(listOfRoomsByCapacity.size()).isEqualTo(TOTAL_NUMBER_ROOMS);
    }
}