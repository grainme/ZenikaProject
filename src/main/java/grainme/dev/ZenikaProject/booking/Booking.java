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
@Data
@Builder
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Time startTime;
    private Time endTime;
    private String meetingType;
    private int nbrPersonnes;
}
