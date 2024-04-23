package grainme.dev.ZenikaProject.equipement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grainme.dev.ZenikaProject.rooms.Room;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="equipements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
