package com.example.metroserver.repository;

import com.example.metroserver.model.Line;
import com.example.metroserver.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findByLine(Line line);
}
