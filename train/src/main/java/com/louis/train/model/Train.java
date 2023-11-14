package com.louis.train.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "t_train")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String src;
    private String dest;
    private Date date;
    private Time time;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainId", cascade = CascadeType.ALL)
    private List<Seat> seatList;
}
