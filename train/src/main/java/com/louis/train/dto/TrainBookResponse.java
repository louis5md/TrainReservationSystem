package com.louis.train.dto;

import com.louis.train.model.Seat;
import com.louis.train.model.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainBookResponse {
    private Long id;
    private String src;
    private String dest;
    private Date date;
    private Time time;
    private String seatNumber;
    private String passenger;
}
