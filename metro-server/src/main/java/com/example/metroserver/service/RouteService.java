package com.example.metroserver.service;

import com.example.metroserver.dto.RouteResponse;
import com.example.metroserver.dto.StationDto;
import com.example.metroserver.model.Station;
import com.example.metroserver.model.Transition;
import com.example.metroserver.repository.StationRepository;
import com.example.metroserver.repository.TransitionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {

    private final StationRepository stationRepository;
    private final TransitionRepository transitionRepository;

    public RouteService(StationRepository stationRepository,
                        TransitionRepository transitionRepository) {
        this.stationRepository = stationRepository;
        this.transitionRepository = transitionRepository;
    }

    public RouteResponse findRoute(Long fromId, Long toId) {
        Station from = stationRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid from station id"));
        Station to = stationRepository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid to station id"));

        List<Station> allStations = stationRepository.findAll();
        Map<Long, List<Transition>> graph = new HashMap<>();
        for (Transition t : transitionRepository.findAll()) {
            graph.computeIfAbsent(t.getFromStation().getId(), k -> new ArrayList<>()).add(t);
        }

        Map<Long, Integer> dist = new HashMap<>();
        Map<Long, Long> prev = new HashMap<>();
        Set<Long> visited = new HashSet<>();
        for (Station s : allStations) {
            dist.put(s.getId(), Integer.MAX_VALUE);
        }
        dist.put(from.getId(), 0);

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> (int) a[1]));
        pq.add(new long[]{from.getId(), 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long curId = cur[0];
            if (visited.contains(curId)) continue;
            visited.add(curId);
            if (curId == to.getId()) break;

            List<Transition> transitions = graph.getOrDefault(curId, Collections.emptyList());
            for (Transition t : transitions) {
                long nextId = t.getToStation().getId();
                int weight = t.getMinutes();
                int alt = dist.get(curId) + weight;
                if (alt < dist.getOrDefault(nextId, Integer.MAX_VALUE)) {
                    dist.put(nextId, alt);
                    prev.put(nextId, curId);
                    pq.add(new long[]{nextId, alt});
                }
            }
        }

        if (!prev.containsKey(to.getId()) && !Objects.equals(from.getId(), to.getId())) {
            return new RouteResponse(Collections.emptyList(), 0);
        }

        List<Station> pathStations = new ArrayList<>();
        Long curId = to.getId();
        pathStations.add(to);
        while (!Objects.equals(curId, from.getId())) {
            curId = prev.get(curId);
            if (curId == null) {
                break;
            }
            Station s = stationRepository.findById(curId).orElseThrow();
            pathStations.add(s);
        }
        Collections.reverse(pathStations);

        List<StationDto> dtoList = new ArrayList<>();
        for (Station s : pathStations) {
            dtoList.add(new StationDto(
                    s.getId(),
                    s.getName(),
                    s.getLine().getName(),
                    s.isTransfer()
            ));
        }

        int totalMinutes = dist.getOrDefault(to.getId(), 0);
        return new RouteResponse(dtoList, totalMinutes);
    }
}
