package com.louis.train.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class TrainRequest {
    private String src;
    private String dest;
    private Date date;
}
