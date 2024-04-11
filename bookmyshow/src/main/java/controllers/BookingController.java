package controllers;

import dto.BookMovieRequestDto;
import dto.BookMovieResponseDto;
import models.Booking;
import models.ResponseStatus;
import org.springframework.stereotype.Controller;
import services.BookingService;

@Controller
public class BookingController {

    private BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }
    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto){
      BookMovieResponseDto response = new BookMovieResponseDto();
        try{
         Booking booking =  bookingService.bookMovie(bookMovieRequestDto.getUserId(),
                  bookMovieRequestDto.getShowId(),
                  bookMovieRequestDto.getShowSeatIds());

         response.setBookingId(booking.getId());
         response.setResponseStatus(ResponseStatus.SUCCESS);
         response.setAmount(booking.getAmount());
      } catch (RuntimeException runtimeException){
            response.setResponseStatus(ResponseStatus.FAILURE);
      }
        return response;
    }

    public Booking cancelMovie() {

        return null;
    }
}
