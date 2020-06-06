import java.util.HashMap;
import java.util.Map;

public class Country
{

    private String name;
    private String geoId;
    private String code;
    private int population;
    private String continent;
    public static HashMap<String, Country> countries = new HashMap<>();

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

    @Override
    public String toString()
    {
        return "Country{" +
                "name='" + name + '\'' +
                ", geoId='" + geoId + '\'' +
                ", code='" + code + '\'' +
                ", population=" + population +
                ", continent='" + continent + '\'' +
                '}';
    }
}