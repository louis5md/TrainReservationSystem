package com.louis.train.dto;

import com.louis.train.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private Status status;
}
