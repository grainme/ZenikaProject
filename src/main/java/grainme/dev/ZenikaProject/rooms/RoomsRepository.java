package grainme.dev.ZenikaProject.rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {

    /**
     * Find a room by its name.
     *
     * @param name Name of the room
     * @return Room object if found, null otherwise
     */
    Room findByName(String name);
}
