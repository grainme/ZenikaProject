package grainme.dev.ZenikaProject.rooms;

import grainme.dev.ZenikaProject.equipement.Equipement;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="rooms")
@Builder
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int capacity;
    private Time lastTimeReserved;
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Equipement> equipementList;

}
