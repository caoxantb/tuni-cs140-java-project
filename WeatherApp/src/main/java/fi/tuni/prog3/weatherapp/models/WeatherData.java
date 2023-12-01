
package fi.tuni.prog3.weatherapp.Models;

/**
 * The {@code WeatherData} class represents weather information for a specific location.
 * It contains details about the current weather conditions and forecasts.
 * This class incorporates timestamped current weather data, hourly and daily forecasts.
 *  
 * @author Ayman Khan
 */

public class WeatherData {
    
    // Fields representing current weather conditions and forecasts
    private int timestamp;
    private int timeOffset;
    private int temp;
    private int tempFeelsLike;
    private int windDir;
    private int minTemp;
    private int maxTemp;
    private float windSpeed;   
    private float precipitation;
    private float lat;
    private float lon;
    private float precipitationPerc;
    private String weatherDesc;
    private String icon;

    /**
     * Constructs a {@code WeatherData} object containing weather information.
     *       
     * @param timestamp         The timestamp representing the current time in Unix format (UTC).
     * @param windSpeed         The wind speed in meters per second.
     * @param icon              The icon code representing the weather condition.
     */
    
    public WeatherData(int timestamp, float windSpeed, String icon) {
        this.timestamp = timestamp;
        this.windSpeed = windSpeed;
        this.icon = icon;
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
     */
    
    public WeatherData(int timestamp, int timeOffset, int temp, int tempFeelsLike, int windDir,
                       float windSpeed, float precipitation, float lat, float lon,
                       String weatherDesc, String icon) {
        this(timestamp, windSpeed, icon);
        this.temp = temp;
        this.timeOffset = timeOffset;
        this.tempFeelsLike = tempFeelsLike;
        this.windDir = windDir;
        this.precipitation = precipitation;
        this.lat = lat;
        this.lon = lon;
        this.weatherDesc = weatherDesc;
    }
      
    /**
     * Constructs a {@code WeatherData} object containing hourly weather information.
     * @param timestamp             The time in Unix format (UTC).
     * @param temp                  The temperature in Kelvin.
     * @param windSpeed             The wind speed in meters per second.
     * @param icon                  The icon code representing the weather condition.
     * @param precipitationPerc     The probability of precipitation.
     */
    public WeatherData(int timestamp, int temp, float windSpeed, String icon, float precipitationPerc) {
        this(timestamp, windSpeed, icon);
        this.temp = temp;
        this.precipitationPerc = precipitationPerc;
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
     */
    public WeatherData(int timestamp, float windSpeed, String icon, float precipitationPerc, int minTemp, int maxTemp) {
        this(timestamp, windSpeed, icon);
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

    public int getTemp() {
        return temp;
    }

    public int getTempFeelsLike() {
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
    
    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }
}

