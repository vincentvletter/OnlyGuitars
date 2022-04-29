package com.example.OnlyGuitars.repository;

import com.example.OnlyGuitars.model.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuitarRepository extends JpaRepository<Guitar, Long> {
    Guitar findByModel(String model);
}
