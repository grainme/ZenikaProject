package grainme.dev.ZenikaProject.booking;

import grainme.dev.ZenikaProject.equipement.Equipement;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    Time startTime;
    Time endTime;
    String meetingType;
    int nbrPersonnes;
}
