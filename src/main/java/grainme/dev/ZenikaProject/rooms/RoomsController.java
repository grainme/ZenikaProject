package grainme.dev.ZenikaProject.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for room-related endpoints.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    @Autowired
    private RoomsServices roomsServices;

    /**
     * Endpoint to retrieve all rooms.
     *
     * @return List of all rooms
     */
    @GetMapping
    public List<Room> getAllRooms() {
        return roomsServices.getListOfRooms();
    }
}
