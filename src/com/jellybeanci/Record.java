package com.jellybeanci;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class Record
{

    private final ObjectProperty<LocalDate> date;
    private final IntegerProperty cases;
    private final IntegerProperty deaths;

    public Record(int day, int month, int year, int cases, int deaths)
    {
        this.date = new SimpleObjectProperty<>(LocalDate.of(year, month, day));
        this.cases = new SimpleIntegerProperty(cases);
        this.deaths = new SimpleIntegerProperty(deaths);
    }

    public LocalDate getDate()
    {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty()
    {
        return date;
    }

    public int getCases()
    {
        return cases.get();
    }

    public IntegerProperty casesProperty()
    {
        return cases;
    }

    public int getDeaths()
    {
        return deaths.get();
    }

    public IntegerProperty deathsProperty()
    {
        return deaths;
    }
}