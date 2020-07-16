package com.jellybeanci;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Record implements Comparable<Record>
{

    //private final ObjectProperty<LocalDate> date;
    private final IntegerProperty dateNumber;
    private final IntegerProperty cases;
    private final IntegerProperty deaths;
    private final StringProperty dateString;

    public Record(int day, int month, int year, int cases, int deaths)
    {
        this.dateNumber = new SimpleIntegerProperty(Integer.parseInt(String.format("%04d%02d%02d", year, month, day)));
        this.dateString = new SimpleStringProperty(String.format("%4d %02d %02d", year, month, day));
        this.cases = new SimpleIntegerProperty(cases);
        this.deaths = new SimpleIntegerProperty(deaths);
    }

    public int getDateNumber()
    {
        return this.dateNumber.get();
    }
    public String getDateString()
    {
        return dateString.get();
    }

    public int getCases()
    {
        return cases.get();
    }

    public int getDeaths()
    {
        return deaths.get();
    }

    public static void parse(ArrayList<String> recordList)
    {
        String regex = "<record><dateRep>(.+)</dateRep><day>(.+)</day><month>(.+)</month><year>(.+)</year><cases>(.+)</cases><deaths>(.+)</deaths><countriesAndTerritories>(.+)</countriesAndTerritories><geoId>(.+)</geoId><countryterritoryCode>(.*)</countryterritoryCode><popData201[89]>(\\d{1,})</popData201[89]><continentExp>(.+)(</continentExp><Cumulative_number_for_14_days_of_COVID-19_cases_per_100000>(.*)</Cumulative_number_for_14_days_of_COVID-19_cases_per_100000>)?</record>";
        Pattern recordPattern = Pattern.compile(regex);
        for (String record : recordList)
        {
            Matcher matcher = recordPattern.matcher(record);
            if (matcher.find())
            {
                try
                {
                    // Create new Country if doesn't exist
                    if (!Country.countries.containsKey(matcher.group(8)))
                    {
                        //System.out.println(matcher.group(8));
                        Country.countries.putIfAbsent(matcher.group(8),
                                new Country(
                                        matcher.group(7),
                                        matcher.group(8),
                                        matcher.group(9),
                                        Integer.parseInt(matcher.group(10)),
                                        matcher.group(11)));
                    }
                    // Add record data to related Country
                    Country.countries.get(matcher.group(8)).insertRecord(
                            new Record(
                                    Integer.parseInt(matcher.group(2)),
                                    Integer.parseInt(matcher.group(3)),
                                    Integer.parseInt(matcher.group(4)),
                                    Math.abs(Integer.parseInt(matcher.group(5))),
                                    Math.abs(Integer.parseInt(matcher.group(6)))
                            )
                    );
                }
                catch (Exception ex)
                {
                    System.out.println("\tAn Error Occurred\n");
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public int compareTo(Record other)
    {
        return ((Integer) this.dateNumber.get()).compareTo(other.dateNumber.get());
    }
}