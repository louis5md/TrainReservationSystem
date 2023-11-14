package com.louis.train.repository;

import com.louis.train.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface TrainRepository extends JpaRepository<Train,Long> {

    @Query(value = "SELECT * FROM t_train t WHERE t.src = ?1 and t.dest = ?2 and t.date = ?3",
            nativeQuery = true)
    List<Train> findAllByConditions(String src, String dest, Date date);
}
