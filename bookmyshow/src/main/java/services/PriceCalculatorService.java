package services;

import models.Show;
import models.ShowSeat;
import models.ShowSeatType;
import org.springframework.stereotype.Service;
import repositories.ShowSeatTypeRepository;

import java.util.List;

@Service
public class PriceCalculatorService {
    private ShowSeatTypeRepository showSeatTypeRepository;

    public PriceCalculatorService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculateBookingPrice(List<ShowSeat> showSeats, Show show){
        int amount = 0;

        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show.getId());

        for (ShowSeat showSeat : showSeats){
            for(ShowSeatType showSeatType : showSeatTypes){
                if (showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                }
            }
        }

        // To Reduce the time complexity we can store the price for each ShowSeatType
        /*
                 double amount = 0.0;

                // Create a HashMap to store the price for each seat type
                HashMap<String, Double> seatTypePriceMap = new HashMap<>();
                for (ShowSeatType showSeatType : showSeatTypes) {
                    seatTypePriceMap.put(showSeatType.getSeatType(), showSeatType.getPrice());
                }

                // Iterate through the booked show seats
                for (ShowSeat showSeat : showSeats) {
                    String seatType = showSeat.getSeat().getSeatType();
                    // Check if the seat type exists in the price map
                    if (seatTypePriceMap.containsKey(seatType)) {
                        // If it exists, add the price to the total amount
                        amount += seatTypePriceMap.get(seatType);
                    }
                }
         */

        return amount;

    }

}
