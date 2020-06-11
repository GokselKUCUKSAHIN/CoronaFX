package com.jellybeanci;

import javafx.beans.property.*;
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
    private final IntegerProperty newCases;
    private final IntegerProperty totalCases;
    private final IntegerProperty newDeaths;
    private final IntegerProperty totalDeaths;
    private final DoubleProperty mortality;
    private final DoubleProperty attackRate;

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
        //
        newCases = new SimpleIntegerProperty(0);
        totalCases = new SimpleIntegerProperty(0);
        newDeaths = new SimpleIntegerProperty(0);
        totalDeaths = new SimpleIntegerProperty(0);
        mortality = new SimpleDoubleProperty(0);
        attackRate = new SimpleDoubleProperty(0);
    }

    public void insertRecord(Record record)
    {
        if(records.size() == 0)
        {
            this.newCases.set(record.getCases());
            this.newDeaths.set(record.getDeaths());
        }
        if (!this.records.containsKey(record.getDate()))
        {
            // Add to record list.
            this.records.putIfAbsent(record.getDate(), record);

            // And update values.
            updateCases(record.getCases());
            updateDeaths(record.getDeaths());
            calculatedValues();
        }
    }

    private void updateCases(int cases)
    {
        this.totalCases.set(getTotalCases() + cases);
    }

    private void updateDeaths(int deaths)
    {
        this.totalDeaths.set(getTotalDeaths() + deaths);
    }

    private void calculatedValues()
    {
        // Mortality
        if (getTotalCases() <= 0)
        {
            mortality.set(0);
        } else
        {
            mortality.set(getTotalDeaths() / (double) getTotalCases());
        }

        // Attack Rate
        attackRate.set(getTotalCases() / (double) getPopulation());
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

    public ObservableList<Record> getRecordList()
    {
        ObservableList<Record> observableList = FXCollections.observableArrayList();
        for (Record record : this.records.values())
        {
            observableList.add(record);
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

    public int getNewCases()
    {
        return newCases.get();
    }

    public int getTotalCases()
    {
        return totalCases.get();
    }

    public int getNewDeaths()
    {
        return newDeaths.get();
    }

    public int getTotalDeaths()
    {
        return totalDeaths.get();
    }

    public double getMortality()
    {
        return mortality.get();
    }

    public double getAttackRate()
    {
        return attackRate.get();
    }

    @Override
    public boolean equals(Object other)
    {
        return (this.getGeoId().equals(((Country) other).getGeoId()));
    }

    @Override
    public String toString()
    {
        return String.format("%s %d %d %d %d %d %f %f", getName(), getTotalCases(), getNewCases(), getTotalDeaths(), getNewDeaths(), getPopulation(), getMortality(), getAttackRate());
    }
}