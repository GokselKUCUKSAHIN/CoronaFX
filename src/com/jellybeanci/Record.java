package com.jellybeanci;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void parse(ArrayList<String> recordList)
    {
        String regex = "<record><dateRep>(.+)</dateRep><day>(.+)</day><month>(.+)</month><year>(.+)</year><cases>(.+)</cases><deaths>(.+)</deaths><countriesAndTerritories>(.+)</countriesAndTerritories><geoId>(.+)</geoId><countryterritoryCode>(.*)</countryterritoryCode><popData2018>(\\d{1,})</popData2018><continentExp>(.+)</continentExp></record>";
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
                        System.out.println(matcher.group(8));
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
                                    Integer.parseInt(matcher.group(5)),
                                    Integer.parseInt(matcher.group(6))));
                }
                catch (Exception ex)
                {
                    System.out.println("\tAn Error Occurred\n");
                    ex.printStackTrace();
                }
            }
        }
    }
}