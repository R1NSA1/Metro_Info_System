package com.example.metroserver.dto;

import java.util.List;

public class RouteResponse {

    private List<StationDto> stations;
    private int totalMinutes;

    public RouteResponse() {
    }

    public RouteResponse(List<StationDto> stations, int totalMinutes) {
        this.stations = stations;
        this.totalMinutes = totalMinutes;
    }

    public List<StationDto> getStations() {
        return stations;
    }

    public void setStations(List<StationDto> stations) {
        this.stations = stations;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }
}
