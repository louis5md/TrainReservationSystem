package com.louis.train.repository;

import com.louis.train.model.Seat;
import com.louis.train.model.Train;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Seat findByIdAndTrainId(Long id, Train trainId);
}
