package grainme.dev.ZenikaProject.booking;

import grainme.dev.ZenikaProject.rooms.Room;
import grainme.dev.ZenikaProject.rooms.RoomsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomsServices roomsServices;

    /**
     * Endpoint to find rooms by meeting type.
     * @return List of rooms matching the meeting type
     */
    @GetMapping("/type/{meetingType}")
    public List<Room> findByMeetingType(@PathVariable String meetingType) {
        return roomsServices.findByMeetingType(meetingType);
    }

    /**
     * Endpoint to find rooms by capacity.
     * @return List of rooms with the specified capacity
     */
    @GetMapping("/capacity/{nbrPeople}")
    public List<Room> findByCapacity(@PathVariable int nbrPeople) {
        return roomsServices.findByCapacity(nbrPeople);
    }

    /**
     * Endpoint to find a room by availability based on various factors.
     * @return ResponseEntity containing the room if found, otherwise not found response
     */
    @GetMapping
    public ResponseEntity<Room> findByAvailability(
            @RequestParam String type,
            @RequestParam int capacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

        Room room = bookingService.findByAllFactors(type, capacity, startTime, endTime);

        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            // Return not found response if no suitable room is found
            return ResponseEntity.notFound().build();
        }
    }
}
