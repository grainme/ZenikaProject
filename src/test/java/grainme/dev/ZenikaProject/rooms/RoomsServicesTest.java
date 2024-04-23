package grainme.dev.ZenikaProject.rooms;

import grainme.dev.ZenikaProject.booking.Booking;
import grainme.dev.ZenikaProject.booking.BookingRepository;
import grainme.dev.ZenikaProject.equipement.Equipement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class RoomsServicesTest {
    @Mock
    private RoomsRepository roomsRepository;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private RoomsServices roomsServices;


    private Room room;
    private Booking booking;

    @BeforeEach
    public void init(){
        room = Room.builder().name("E0047").capacity(47).build();
        booking = Booking.builder().name("Reunion 47").nbrPersonnes(4).meetingType("VC").build();
    }


    @Test
    void roomsServices_GetAllEquipments_ReturnListOfEquipmentNames() {
        // Arrange
        Equipement equipement = Equipement.builder().name("Ecran").build();
        equipement.setRoom(room);


        List<Equipement> roomEquipments = Optional.ofNullable(room.getEquipementList())
                .orElse(new ArrayList<>());

        roomEquipments.add(equipement);
        room.setEquipementList(roomEquipments);

        roomsServices.saveRoom(room);

        // Act
        List<String> listEquipments = roomsServices.getAllEquipments(room);

        // Assert
        Assertions.assertThat(listEquipments).isNotNull();
        Assertions.assertThat(listEquipments.size()).isGreaterThan(0);
    }
}