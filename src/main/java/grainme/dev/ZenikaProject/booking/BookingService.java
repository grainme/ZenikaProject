package grainme.dev.ZenikaProject.booking;

import grainme.dev.ZenikaProject.rooms.Room;
import grainme.dev.ZenikaProject.rooms.RoomsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private RoomsServices roomsServices;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Retrieve all bookings.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Find a booking by name.
     */
    public Booking findByName(String name) {
        return bookingRepository.findByName(name);
    }


    /**
     * Find a room by considering all factors: meeting type, capacity, start time, and end time.
     */
    public Room findByAllFactors(String meetingType, int capacity, Time startTime, Time endTime) {
        List<String> meetingTypeRooms = roomsServices.findByMeetingType(meetingType)
                                        .stream()
                                        .map(room -> room.getName())
                                        .collect(Collectors.toList());
        List<String> capacityRooms = roomsServices.findByCapacity(capacity)
                                    .stream()
                                    .map(room -> room.getName())
                                    .collect(Collectors.toList());
        List<String> availableRooms = roomsServices.findAvailableRooms(startTime, endTime)
                                    .stream()
                                    .map(room -> room.getName())
                                    .collect(Collectors.toList());

        // Filtering rooms based on the meeting type, capacity, and availability
        Set<String> meetingTypeSet = new HashSet<>(meetingTypeRooms);
        Set<String> capacitySet = new HashSet<>(capacityRooms);
        Set<String> availableSet = new HashSet<>(availableRooms);
        meetingTypeSet.retainAll(capacitySet);
        meetingTypeSet.retainAll(availableSet);

        HashSet IntersectionSet = new HashSet<>();
        for(String roomName : meetingTypeSet){
            Room room = roomsServices.findByName(roomName);
            IntersectionSet.add(room);
        }
        List<Room> roomsAvailable = new ArrayList<>(IntersectionSet);

        // Sorting rooms by capacity in ascending order
        roomsAvailable.sort(Comparator.comparingInt(Room::getCapacity));

        if (!roomsAvailable.isEmpty()) {
            Room roomResulted = roomsAvailable.get(0);
            // Keep the room not available for an additional hour due to COVID-19 constraints
            Time newTime = new Time(endTime.getTime() + TimeUnit.HOURS.toMillis(1));
            roomResulted.setLastTimeReserved(newTime);
            roomsServices.saveRoom(roomResulted);
            return roomResulted;
        }

        return null;
    }

}
