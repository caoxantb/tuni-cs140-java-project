
package fi.tuni.prog3.weatherapp.controllers;

import java.time.Instant;

import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.utilities.WeatherUtils;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 * Controller class responsible for managing and handling WeatherData in the UI.
 * Contains methods to retrieve specific details of weather information for display purposes.
 * Handles conversion between different unit systems for temperature measurement (imperial/metric).
 * 
 * @author Ayman Khan
 */
public class WeatherDataController {
  WeatherData weather;
  String unit;
  WeatherUtils weatherUtils = new WeatherUtils();

  /**
     * Constructs a WeatherDataController object with the specified WeatherData and unit.
     * 
     * @param weather The WeatherData object to be managed by this controller.
     * @param unit    The unit system for temperature measurement (imperial/metric).
     */
  public WeatherDataController(WeatherData weather, String unit) {
    this.weather = weatherUtils.convertToUnitSystem(weather, unit);
    this.unit = unit;
  }

  /**
     * Retrieves the local day (day of the week and date) of the weather.
     * 
     * @return A Text object representing the local day.
     */
  public Text getWeatherLocalDay() {
    int timestamp = (int) Instant.now().getEpochSecond();
    int timeOffset = weather.getTimeOffset();
    String currentDayString = weatherUtils.getLocalTime(timestamp, timeOffset).substring(0, 10);
    String currentDayOfWeekString = weatherUtils.getWeekDay(timestamp, timeOffset);
    Text currentDay = new Text(String.format("%s, %s", currentDayOfWeekString, currentDayString));
    currentDay.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    return currentDay;
  }

  /**
     * Retrieves the day of the week of the weather.
     * 
     * @return A Text object representing the day of the week.
     */
  public Text getWeatherWeekDay() {
    int timestamp = weather.getTimestamp();
    int timeOffset = weather.getTimeOffset();
    String weekDayString = weatherUtils.getWeekDay(timestamp, timeOffset);

    String truncatedWeekDay = (weekDayString.length() >= 10) ? weekDayString.substring(0, 10) : weekDayString;
    Text weekDay = new Text(truncatedWeekDay);
    weekDay.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    return weekDay;
  }

  /**
     * Retrieves the local time of the weather.
     * 
     * @return A Text object representing the local time.
     */
  public Text getWeatherLocalTime() {
    int timestamp = (int) Instant.now().getEpochSecond();
    int timeOffset = weather.getTimeOffset();
    Text currentTime = new Text(weatherUtils.getLocalTime(timestamp, timeOffset).substring(11, 16));
    currentTime.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    return currentTime;
  }

