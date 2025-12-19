package com.example.metroserver.repository;

import com.example.metroserver.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
}
