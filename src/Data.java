import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data
{

    public void printData()
    {
        try
        {
            String data = readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
            System.out.println(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private static String readFromWeb(String webURL) throws IOException
    {
        String data = "";
        URL url = new URL(webURL);
        InputStream is = url.openStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                data += line;
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
        return data;
    }
}