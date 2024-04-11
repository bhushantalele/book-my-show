package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {

    @ManyToMany
    private List<ShowSeat> showSeats;  // If we cancel the ticket

    @ManyToOne
    private User user;

    private int amount;

    @OneToMany
    List<Payment> payments;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
}
