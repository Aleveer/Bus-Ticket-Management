package com.myproject.busticket.models;

import com.myproject.busticket.enums.ControllerStatus;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "status", columnDefinition = "enum('active', 'inactive') default 'active'")
    private ControllerStatus status;
}
