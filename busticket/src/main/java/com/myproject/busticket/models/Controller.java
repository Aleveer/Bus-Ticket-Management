package com.myproject.busticket.models;

import com.myproject.busticket.enums.ControllerStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "controller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Controller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "controller_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "enum('active', 'inactive') default 'active'")
    private ControllerStatus status;
}
