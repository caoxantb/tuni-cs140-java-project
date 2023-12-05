/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp.controllers;

import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.utilities.WeatherUtils;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Ayman Khan
 */
public class WeatherDataController {
  WeatherData weather;
  WeatherUtils weatherUtils = new WeatherUtils();

  public WeatherDataController(WeatherData weather) {
    this.weather = weather;
  }

  public Text getWeatherLocalTime() {
    int timestamp = weather.getTimestamp();
    int timeOffset = weather.getTimeOffset();
    Text currentTime = new Text(weatherUtils.getLocalTime(timestamp, timeOffset));
    currentTime.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    return currentTime;
  }

  public Text getWeatherTemp() {
    int tempInt = weather.getTemp();
    Text temp = new Text(String.format("%d°C", tempInt));
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 60));
    return temp;
  }

  public Text getWeatherDescription() {
    String weatherDesc = weather.getWeatherDesc();
    Text desc = new Text(Character.toUpperCase(weatherDesc.charAt(0)) + weatherDesc.substring(1));
    desc.setFont(Font.font("Futura", FontWeight.BOLD, 20));
    return desc;
  }

  public Text getWeatherPrecipitation() {
    float precipitationFloat = weather.getPrecipitation();
    Text precipitation = new Text(String.format(" %.1f mm/h", precipitationFloat));
    precipitation.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(precipitation, new Insets(0, 20, 0, 0));
    return precipitation;
  }

  public Text getWeatherTempFeelsLike() {
    int tempFeelsLikeInt = weather.getTempFeelsLike();
    Text tempFeelsLike = new Text(String.format("Feels like %d°", tempFeelsLikeInt));
    tempFeelsLike.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(tempFeelsLike, new Insets(0, 12, 0, 0));
    return tempFeelsLike;
  }

  public Text getWeatherWindSpeed() {
    float windSpeed = weather.getWindSpeed();
    Text wind = new Text(String.format(" %.1f km/h", windSpeed));
    wind.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(wind, new Insets(0, 4, 0, 0));
    return wind;
  }

  public ImageView getWeatherWindDir() {
    int windDir = weather.getWindDir();
    ImageView windDirectionIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/arrow-thick-top.png")));
    windDirectionIcon.setFitHeight(14);
    windDirectionIcon.setPreserveRatio(true);
    Rotate rotate = new Rotate(windDir, 7, 7);
    windDirectionIcon.getTransforms().add(rotate);
    return windDirectionIcon;
  }
}
