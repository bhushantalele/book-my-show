package models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel {

    @ManyToMany
    private Show show;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    private int price;

}
