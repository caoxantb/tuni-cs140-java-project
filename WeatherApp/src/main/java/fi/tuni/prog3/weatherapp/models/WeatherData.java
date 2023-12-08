
package fi.tuni.prog3.weatherapp.models;

/**
 * The {@code WeatherData} class represents weather information for a specific
 * location.
 * It contains details about the current weather conditions and forecasts.
 * This class incorporates timestamped current weather data, hourly and daily
 * forecasts.
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
  private double originalTemp;
  private double originalMinTemp;
  private double originalMaxTemp;

  /**
   * Constructs a {@code WeatherData} object containing weather information.
   * 
   * @param timestamp The timestamp representing the current time in Unix format
   *                  (UTC).
   * @param windSpeed The wind speed in meters per second.
   * @param id        The weather code representing the weather condition.
   * @param windDir   The wind direction in degrees.
   */

  public WeatherData(int timestamp, float windSpeed, int id, int windDir) {
    this.timestamp = timestamp;
    this.windSpeed = windSpeed;
    this.id = id;
    this.windDir = windDir;
  }

  /**
   * Constructs a {@code WeatherData} object containing current weather
   * information.
   * 
   * @param temp          The temperature in Kelvin.
   * @param windSpeed     The wind speed in meters per second.
   * @param icon          The icon code representing the weather condition.
   * @param timestamp     The timestamp representing the current time in Unix
   *                      format (UTC).
   * @param timeOffset    The time offset in seconds from UTC.
   * @param tempFeelsLike The perceived temperature in Kelvin.
   * @param windDir       The wind direction in degrees.
   * @param precipitation The intensity of precipitation in millimeters.
   * @param lat           The latitude of the measured location.
   * @param lon           The longitude of the measured location.
   * @param weatherDesc   A brief description of the weather condition.
   * @param sunrise       The sunrise time in Unix format (UTC).
   * @param sunset        The sunset time in Unix format (UTC).
   * @param id            The weather id of current weather
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
   * Constructs a {@code WeatherData} object containing hourly weather
   * information.
   * 
   * @param timestamp         The time in Unix format (UTC).
   * @param timeOffset        The time offset in seconds from UTC.
   * @param temp              The temperature in Kelvin.
   * @param timeOffset        The time offset in seconds from UTC
   * @param windSpeed         The wind speed in meters per second.
   * @param icon              The icon code representing the weather condition.
   * @param precipitationPerc The probability of precipitation.
   * @param windDir           The wind direction in degrees.
   * @param sunrise           The sunrise time in Unix format (UTC).
   * @param sunset            The sunset time in Unix format (UTC).
   * @param id                The weather id of current weather
   * @param originalTemp      The original temperature in Kelvin
   */
  public WeatherData(int timestamp, int timeOffset, double temp, float windSpeed, String icon, float precipitationPerc,
      int windDir, int id, int sunrise, int sunset, double originalTemp) {
    this(timestamp, windSpeed, id, windDir);
    this.temp = temp;
    this.timeOffset = timeOffset;
    this.precipitationPerc = precipitationPerc;
    this.sunrise = sunrise;
    this.sunset = sunset;
    this.originalTemp = originalTemp;
  }

  /**
   * Constructs a {@code WeatherData} object for daily weather forecasts.
   * 
   * @param timestamp         The time in Unix format (UTC).
   * @param windSpeed         The wind speed in meters per second.
   * @param icon              The icon code representing the weather condition.
   * @param precipitationPerc The probability of precipitation.
   * @param minTemp           The minimum temperature of the day in Kelvin.
   * @param maxTemp           The maximum temperature of the day in Kelvin.
   * @param windDir           The wind direction in degrees.
   * @param id                The weather id of current weather
   * @param originalMinTemp   The original minimum temperature in Kelvin
   * @param originalMaxTemp   The original maximum temperature in Kelvin
   */
  public WeatherData(int timestamp, float windSpeed, String icon, float precipitationPerc, double minTemp,
      double maxTemp, int windDir, int id, double originalMinTemp, double originalMaxTemp) {
    this(timestamp, windSpeed, id, windDir);
    this.precipitationPerc = precipitationPerc;
    this.minTemp = minTemp;
    this.maxTemp = maxTemp;
    this.originalMinTemp = originalMinTemp;
    this.originalMaxTemp = originalMaxTemp;

  }

  // GETTERS for WeatherData Feilds

  /**
   * Retrieves the timestamp representing the current time in Unix format (UTC).
   * 
   * @return The timestamp of the weather data.
   */
  public int getTimestamp() {
    return timestamp;
  }

  /**
   * Retrieves the time offset in seconds from UTC.
   * 
   * @return The time offset of the weather data.
   */
  public int getTimeOffset() {
    return timeOffset;
  }

  /**
   * Retrieves the temperature in Kelvin.
   * 
   * @return The temperature of the weather data.
   */
  public double getTemp() {
    return temp;
  }

  /**
   * Retrieves the perceived temperature in Kelvin.
   * 
   * @return The perceived temperature of the weather data.
   */
  public double getTempFeelsLike() {
    return tempFeelsLike;
  }

  /**
   * Retrieves the wind direction in degrees.
   * 
   * @return The wind direction of the weather data.
   */
  public int getWindDir() {
    return windDir;
  }

  /**
   * Retrieves the wind speed in meters per second.
   * 
   * @return The wind speed of the weather data.
   */
  public float getWindSpeed() {
    return windSpeed;
  }

  /**
   * Retrieves the intensity of precipitation in millimeters.
   * 
   * @return The precipitation intensity of the weather data.
   */
  public float getPrecipitation() {
    return precipitation;
  }

  /**
   * Retrieves the latitude of the measured location.
   * 
   * @return The latitude of the weather data.
   */
  public float getLat() {
    return lat;
  }

  /**
   * Retrieves the longitude of the measured location.
   * 
   * @return The longitude of the weather data.
   */
  public float getLon() {
    return lon;
  }

  /**
   * Retrieves a brief description of the weather condition.
   * 
   * @return The weather description.
   */
  public String getWeatherDesc() {
    return weatherDesc;
  }

  /**
   * Retrieves the weather icon.
   * 
   * @return The weather icon.
   */
  public String getIcon() {
    return icon;
  }

  /**
   * Retrieves the probability of precipitation.
   * 
   * @return The precipitation probability.
   */
  public float getPrecipitationPerc() {
    return precipitationPerc;
  }

  /**
   * Retrieves the minimum temperature of the day in Kelvin.
   * 
   * @return The minimum temperature of the day.
   */
  public double getMinTemp() {
    return minTemp;
  }

  /**
   * Retrieves the maximum temperature of the day in Kelvin.
   * 
   * @return The maximum temperature of the day.
   */
  public double getMaxTemp() {
    return maxTemp;
  }

  /**
   * Retrieves the sunrise time in Unix format (UTC).
   * 
   * @return The sunrise time.
   */
  public int getSunrise() {
    return sunrise;
  }

  /**
   * Retrieves the sunset time in Unix format (UTC).
   * 
   * @return The sunset time.
   */
  public int getSunset() {
    return sunset;
  }

  /**
   * Retrieves the weather ID of the current weather.
   * 
   * @return The weather ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Retrieves the original temperature in Kelvin
   * 
   * @return The weather ID.
   */
  public double getOriginalTemp() {
    return originalTemp;
  }

  /**
   * Retrieves the original maximum temperature of the day in Kelvin.
   * 
   * @return The original max temperature.
   */
  public double getOriginalMaxTemp() {
    return originalMaxTemp;
  }

  /**
   * Retrieves the original minimum temperature of the day in Kelvin.
   * 
   * @return The original min temperature.
   */
  public double getOriginalMinTemp() {
    return originalMinTemp;
  }

  /**
   * Sets the temperature in Kelvin.
   * 
   * @param temp The temperature value to be set.
   */
  public void setTemp(double temp) {
    this.temp = temp;
  }

  /**
   * Sets the perceived temperature in Kelvin.
   * 
   * @param tempFeelsLike The perceived temperature value to be set.
   */
  public void setTempFeelsLike(double tempFeelsLike) {
    this.tempFeelsLike = tempFeelsLike;
  }

  /**
   * Sets the minimum temperature of the day in Kelvin.
   * 
   * @param minTemp The minimum temperature value to be set.
   */
  public void setMinTemp(double minTemp) {
    this.minTemp = minTemp;
  }

  /**
   * Sets the maximum temperature of the day in Kelvin.
   * 
   * @param maxTemp The maximum temperature value to be set.
   */
  public void setMaxTemp(double maxTemp) {
    this.maxTemp = maxTemp;
  }

}
