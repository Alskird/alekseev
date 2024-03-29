package com.psuti.alekseev.repository;

import com.psuti.alekseev.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
}
