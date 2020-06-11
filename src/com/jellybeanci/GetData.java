package com.jellybeanci;

import com.jellybeanci.Country;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetData
{

    public static void main(String[] args) throws IOException
    {
        ArrayList<String> contList = readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
        // FOR-EACH
        Record.parse(contList);
        for (Country country : Country.countries.values())
        {
            System.out.println(country.toString());
        }
    }

/*
    private static ArrayList<String> loadDataFromTxt(String FileName) throws FileNotFoundException
    {
        ArrayList<String> rows = new ArrayList<>();
        String path = "QueryFolder/" + FileName; //res/data.txt //default
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            //Scan everyline and append to arrayList
            rows.add(scanner.nextLine());
        }
        return rows;
    }*/

    public static void printData()
    {
        try
        {
            ArrayList<String> data = readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
            for (String record : data)
            {
                System.out.println(record);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static void download(String urlString) throws IOException
    {
        // CHECK IF FOLDER EXIST
        File downloadDir = new File("Downloads");
        if (!downloadDir.exists())
        {
            System.out.println(downloadDir.getName() + "Download klasörü oluşturuluyor.");
            boolean result = false;
            try
            {
                downloadDir.mkdir();
                result = true;
            }
            catch (SecurityException se)
            {
                //handle it
                System.out.println("Klasör oluşturulamadı!");
            }
            if (result)
            {
                System.out.println("Klasör başarı ile oluşturuldu.");
            }
        }
        //
        File fileName = new File("Downloads/" + LocalDate.now() + ".xml");
        if (!fileName.exists() && !fileName.isDirectory())
        {
            URL url = new URL(urlString);
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            )
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    writer.write(line + "\n");
                }
                System.out.println(fileName.getName() + " indirildi.");
            }
        } else if (fileName.exists() && !fileName.isDirectory())
        {
            System.out.println(fileName.getName() + " zaten bugün içerisinde indirilmiş.");
        }
    }


    private static ArrayList<String> readFromWeb(String webURL) throws IOException
    {
        ArrayList<String> records = new ArrayList<>();
        URL url = new URL(webURL);
        InputStream is = url.openStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            boolean isStarted = false;
            String record = "";
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                if (isStarted && !line.equals("</records>"))
                {
                    record += line;
                    if (line.equals("</record>"))
                    {
                        //record is complete save it and restart!
                        records.add(record);
                        record = "";
                    }
                }
                if (!isStarted && line.equals("<records>"))
                {
                    isStarted = true;
                }
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IOException();
        }
        return records;
    }
}