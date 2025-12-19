package com.example.metroserver.repository;

import com.example.metroserver.model.Station;
import com.example.metroserver.model.Transition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransitionRepository extends JpaRepository<Transition, Long> {

    List<Transition> findByFromStation(Station fromStation);
}
