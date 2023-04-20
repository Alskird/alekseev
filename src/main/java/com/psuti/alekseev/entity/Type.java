package com.psuti.alekseev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "types")
public class Type implements Serializable {
    @Id
    @Column(name = "name", nullable = false)
    private String name;
}

