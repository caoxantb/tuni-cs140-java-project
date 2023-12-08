
package fi.tuni.prog3.weatherapp.models;

/**
 * The {@code WeatherData} class represents weather information for a specific location.
 * It contains details about the current weather conditions and forecasts.
 * This class incorporates timestamped current weather data, hourly and daily forecasts.
 *  
 * @author Ayman Khan
 */

public class WeatherData {
    
    // Fields representing current weather conditions and forecasts
    private int id;
    private int timestamp;
    private int timeOffset;
    private double temp;
    private double tempFeelsLike;
    private int windDir;
    private double minTemp;
    private double maxTemp;
    private float windSpeed;   
    private float precipitation;
    private float lat;
    private float lon;
    private float precipitationPerc;
    private String weatherDesc;
    private String icon;
    private int sunrise;
    private int sunset;

    /**
     * Constructs a {@code WeatherData} object containing weather information.
     *       
     * @param timestamp         The timestamp representing the current time in Unix format (UTC).
     * @param windSpeed         The wind speed in meters per second.
     * @param icon              The icon code representing the weather condition.
     * @param windDir           The wind direction in degrees.
     */
    
    public WeatherData(int timestamp, float windSpeed, int id, int windDir) {
        this.timestamp = timestamp;
        this.windSpeed = windSpeed;
        this.id = id;
        this.windDir = windDir;
    }
    
    /**
     * Constructs a {@code WeatherData} object containing current weather information.
     * 
     * @param temp              The temperature in Kelvin.
     * @param windSpeed         The wind speed in meters per second.
     * @param icon              The icon code representing the weather condition.
     * @param timestamp         The timestamp representing the current time in Unix format (UTC).
     * @param timeOffset        The time offset in seconds from UTC.    
     * @param tempFeelsLike     The perceived temperature in Kelvin.
     * @param windDir           The wind direction in degrees.   
     * @param precipitation     The intensity of precipitation in millimeters.
     * @param lat               The latitude of the measured location.
     * @param lon               The longitude of the measured location.
     * @param weatherDesc       A brief description of the weather condition. 
     * @param sunrise           The sunrise time in Unix format (UTC).
     * @param sunset            The sunset time in Unix format (UTC).
     * @param id                The weather id of current weather
     */
    
    public WeatherData(int timestamp, int timeOffset, double temp, double tempFeelsLike, int windDir,
                       float windSpeed, float precipitation, float lat, float lon,
                       String weatherDesc, String icon, int sunrise, int sunset, int id) {
        this(timestamp, windSpeed, id, windDir);
        this.temp = temp;
        this.timeOffset = timeOffset;
        this.tempFeelsLike = tempFeelsLike;
        this.precipitation = precipitation;
        this.lat = lat;
        this.lon = lon;
        this.weatherDesc = weatherDesc;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
      
    /**
     * Constructs a {@code WeatherData} object containing hourly weather information.
     * @param timestamp             The time in Unix format (UTC).
     * @param temp                  The temperature in Kelvin.
     * @param timeOffset            The time offset in seconds from UTC
     * @param windSpeed             The wind speed in meters per second.
     * @param icon                  The icon code representing the weather condition.
     * @param precipitationPerc     The probability of precipitation.
     * @param windDir               The wind direction in degrees.
     * @param sunrise               The sunrise time in Unix format (UTC).
     * @param sunset                The sunset time in Unix format (UTC).
     * @param id                    The weather id of current weather
     */
    public WeatherData(int timestamp, int timeOffset, double temp, float windSpeed, String icon, float precipitationPerc, int windDir, int id, int sunrise, int sunset) {
        this(timestamp, windSpeed, id, windDir);
        this.temp = temp;
        this.timeOffset = timeOffset;
        this.precipitationPerc = precipitationPerc;
        this.sunrise = sunrise;
        this.sunset = sunset;
    } 
    
    /**
     * Constructs a {@code WeatherData} object for daily weather forecasts.
     * 
     * @param timestamp             The time in Unix format (UTC).
     * @param windSpeed             The wind speed in meters per second.
     * @param icon                  The icon code representing the weather condition.
     * @param precipitationPerc     The probability of precipitation.
     * @param minTemp               The minimum temperature of the day in Kelvin.
     * @param maxTemp               The maximum temperature of the day in Kelvin.
     * @param windDir               The wind direction in degrees.
     * @param id                    The weather id of current weather
     */
    public WeatherData(int timestamp, float windSpeed, String icon, float precipitationPerc, double minTemp, double maxTemp, int windDir, int id) {
        this(timestamp, windSpeed, id, windDir);
        this.precipitationPerc = precipitationPerc;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
    
    // GETTERS for WeatherData Feilds
    
    public int getTimestamp() {
        return timestamp;
    }

    public int getTimeOffset() {
        return timeOffset;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempFeelsLike() {
        return tempFeelsLike;
    }

    public int getWindDir() {
        return windDir;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getIcon() {
        return icon;
    }
    
    public float getPrecipitationPerc() {
        return precipitationPerc;
    }
    
    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
    
    public int getSunrise() {
        return sunrise;
    }
    
    public int getSunset() {
        return sunset;
    }

    public int getId() {
        return id;
    }
    
    // SETTERS for WeatherData Temperature Feilds
    
    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setTempFeelsLike(double tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
    
}

