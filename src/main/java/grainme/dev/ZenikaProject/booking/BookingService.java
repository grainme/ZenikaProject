package grainme.dev.ZenikaProject.booking;

import grainme.dev.ZenikaProject.rooms.Room;
import grainme.dev.ZenikaProject.rooms.RoomsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private RoomsServices roomsServices;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Retrieve all bookings.
     *
     * @return List of all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Find a booking by name.
     *
     * @param name Name of the booking
     * @return Booking object if found, null otherwise
     */
    public Booking findByName(String name) {
        return bookingRepository.findByName(name);
    }

    /**
     * Find available rooms between the specified start and end times.
     * @return List of available rooms
     */
    public List<Room> findAvailableRooms(Time startTime, Time endTime) {
        return roomsServices.findAvailableRooms(startTime, endTime);
    }

    /**
     * Find a room by considering all factors: meeting type, capacity, start time, and end time.
     * @return Room object that meets all criteria
     */
    public Room findByAllFactors(String meetingType, int capacity, LocalTime startTime, LocalTime endTime) {
        List<Room> meetingTypeRooms = roomsServices.findByMeetingType(meetingType);
        List<Room> capacityRooms = roomsServices.findByCapacity(capacity);
        List<Room> availableRooms = findAvailableRooms(Time.valueOf(startTime), Time.valueOf(endTime));

        Set<Room> intersection = new HashSet<>(meetingTypeRooms);
        intersection.retainAll(new HashSet<>(capacityRooms));
        intersection.retainAll(new HashSet<>(availableRooms));

        List<Room> roomsAvailable = new ArrayList<>(intersection);

        // Sorting rooms by capacity in ascending order to avoid wasting resources
        roomsAvailable.sort((Room a, Room b) -> a.getNumber() - b.getNumber());

        if (!roomsAvailable.isEmpty()) {
            Room roomResulted = roomsAvailable.get(0);
            // Keep the room not available for an additional hour due to COVID-19 constraints
            roomResulted.setLastTimeReserved(Time.valueOf(endTime.plusHours(1)));
            roomsServices.saveRoom(roomResulted);
            return roomResulted;
        }

        return null; // null if no suitable room is found
    }
}
