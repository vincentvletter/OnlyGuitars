package com.example.OnlyGuitars.repository;

import com.example.OnlyGuitars.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
