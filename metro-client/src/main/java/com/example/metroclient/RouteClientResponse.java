package com.example.metroclient;

import java.util.List;

public class RouteClientResponse {

    private List<StationClientDto> stations;
    private int totalMinutes;

    public RouteClientResponse() {
    }

    public List<StationClientDto> getStations() {
        return stations;
    }

    public void setStations(List<StationClientDto> stations) {
        this.stations = stations;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }
}
