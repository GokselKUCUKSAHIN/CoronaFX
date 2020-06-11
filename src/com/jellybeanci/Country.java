package com.jellybeanci;

import java.util.HashMap;

public class Country
{
    public static HashMap<String, Country> countries = new HashMap<>();
    private String name;
    private String geoId;
    private String code;
    private int population;
    private String continent;

    public Country(String name, String geoId, String code, int population, String continent)
    {
        this.name = name;
        this.geoId = geoId;
        this.code = code;
        this.continent = continent;
        if (population <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.population = population;
    }

    public String getName()
    {
        return name;
    }

    public String getGeoId()
    {
        return geoId;
    }

    public String getCode()
    {
        return code;
    }

    public int getPopulation()
    {
        return population;
    }

    public String getContinent()
    {
        return continent;
    }

    @Override
    public String toString()
    {
        return "com.jellybeanci.Country{" +
                "name='" + name + '\'' +
                ", geoId='" + geoId + '\'' +
                ", code='" + code + '\'' +
                ", population=" + population +
                ", continent='" + continent + '\'' +
                '}';
    }
}