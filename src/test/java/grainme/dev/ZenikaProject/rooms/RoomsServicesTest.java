package grainme.dev.ZenikaProject.rooms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class RoomsServicesTest {

    @Autowired
    private RoomsServices roomsServices;

    @Test
    void RoomsServices_FindByName_ReturnRoom(){
        // Arrange
        String roomName = "E1001";

        // Act
        Room room = roomsServices.findByName(roomName);

        // Assert
        Assertions.assertThat(room).isNotNull();
        Assertions.assertThat(room.getName()).isEqualTo("E1001");
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
}