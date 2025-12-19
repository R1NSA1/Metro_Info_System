package com.example.metroserver.dto;

public class StationDto {

    private Long id;
    private String name;
    private String lineName;
    private boolean transfer;

    public StationDto() {
    }

    public StationDto(Long id, String name, String lineName, boolean transfer) {
        this.id = id;
        this.name = name;
        this.lineName = lineName;
        this.transfer = transfer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }
}
