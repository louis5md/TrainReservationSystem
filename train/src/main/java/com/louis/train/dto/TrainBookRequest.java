package com.louis.train.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainBookRequest {
    private Long trainId;
    private Long seatId;
    private String passenger;
}
