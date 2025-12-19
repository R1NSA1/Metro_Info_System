package com.example.metroclient;

public class StationClientDto {

    private Long id;
    private String name;
    private String lineName;
    private boolean transfer;

    public StationClientDto() {
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

    @Override
    public String toString() {
        return name + " (" + lineName + ")";
    }
}
