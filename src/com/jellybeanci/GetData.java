package com.jellybeanci;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

public class GetData
{
    //URL : https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/

    public static File download(String urlString) throws IOException
    {
        // CHECK IF FOLDER EXIST
        File downloadDir = new File("Downloads");
        if (!downloadDir.exists())
        {
            final boolean mkdir = downloadDir.mkdir();
        }
        File fileName = new File("Downloads/" + LocalDate.now() + ".xml");
        if (!fileName.exists() && !fileName.isDirectory())
        {
            URL url = new URL(urlString);
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))
            )
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    writer.write(line + "\n");
                }
                //System.out.println(fileName.getName() + " indirildi.");
            }
        }
        return fileName;
    }


    public static ArrayList<String> getDataFromAnywhere(String path) throws IOException
    {
        Reader reader;
        if (isURL(path))
        {

            reader = new FileReader(download(path));
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
            StringBuilder record = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                if (isStarted && !line.equals("</records>"))
                {
                    record.append(line);
                    if (line.equals("</record>"))
                    {
                        //record is complete save it and restart!
                        records.add(record.toString());
                        record = new StringBuilder();
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
}