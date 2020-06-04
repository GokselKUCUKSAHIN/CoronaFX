import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordParser
{
    private static String regex = "<record><dateRep>(.*)</dateRep><day>(.*)</day><month>(.*)</month><year>(.*)</year><cases>(.*)</cases><deaths>(.*)</deaths><countriesAndTerritories>(.*)</countriesAndTerritories><geoId>(.*)</geoId><countryterritoryCode>(.*)</countryterritoryCode><popData2018>(.*)</popData2018><continentExp>(.*)</continentExp></record>";
    private Pattern recordPattern = Pattern.compile(regex);
    //public static
}
