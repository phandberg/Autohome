package com.example.demo.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class dateConverter {
    int daysbetween;

    public LocalDate dateConverter(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public int daysbetween(LocalDate date1, LocalDate date2){
        ChronoUnit.DAYS.between(date1, date2);
        return daysbetween;
    }

}
