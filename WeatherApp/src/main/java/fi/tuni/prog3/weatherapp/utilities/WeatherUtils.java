
package fi.tuni.prog3.weatherapp.utilities;

import fi.tuni.prog3.weatherapp.models.WeatherData;
import java.time.DayOfWeek;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * The {@code WeatherUtils} class provides utility methods for weather-related
 * operations.
 * These methods include unit system conversion, local time calculation, and
 * wind direction SVG generation.
 * 
 * @author Ayman Khan
 */
public class WeatherUtils {

  /**
   * Converts the given WeatherData fields to the specified unit system
   * (metric/imperial).
   * 
   * @param data   The WeatherData object containing weather information.
   * @param system The unit system to which the fields should be converted
   *               ("metric" or "imperial").
   * @return The WeatherData object with fields converted to the specified unit
   *         system.
   *         Returns null if data or system is null or if the system is unknown.
   */
  public WeatherData convertToUnitSystem(WeatherData data, String system) {
    if (data == null || system == null) {
      return null;
    }

    // Convert fields based on the system
    switch (system) {
      case "initial":
        // Convert to metric system on initialization
        data.setTemp(data.getTemp() - 273.15); // Convert temperature from Fahrenheit to Celsius
        data.setTempFeelsLike(data.getTempFeelsLike() - 273.15); // Convert feels-like temperature
        data.setMinTemp(data.getMinTemp() - 273.15); // Convert min temperature
        data.setMaxTemp(data.getMaxTemp() - 273.15); // Convert max temperature
        break;
      case "metric":
        // Convert to metric system (if required)
        // For example, if the system is metric, convert Fahrenheit to Celsius
        data.setTemp((data.getTemp() - 32) * 5 / 9); // Convert temperature from Fahrenheit to Celsius
        data.setTempFeelsLike((data.getTempFeelsLike() - 32) * 5 / 9); // Convert feels-like temperature
        data.setMinTemp((data.getMinTemp() - 32) * 5 / 9); // Convert min temperature
        data.setMaxTemp((data.getMaxTemp() - 32) * 5 / 9); // Convert max temperature
        break;
      case "imperial":
        // Convert to imperial system (if required)
        // Implement conversion logic for the imperial system here
        // For example, if you want to convert temperature from Celsius to Fahrenheit
        data.setTemp(data.getTemp() * 9 / 5 + 32); // Convert temperature from Kelvin to Fahrenheit
        data.setTempFeelsLike(data.getTempFeelsLike() * 9 / 5 + 32); // Convert feels-like temperature
        data.setMinTemp(data.getMinTemp() * 9 / 5 + 32); // Convert min temperature
        data.setMaxTemp(data.getMaxTemp() * 9 / 5 + 32); // Convert max temperature
        break;
      default:
        // Handle unknown system
        return null;
    }

    return data;
  }

  /**
   * Returns the local time in string format ("dd.MM.yyyy HH:mm:ss") based on the
   * provided timestamp and time offset.
   * 
   * @param timestamp  The timestamp representing the time in Unix format (UTC).
   * @param timeOffset The time offset in seconds from UTC.
   * @return The local time in the specified format.
   */
  public String getLocalTime(int timestamp, int timeOffset) {
    // Convert timestamp to milliseconds
    long epochSeconds = (long) timestamp;
    long epochMillis = epochSeconds * 1000;

    // Calculate the offset time in milliseconds
    int offsetSeconds = timeOffset;
    int offsetMillis = offsetSeconds * 1000;

    // Calculate the final time with offset
    long finalTimeMillis = epochMillis + offsetMillis;

    // Create an Instant from the calculated time
    Instant instant = Instant.ofEpochMilli(finalTimeMillis);

    // Convert to LocalDateTime using the given timezone
    ZoneId zoneId = ZoneId.of("UTC"); // Use the appropriate timezone
    LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

    // Format the LocalDateTime to the desired string format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    return localDateTime.format(formatter);
  }

  /**
   * Returns the week day in string format based on the provided timestamp and
   * time offset.
   * 
   * @param timestamp  The timestamp representing the time in Unix format (UTC).
   * @param timeOffset The time offset in seconds from UTC.
   * @return The local time in the specified format.
   */
  public String getWeekDay(int timestamp, int timeOffset) {
    Instant instant = Instant.ofEpochSecond(timestamp);
    instant = instant.plusSeconds(timeOffset);

    DayOfWeek dayOfWeek = instant.atOffset(ZoneOffset.UTC).getDayOfWeek();

    return dayOfWeek.toString();
  }
  /**
   * Returns the week day in string format based on the provided timestamp and
   * time offset.
   * 
   * @param id The id of weather condition from OpenWeatherMap
   * @param currentTime The current time (UTC) according in Unix format
   * @param sunrise The sunrise time in unix format
   * @param sunset The sunset time in unix format
   * @param timesOfDay Check if icon is required to display according to times of day (day/night)
   * @return The local time in the specified format.
   */
  public String getIconString(int id, int currentTime, int sunrise, int sunset, boolean timesOfDay) {
    String prefix;
    String iconString = "";
    prefix = !timesOfDay ? "wi"
        : ((currentTime >= sunrise && currentTime < sunset)
            || currentTime >= sunrise + 86400 && currentTime < sunset + 86400) ? "wi-day" : "wi-night";
    if (id < 210) {
      iconString = String.format("%s-thun-rain", prefix);
    } else if (id < 230) {
      iconString = String.format("%s-thun", prefix);
    } else if (id < 300) {
      iconString = String.format("%s-thun-show", prefix);
    } else if (id < 500) {
      iconString = String.format("%s-rizz", prefix);
    } else if (id < 520) {
      iconString = String.format("%s-rain", prefix);
    } else if (id < 600) {
      iconString = String.format("%s-show", prefix);
    } else if (id < 611) {
      iconString = String.format("%s-snow", prefix);
    } else if (id < 615) {
      iconString = String.format("%s-snow-sleet", prefix);
    } else if (id < 701) {
      iconString = String.format("%s-snow-mix", prefix);
    } else if (id < 751 && id != 731) {
      iconString = String.format("%s-fog", prefix);
    } else if (id < 771) {
      prefix = "wi";
      iconString = String.format("%s-dust", prefix);
    } else if (id < 800) {
      prefix = "wi";
      iconString = String.format("%s-tornado", prefix);
    } else if (id == 800) {
      iconString = String.format("%s-clear", prefix);
    } else if (id == 801) {
      iconString = String.format("%s-few", prefix);
    } else if (id == 802) {
      iconString = String.format("%s-scattered", prefix);
    } else if (id == 803) {
      iconString = String.format("%s-broken", prefix);
    } else if (id == 804) {
      prefix = "wi";
      iconString = String.format("%s-overcast", prefix);
    }

    return iconString;
  }
}
