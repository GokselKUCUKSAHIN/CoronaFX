package com.jellybeanci;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.HashMap;

public class Country
{

    // 'Global' Whole Country List
    public static HashMap<String, Country> countries = new HashMap<>();
    // All record of 'this' Country
    private final HashMap<LocalDate, Record> records = new HashMap<>();
    private final StringProperty name;
    private final StringProperty geoId;
    private final StringProperty code;
    private final IntegerProperty population;
    private final StringProperty continent;
    //


    public Country(String name, String geoId, String code, int population, String continent)
    {
        this.name = new SimpleStringProperty(name);
        this.geoId = new SimpleStringProperty(geoId);
        this.code = new SimpleStringProperty(code);
        this.continent = new SimpleStringProperty(continent);
        if (population <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.population = new SimpleIntegerProperty(population);
    }

    public void insertRecord(Record record)
    {
        if (this.records.containsKey(record.getDate()))
        {
            records.putIfAbsent(record.getDate(), record);
        }
    }


    public static ObservableList<Country> getObservableList()
    {
        ObservableList<Country> observableList = FXCollections.observableArrayList();
        for (Country country : Country.countries.values())
        {
            observableList.add(country);
        }
        return observableList;
    }


    public String getName()
    {
        return name.get();
    }

    public String getGeoId()
    {
        return geoId.get();
    }

    public String getCode()
    {
        return code.get();
    }

    public int getPopulation()
    {
        return population.get();
    }

    public String getContinent()
    {
        return continent.get();
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getName(), getGeoId());
    }
}