package com.louis.train.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainResponse {
    private Long id;
    private String src;
    private String dest;
    private Date date;
    private Time time;
    private List<SeatResponse> listSeat;
}
