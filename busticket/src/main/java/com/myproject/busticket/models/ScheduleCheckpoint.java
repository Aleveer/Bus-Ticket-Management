package com.myproject.busticket.models;

import java.time.LocalDateTime;

import com.myproject.busticket.enums.ScheduleCheckpointStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule_checkpoints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCheckpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "checkpoint_id", nullable = false)
    private Checkpoint checkpoint;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "status", columnDefinition = "enum('arriving', 'at_checkpoint', 'departed') default 'arriving", nullable = false)
    private ScheduleCheckpointStatus status;
}