package com.esprit.wellnest.model;

public class Place {
    public enum PlaceStatus{
        DISPONIBEL,
        INDISPONIBLE,
        SELECTION,
        VIDE

    }
    private PlaceStatus placeStatus;
    private String name;

    public Place(PlaceStatus placeStatus, String name) {
        this.placeStatus = placeStatus;
        this.name = name;
    }

    public PlaceStatus getPlaceStatus() {
        return placeStatus;
    }

    public void setPlaceStatus(PlaceStatus placeStatus) {
        this.placeStatus = placeStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
