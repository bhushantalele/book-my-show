package dto;


import lombok.Getter;
import lombok.Setter;
import models.ResponseStatus;

import java.util.List;

@Getter
@Setter

public class BookMovieResponseDto {
    private Long bookingId;

    private double amount;

    private ResponseStatus responseStatus;

}
