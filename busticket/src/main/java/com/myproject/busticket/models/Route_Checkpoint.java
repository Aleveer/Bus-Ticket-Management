package com.myproject.busticket.models;

import com.myproject.busticket.enums.CheckpointType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "route_checkpoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route_Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_code", referencedColumnName = "code", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "checkpoint_id", nullable = false)
    private Checkpoint checkpoint;

    @Column(name = "checkpoint_order", nullable = false, length = 15)
    private String checkpointOrder;

    @Column(name = "checkpoint_city", nullable = false, length = 100)
    private String checkpointCity;

    @Column(name = "checkpoint_province", nullable = false, length = 100)
    private String checkpointProvince;

    @Column(name = "type", nullable = false, columnDefinition = "ENUM('departure', 'drop_off', 'en_route', 'rest') DEFAULT 'departure'")
    private CheckpointType type;

}
