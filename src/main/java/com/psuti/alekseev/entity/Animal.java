package com.psuti.alekseev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "animals")
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(nullable = false, length = 25,name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "type")
    private Type type;
}

