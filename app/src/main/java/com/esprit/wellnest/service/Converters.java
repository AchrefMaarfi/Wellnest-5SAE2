package com.esprit.wellnest.service;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converters {
//youssef
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromLong(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }


    //khalil
    @TypeConverter
    public static String fromList(List<String> list) {
        return list != null ? String.join(",", list) : null;
    }

    @TypeConverter
    public static List<String> toList(String data) {
        return data != null ? Arrays.asList(data.split(",")) : null;
    }




}

