package com.example.to_do;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateValidator {


    public boolean checkDate(String date) {
        if (date == null || date.isEmpty()) {
            return true;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("cs_CZ"));
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
            return true;
        } catch (ParseException exc) {
            return false;
        }
    }

}
