package com.example.metroserver.config;

import com.example.metroserver.model.Line;
import com.example.metroserver.model.Station;
import com.example.metroserver.model.Transition;
import com.example.metroserver.repository.LineRepository;
import com.example.metroserver.repository.StationRepository;
import com.example.metroserver.repository.TransitionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final TransitionRepository transitionRepository;

    public DataInitializer(LineRepository lineRepository,
                           StationRepository stationRepository,
                           TransitionRepository transitionRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.transitionRepository = transitionRepository;
    }

    @PostConstruct
    public void init() {
        if (stationRepository.count() > 0) {
            return;
        }

        Line red = lineRepository.save(new Line("Красная линия", "red"));
        Line blue = lineRepository.save(new Line("Синяя линия", "blue"));

        Station center = stationRepository.save(new Station("Центр", red, true, "Центральная станция"));
        Station river = stationRepository.save(new Station("Речной вокзал", red, false, "Станция у реки"));
        Station square = stationRepository.save(new Station("Площадь", blue, true, "Площадь города"));
        Station stadium = stationRepository.save(new Station("Стадион", blue, false, "Рядом со стадионом"));

        transitionRepository.save(new Transition(center, river, 5));
        transitionRepository.save(new Transition(river, center, 5));
        transitionRepository.save(new Transition(center, square, 4));
        transitionRepository.save(new Transition(square, center, 4));
        transitionRepository.save(new Transition(square, stadium, 6));
        transitionRepository.save(new Transition(stadium, square, 6));
    }
}