  /**
     * Retrieves the temperature of the weather.
     * 
     * @return A Text object representing the temperature.
     */
  public Text getWeatherTemp() {
    int tempInt = (int) Math.round(weather.getTemp());
    String tempUnit = unit == "imperial" ? "F" : "C";
    Text temp = new Text(String.format("%d°%s", tempInt, tempUnit));
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 60));
    return temp;
  }

  /**
     * Retrieves the weather icon representing the current weather conditions.
     * 
     * @return An ImageView representing the weather icon.
     */
  public ImageView getWeatherIcon() {
    int id = weather.getId();
    int sunrise = weather.getSunrise();
    int sunset = weather.getSunset();
    String path = weatherUtils.getIconString(id, (int) Instant.now().getEpochSecond(), sunrise, sunset, true);
    ImageView weatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream(String.format("/weather-icons/%s.png", path))));
    weatherIcon.setFitHeight(100);
    weatherIcon.setPreserveRatio(true);
    return weatherIcon;
  }

  /**
     * Retrieves the hourly weather icon representing the weather conditions for the hour.
     * 
     * @return An ImageView representing the hourly weather icon.
     */
  public ImageView getHourlyWeatherIcon() {
    int id = weather.getId();
    int sunrise = weather.getSunrise();
    int sunset = weather.getSunset();
    int currentTime = weather.getTimestamp();
    String path = weatherUtils.getIconString(id, currentTime, sunrise, sunset, true);
    ImageView weatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream(String.format("/weather-icons/%s.png", path))));
    weatherIcon.setFitHeight(60);
    weatherIcon.setPreserveRatio(true);
    return weatherIcon;
  }

  /**
     * Retrieves the daily weather icon representing the weather conditions for the day.
     * 
     * @return An ImageView representing the daily weather icon.
     */
  public ImageView getDailyWeatherIcon() {
    int id = weather.getId();
    int currentTime = weather.getTimestamp();
    String path = weatherUtils.getIconString(id, currentTime, 0, 0, false);
    ImageView weatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream(String.format("/weather-icons/%s.png", path))));
    weatherIcon.setFitHeight(60);
    weatherIcon.setPreserveRatio(true);
    return weatherIcon;
  }

   /**
     * Retrieves the minimum temperature of the weather.
     * 
     * @return A Text object representing the minimum temperature.
     */
  public Text getWeatherMinTemp() {
    int tempInt = (int) Math.round(weather.getMinTemp());
    String tempUnit = unit == "imperial" ? "F" : "C";
    Text temp = new Text(String.format("%d°%s", tempInt, tempUnit));
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    return temp;
  }

  /**
     * Retrieves the maximum temperature of the weather.
     * 
     * @return A Text object representing the maximum temperature.
     */
  public Text getWeatherMaxTemp() {
    int tempInt = (int) Math.round(weather.getMaxTemp());
    String tempUnit = unit == "imperial" ? "F" : "C";
    Text temp = new Text(String.format("%d°%s", tempInt, tempUnit));
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    VBox.setMargin(temp, new Insets(10, 0, 0, 0));
    return temp;
  }

  /**
     * Retrieves the weather description providing information about current conditions.
     * 
     * @return A Text object representing the weather description.
     */
  public Text getWeatherDescription() {
    String weatherDesc = weather.getWeatherDesc();
    Text desc = new Text(Character.toUpperCase(weatherDesc.charAt(0)) + weatherDesc.substring(1));
    desc.setFont(Font.font("Futura", FontWeight.BOLD, 20));
    return desc;
  }

   /**
     * Retrieves the precipitation amount in the weather.
     * 
     * @return A Text object representing the precipitation.
     */
  public Text getWeatherPrecipitation() {
    float precipitationFloat = weather.getPrecipitation();
    Text precipitation = new Text(String.format(" %.1f mm/h", precipitationFloat));
    precipitation.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(precipitation, new Insets(0, 20, 0, 0));
    return precipitation;
  }

  /**
     * Retrieves the percentage chance of precipitation in the weather.
     * 
     * @return A Text object representing the precipitation percentage.
     */
  public Text getWeatherPrecipitationPercentage() {
    float precipitationPercentageFloat = weather.getPrecipitationPerc() * 100;
    Text precipitationPercentage = new Text(String.format(" %.0f%%", precipitationPercentageFloat));
    precipitationPercentage.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    return precipitationPercentage;
  }

  /**
     * Retrieves the 'feels like' temperature of the weather.
     * 
     * @return A Text object representing the 'feels like' temperature.
     */
  public Text getWeatherTempFeelsLike() {
    int tempFeelsLikeInt = (int) Math.round(weather.getTempFeelsLike());
    String tempUnit = unit == "imperial" ? "F" : "C";
    Text tempFeelsLike = new Text(String.format("Feels like %d°%s", tempFeelsLikeInt, tempUnit));
    tempFeelsLike.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(tempFeelsLike, new Insets(0, 12, 0, 0));
    return tempFeelsLike;
  }

  /**
     * Retrieves the wind speed in the weather.
     * 
     * @return A Text object representing the wind speed.
     */
  public Text getWeatherWindSpeed() {
    float windSpeed = weather.getWindSpeed();
    Text wind = new Text(String.format(" %.1f km/h", windSpeed));
    wind.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(wind, new Insets(0, 4, 0, 0));
    return wind;
  }

  /**
     * Retrieves the wind direction icon representing the direction of the wind.
     * 
     * @return An ImageView representing the wind direction icon.
     */
  public ImageView getWeatherWindDir() {
    int windDir = weather.getWindDir();
    ImageView windDirectionIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/label-icons/arrow-thick-top.png")));
    windDirectionIcon.setFitHeight(14);
    windDirectionIcon.setPreserveRatio(true);
    Rotate rotate = new Rotate(windDir, 7, 7);
    windDirectionIcon.getTransforms().add(rotate);
    return windDirectionIcon;
  }
}
