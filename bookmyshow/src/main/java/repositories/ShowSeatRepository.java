package repositories;

import models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    Optional<ShowSeat> findById(Long aLong);

    @Override
    ShowSeat save(ShowSeat showSeat); //Upsert -> Update + Insert.
    //if showSeat object is not there in DB, then insert it.
    //Else  update the showSeat object in DB.

}
