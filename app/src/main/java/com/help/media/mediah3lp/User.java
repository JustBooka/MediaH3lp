package com.help.media.mediah3lp;

import org.joda.time.DateTime;
/**
 * Created by alexey.bukin on 12.12.2014.
 */
public class User {

    private final String name;
    private final DateTime birthDate;
    private final String dateFormat;

    public User(String name, DateTime birthDate, String dateFormat) {
        this.name = name;
        this.birthDate = birthDate;
        this.dateFormat = dateFormat;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
