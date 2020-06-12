package com.jellybeanci;

import com.jellybeanci.Country;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetData
{

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("Enter->");
            System.out.println(isURL(scanner.nextLine()));
        }



        /*ArrayList<String> contList = readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
        Record.parse(contList);
        for (Country country : Country.countries.values())
        {
            System.out.println(country.toString());
        }*/
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


    public static ArrayList<String> getDataFromAnywhere(String path) throws IOException
    {
        Reader reader;
        if (isURL(path))
        {
            //URL
            URL url = new URL(path);
            InputStream is = url.openStream();
            reader = new InputStreamReader(is);
        } else
        {
            //Maybe File
            File file = new File(path);
            if (file.isDirectory() || !file.exists())
            {
                throw new FileNotFoundException("File Not Found");
            } else
            {
                reader = new FileReader(file);
            }
        }
        // READER DONE
        ArrayList<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(reader))
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

    public static boolean isURL(String path)
    {
        return (path.matches("http[s]?://(www)?(.*)"));
    }
/*

    public static ArrayList<String> readFromWeb(String webURL) throws IOException
    {
        ArrayList<String> records = new ArrayList<>();
        URL url = new URL(webURL);
        InputStream is = url.openStream();
        Reader rdr = new InputStreamReader(is);
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

    public static ArrayList<String> readFromFile(String dataPath) throws IOException
    {
        ArrayList<String> records = new ArrayList<>();
        File xmlFile = new File(dataPath);
        try (BufferedReader br = new BufferedReader(new FileReader(xmlFile)))
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
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IOException();
        }
        return records;
    }*/
}