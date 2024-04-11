package services;

import exception.ShowNotFoundException;
import exception.ShowSeatNotAvailableException;
import exception.UserNotFoundException;
import models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import repositories.BookingRepository;
import repositories.ShowRepository;
import repositories.ShowSeatRepository;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    private PriceCalculatorService priceCalculatorService;

    public BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, PriceCalculatorService priceCalculatorService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculatorService = priceCalculatorService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds){

        /*
            Steps :- (Reference : Approach#1 from notes.)
            -------------TAKE A LOCK----------------
            1. Get the user from userId.
            2. Get the show from showId.
            3. Get the list of showSeats from showSeatIds.
            4. Check if all the show seats are available.
            5. If all the show seats are not available then throw an exception.
            6. If all are available, then change the status to be LOCKED.
            7. Change the status in DB as well.
            8. Create the Booking Object, and store it in DB.
            9. Return the Booking Object.
            -----------RELEASE THE LOCK---------------
         */
        //1. Get the user from userId.
        Optional<User> optionalUser= userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("Invalid User");
        }
        User bookedBy = optionalUser.get();

        //2. Get the show from showId.
        Optional<Show> optionalShow= showRepository.findById(showId);
        if (optionalUser.isEmpty()){
            throw new ShowNotFoundException("Invalid ShowId");
        }
        Show show = optionalShow.get();

        //3. Get the list of showSeats from showSeatIds.

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        //4. Check if all the show seats are available.
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                //5. If all the show seats are not available then throw an exception.
                throw new ShowSeatNotAvailableException("ShowSeat with id: " + showSeat.getId() + "is not available");
            }

        }

        List<ShowSeat> bookedShowSeats = new ArrayList<>();
        //6. If all are available, then change the status to be LOCKED.
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
        //7. Change the status in DB as well.
            bookedShowSeats.add(showSeatRepository.save(showSeat));
        }

        //8. Create the Booking Object, and store it in DB.
        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking.setPayments(new ArrayList<>());
        booking.setShowSeats(bookedShowSeats);
        booking.setCreatedAt(new Date());
        booking.setLastModifiedAt(new Date());
        booking.setAmount(priceCalculatorService.calculateBookingPrice(bookedShowSeats, show));

        return bookingRepository.save(booking);

    }
}
