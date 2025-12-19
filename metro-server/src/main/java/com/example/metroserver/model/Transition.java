package com.example.metroserver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transitions",
       uniqueConstraints = @UniqueConstraint(columnNames = {"from_station_id", "to_station_id"}))
public class Transition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_station_id")
    private Station fromStation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_station_id")
    private Station toStation;

    @Column(name = "minutes", nullable = false)
    private int minutes;

    public Transition() {
    }

    public Transition(Station fromStation, Station toStation, int minutes) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.minutes = minutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }

    public Station getToStation() {
        return toStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
