package com.esprit.wellnest.model;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (String s : list) {
            result.append(s).append(",");
        }
        return result.toString();
    }

    @TypeConverter
    public List<String> fromString(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(value.split(",")));
    }


    @TypeConverter
    public static String fromPlaceStatusList(List<Place.PlaceStatus> placeStatusList) {
        if (placeStatusList == null) return null;
        StringBuilder statuses = new StringBuilder();
        for (Place.PlaceStatus status : placeStatusList) {
            statuses.append(status.name()).append(",");
        }
        return statuses.toString();
    }
    @TypeConverter
    public static List<Place.PlaceStatus> toPlaceStatusList(String data) {
        if (data == null || data.isEmpty()) return new ArrayList<>();
        List<Place.PlaceStatus> placeStatusList = new ArrayList<>();
        String[] statuses = data.split(",");
        for (String status : statuses) {
            placeStatusList.add(Place.PlaceStatus.valueOf(status));
        }
        return placeStatusList;
    }
}
