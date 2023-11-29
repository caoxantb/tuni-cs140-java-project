
package fi.tuni.prog3.weatherapp.models;

/**
 * The {@code WeatherData} class represents weather information for a specific location.
 * It contains details about the current weather conditions and forecasts.
 * This class incorporates timestamped weather data and forecasts.
 * 
 * It is extended by {@link WeatherHourlyData} and {@link WeatherDailyData} classes to represent hourly and daily forecasts, respectively.
 * 
 * @author Ayman Khan
 */

import java.util.List;

public class WeatherData {
    
    // Fields representing current weather conditions and forecasts
    private int timestamp;
    private int timeOffset;
    private int temp;
    private int tempFeelsLike;
    private int windDir;
    private float windSpeed;   
    private float precipitation;
    private float lat;
    private float lon;
    private String weatherDesc;
    private String icon;
    private List<WeatherHourlyData> hourlyForecast;
    private List<WeatherDailyData> dailyForecast;

    /**
     * Constructs a {@code WeatherData} object containing weather information.
     * 
     * @param timestamp         The timestamp representing the current time in Unix format (UTC).
     * @param timeOffset        The time offset in seconds from UTC.
     * @param temp              The temperature in Kelvin.
     * @param tempFeelsLike     The perceived temperature in Kelvin.
     * @param windDir           The wind direction in degrees.
     * @param windSpeed         The wind speed in meters per second.
     * @param precipitation     The intensity of precipitation in millimeters.
     * @param lat               The latitude of the measured location.
     * @param lon               The longitude of the measured location.
     * @param weatherDesc       A brief description of the weather condition.
     * @param icon              The icon code representing the weather condition.
     * @param hourlyForecast    A list of hourly forecasts ({@link WeatherHourlyData}).
     * @param dailyForecast     A list of daily forecasts ({@link WeatherDailyData}).
     */
    public WeatherData(int timestamp, int timeOffset, int temp, int tempFeelsLike, int windDir,
                       float windSpeed, float precipitation, float lat, float lon,
                       String weatherDesc, String icon, List<WeatherHourlyData> hourlyForecast,
                       List<WeatherDailyData> dailyForecast) {
        this.timestamp = timestamp;
        this.timeOffset = timeOffset;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.windDir = windDir;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.lat = lat;
        this.lon = lon;
        this.weatherDesc = weatherDesc;
        this.icon = icon;
        this.hourlyForecast = hourlyForecast;
        this.dailyForecast = dailyForecast;
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

    public List<WeatherHourlyData> getHourlyForecast() {
        return hourlyForecast;
    }

    public List<WeatherDailyData> getDailyForecast() {
        return dailyForecast;
    }
    
}

/**
 * The {@code WeatherHourlyData} class extends {@link WeatherData} and represents hourly weather forecasts.
 * It contains specific details for hourly weather predictions.
 * 
 * @author Ayman
 */
class WeatherHourlyData extends WeatherData {

    // Feilds for hourly forecasts
    private int timestamp;
    private int temp;
    private float windSpeed;
    private String icon;
    private float precipitationPerc;

    /**
     * Constructs a {@code WeatherHourlyData} object for hourly weather forecasts.
     * 
     * @param timestamp             The time in Unix format (UTC).
     * @param temp                  The temperature in Kelvin.
     * @param windSpeed             The wind speed in meters per second.
     * @param icon                  The icon code representing the weather condition.
     * @param precipitationPerc     The probability of precipitation.
     */
    public WeatherHourlyData(int timestamp, int temp, float windSpeed, String icon, float precipitationPerc) {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0, "", "", null, null);
        this.timestamp = timestamp;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.icon = icon;
        this.precipitationPerc = precipitationPerc;
    }

    // GETTERS for WeatherHourlyData Feilds
    
    public int getTimestamp() {
        return timestamp;
    }

    public int getTemp() {
        return temp;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public float getPrecipitationPerc() {
        return precipitationPerc;
    }
}

/**
 * The {@code WeatherDailyData} class extends {@link WeatherData} and represents daily weather forecasts.
 * It contains specific details for daily weather predictions.
 * 
 * @author Ayman
 */
class WeatherDailyData extends WeatherData {

    // Feilds for daily forecasts
    private int timestamp;
    private float windSpeed;
    private String icon;
    private float precipitationPerc;
    private int minTemp;
    private int maxTemp;

    /**
     * Constructs a {@code WeatherDailyData} object for daily weather forecasts.
     * 
     * @param timestamp             The time in Unix format (UTC).
     * @param windSpeed             The wind speed in meters per second.
     * @param icon                  The icon code representing the weather condition.
     * @param precipitationPerc     The probability of precipitation.
     * @param minTemp               The minimum temperature of the day in Kelvin.
     * @param maxTemp               The maximum temperature of the day in Kelvin.
     */
    public WeatherDailyData(int timestamp, float windSpeed, String icon, float precipitationPerc, int minTemp, int maxTemp) {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0, "", "", null, null);
        this.timestamp = timestamp;
        this.windSpeed = windSpeed;
        this.icon = icon;
        this.precipitationPerc = precipitationPerc;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
    
    // GETTERS for WeatherDailyData Feilds
    public int getTimestamp() {
        return timestamp;
    }

    public float getWindSpeed() {
        return windSpeed;
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
