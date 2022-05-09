package com.example.OnlyGuitars.repository;

import com.example.OnlyGuitars.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUsername(String username);
}
