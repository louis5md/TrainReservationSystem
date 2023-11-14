package com.louis.train.service;

import com.louis.train.dto.*;
import com.louis.train.model.Booking;
import com.louis.train.model.Seat;
import com.louis.train.model.Status;
import com.louis.train.model.Train;
import com.louis.train.repository.BookingRepository;
import com.louis.train.repository.SeatRepository;
import com.louis.train.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {
    private final TrainRepository trainRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;


    public List<TrainResponse> getTrains(TrainRequest trainRequest) {

        List<Train> trainList = trainRepository.findAllByConditions(trainRequest.getSrc(), trainRequest.getDest(), trainRequest.getDate());
        return trainList.stream().map(this::toTrainResponse).toList();
    }

    private TrainResponse toTrainResponse(Train train) {
        TrainResponse trainResponse = new TrainResponse();
        trainResponse.setId(train.getId());
        trainResponse.setSrc(train.getSrc());
        trainResponse.setDest(train.getDest());
        trainResponse.setDate(train.getDate());
        trainResponse.setTime(train.getTime());
        //Integer seatAvailable = Math.toIntExact(train.getSeatList().stream().filter(seat -> seat.getStatus().equals(Status.AVAILABLE)).count());
        List<SeatResponse> listSeatResponse = train.getSeatList().stream().map(this::toSeatResponse).toList();
        trainResponse.setListSeat(listSeatResponse);
        return trainResponse;
    }

    private SeatResponse toSeatResponse(Seat seat) {
        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setId(seat.getId());
        seatResponse.setSeatNumber(seat.getSeatNumber());
        seatResponse.setStatus(seat.getStatus());
        return seatResponse;
    }

    @Transactional
    public TrainBookResponse bookTrain(TrainBookRequest trainBookRequest) {
        Train train = trainRepository.findById(trainBookRequest.getTrainId())
                .orElseThrow(()-> new IllegalStateException(
                        "Train with " + trainBookRequest.getTrainId() + " does not exist"
                ));;
        Seat seat = seatRepository.findByIdAndTrainId(trainBookRequest.getSeatId(), train);

        if(seat == null || !seat.getStatus().equals(Status.AVAILABLE)){
            throw new IllegalArgumentException("Booking failed, seat not available");
        }
        seat.setStatus(Status.HOLD);
        Booking booking = new Booking();
        booking.setTrainId(train);
        booking.setSeatId(seat);
        booking.setPassenger(trainBookRequest.getPassenger());
        bookingRepository.save(booking);
        return toBookResponse(booking);
    }

    private TrainBookResponse toBookResponse(Booking booking) {
        return new TrainBookResponse(
                booking.getId(),
                booking.getTrainId().getSrc(),
                booking.getTrainId().getDest(),
                booking.getTrainId().getDate(),
                booking.getTrainId().getTime(),
                booking.getSeatId().getSeatNumber(),
                booking.getPassenger()
        );
    }
}
