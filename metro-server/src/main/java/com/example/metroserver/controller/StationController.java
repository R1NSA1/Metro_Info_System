package com.example.metroserver.controller;

import com.example.metroserver.model.Station;
import com.example.metroserver.repository.StationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@CrossOrigin
public class StationController {

    private final StationRepository stationRepository;

    public StationController(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public List<Station> getAll() {
        return stationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> getOne(@PathVariable Long id) {
        return stationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Station create(@RequestBody Station station) {
        station.setId(null);
        return stationRepository.save(station);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Station> update(@PathVariable Long id,
                                          @RequestBody Station station) {
        if (!stationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        station.setId(id);
        return ResponseEntity.ok(stationRepository.save(station));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!stationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        stationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
