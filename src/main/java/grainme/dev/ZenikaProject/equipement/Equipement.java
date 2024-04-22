package grainme.dev.ZenikaProject.equipement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grainme.dev.ZenikaProject.rooms.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="equipements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;
}
