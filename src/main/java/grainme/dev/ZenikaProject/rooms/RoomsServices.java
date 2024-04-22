package grainme.dev.ZenikaProject.rooms;

import grainme.dev.ZenikaProject.equipement.Equipement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


@Service
public class RoomsServices {

    // Constants
    private static final double COVID_FRACTION = 0.7; // Reduce capacity by 30% due to COVID restrictions
    private static final int MINIMUM_RS_PARTICIPANTS = 3;

    @Autowired
    private RoomsRepository roomsRepository;

    /**
     * Retrieve all rooms.
     *
     * @return List of all rooms
     */
    public List<Room> getListOfRooms() {
        return roomsRepository.findAll();
    }

    /**
     * Save a room.
     *
     * @param room Room object to save
     */
    public void saveRoom(Room room) {
        roomsRepository.save(room);
    }

    /**
     * Find a room by name.
     *
     * @param name Name of the room
     * @return Room object if found, null otherwise
     */
    public Room findByName(String name) {
        return roomsRepository.findByName(name);
    }

    /**
     * Get all equipment names for a given room.
     *
     * @param roomName Name of the room
     * @return List of equipment names
     */
    public List<String> getAllEquipments(String roomName) {
        Room room = findByName(roomName);
        List<String> equipmentNames = new ArrayList<>();

        for (Equipement equipment : room.getEquipementList()) {
            equipmentNames.add(equipment.getName());
        }

        return equipmentNames;
    }

    /**
     * Find rooms available between specified start and end times.
     *
     * @param startTime Start time
     * @param endTime   End time
     * @return List of available rooms
     */
    public List<Room> findAvailableRooms(@Param("startTime") Time startTime, @Param("endTime") Time endTime) {
        List<Room> availableRooms = new ArrayList<>();

        // Define the start and end times for room availability (8 AM to 8 PM)
        Time availabilityStartTime = Time.valueOf("07:00:00");
        Time availabilityEndTime = Time.valueOf("21:00:00");

        // Check if the provided start and end times are within the availability range
        if (startTime.after(availabilityStartTime) && endTime.before(availabilityEndTime)) {
            for (Room room : roomsRepository.findAll()) {
                if (room.getLastTimeReserved() == null || room.getLastTimeReserved().before(startTime)) {
                    availableRooms.add(room);
                }
            }
        }

        return availableRooms;
    }


    /**
     * Find rooms by meeting type.
     *
     * @param meetingType Meeting type
     * @return List of rooms matching the meeting type
     * @throws IllegalArgumentException if an invalid meeting type is provided
     */
    public List<Room> findByMeetingType(String meetingType) {
        List<Room> rooms = getListOfRooms();
        List<Room> filteredRooms = new ArrayList<>();

        for (Room room : rooms) {
            String roomName = room.getName();
            List<String> roomEquipments = getAllEquipments(roomName);

            switch (meetingType) {
                case "VC":
                    if (roomEquipments.contains("Pieuvre") && roomEquipments.contains("Ecran") &&
                            roomEquipments.contains("Webcam")) {
                        filteredRooms.add(room);
                    }
                    break;
                case "SPEC":
                    if (roomEquipments.contains("Tableau")) {
                        filteredRooms.add(room);
                    }
                    break;
                case "RC":
                    if (roomEquipments.contains("Tableau") && roomEquipments.contains("Ecran") &&
                            roomEquipments.contains("Pieuvre")) {
                        filteredRooms.add(room);
                    }
                    break;
                case "RS":
                    // RS: Reunion Simple
                    if (room.getNumber() * COVID_FRACTION >= MINIMUM_RS_PARTICIPANTS) {
                        filteredRooms.add(room);
                    }
                    break;
                default:
                    // Invalid meeting type
                    throw new IllegalArgumentException("Invalid meetingType: " + meetingType);
            }
        }

        return filteredRooms;
    }

    /**
     * Find rooms by capacity.
     *
     * @param nbrPeople Number of people
     * @return List of rooms with capacity greater than or equal to the specified number
     */
    public List<Room> findByCapacity(int nbrPeople) {
        List<Room> rooms = getListOfRooms();
        List<Room> filteredRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getNumber() * COVID_FRACTION >= nbrPeople) {
                filteredRooms.add(room);
            }
        }

        return filteredRooms;
    }
}
