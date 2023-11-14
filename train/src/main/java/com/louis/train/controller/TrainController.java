package com.louis.train.controller;


import com.louis.train.dto.TrainBookRequest;
import com.louis.train.dto.TrainBookResponse;
import com.louis.train.dto.TrainRequest;
import com.louis.train.dto.TrainResponse;
import com.louis.train.model.Status;
import com.louis.train.model.Train;
import com.louis.train.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/train")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrainResponse> getTrains(@RequestBody TrainRequest trainRequest){
        return trainService.getTrains(trainRequest);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainBookResponse bookTrain(@RequestBody TrainBookRequest trainBookRequest){
        try{
            return trainService.bookTrain(trainBookRequest);
        }catch (IllegalArgumentException ex){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, ex.getMessage(), ex.getCause());
        }

    }

}
