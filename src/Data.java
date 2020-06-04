import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data
{

    public static void main(String[] args) throws IOException
    {
        recordParser("dasda");
        //printData();
        //readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
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

    private static  void recordParser(String record)
    {

        //
        Matcher matcher = recordPattern.matcher("<record><dateRep>11/05/2020</dateRep><day>11</day><month>5</month><year>2020</year><cases>138</cases><deaths>1</deaths><countriesAndTerritories>Armenia</countriesAndTerritories><geoId>AM</geoId><countryterritoryCode>ARM</countryterritoryCode><popData2018>2951776</popData2018><continentExp>Europe</continentExp></record>");
        if (matcher.find())
        {
            for (int i = 1; i <= 11; i++)
            {
                System.out.printf("%s ", matcher.group(i));
            }
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