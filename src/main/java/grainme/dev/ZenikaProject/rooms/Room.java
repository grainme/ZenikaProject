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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int number;
    Time lastTimeReserved;
    String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Equipement> equipementList;
}
