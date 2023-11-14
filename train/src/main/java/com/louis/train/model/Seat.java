package com.louis.train.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "t_seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "train_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Train trainId;

    private String seatNumber;

    @Column(columnDefinition = "ENUM('AVAILABLE', 'HOLD', 'BOOKED')")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "seatId")
    private Booking booking;
}

