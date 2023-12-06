package fi.tuni.prog3.weatherapp.views;

import fi.tuni.prog3.weatherapp.controllers.WeatherDataController;
import java.util.ArrayList;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.utilities.WeatherUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class HourlyWeatherBox {
  int width, height;
  LocationData location;
  ArrayList<WeatherData> hourlyWeatherData;
  String unit;

  public HourlyWeatherBox(int width, int height, LocationData location, ArrayList<WeatherData> hourlyWeatherData, String unit) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.hourlyWeatherData = hourlyWeatherData;
    this.unit = unit;
  }

  public HBox getContent() {
    HBox hourlyWeatherBox = new HBox();
    hourlyWeatherBox.setPrefWidth(width);
    hourlyWeatherBox.setSpacing(10);
    hourlyWeatherBox.setPadding(new Insets(20));

    VBox hourlyWeatherContainer = new VBox();
    hourlyWeatherContainer.setPrefWidth(width);
    hourlyWeatherContainer.setSpacing(20);

    Text labelHourly = new Text("Hourly Forecast");
    labelHourly.setFont(Font.font("Futura", FontWeight.BOLD, 16));

    HBox hourlyWeatherStackContainer = new HBox();
    hourlyWeatherStackContainer.setPrefWidth(width);
    hourlyWeatherStackContainer.setSpacing(20);

    for (WeatherData hourlyWeather : hourlyWeatherData) {
        
      WeatherDataController weatherDataController = new WeatherDataController(hourlyWeather, unit);
      WeatherUtils weatherUtils = new WeatherUtils();

      VBox hourlyWeatherStack = new VBox();
      hourlyWeatherStack.setPrefWidth(width / 8);
      hourlyWeatherStack.setSpacing(5);
      hourlyWeatherStack.setAlignment(Pos.TOP_CENTER);

      String dateTimeString = weatherUtils.getLocalTime(hourlyWeather.getTimestamp(), hourlyWeather.getTimeOffset());
      LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")); 
      Text forecastHour = new Text(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
      forecastHour.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

      ImageView hourlyWeatherIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/weather-91.png")));
      hourlyWeatherIcon.setFitHeight(40);
      hourlyWeatherIcon.setPreserveRatio(true);

      HBox hourlyPos = new HBox();
      hourlyPos.setPrefWidth(width / 8);
      hourlyPos.setAlignment(Pos.CENTER);

      ImageView hourlyPosIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/pos.png")));
      hourlyPosIcon.setFitHeight(14);
      hourlyPosIcon.setPreserveRatio(true);

      Text hourlyPosText = weatherDataController.getWeatherPrecipitationPercentage();
      hourlyPosText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

      HBox hourlyWind = new HBox();
      hourlyWind.setPrefWidth(width / 8);
      hourlyWind.setAlignment(Pos.CENTER);

      ImageView hourlyWindIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/wind.png")));
      hourlyWindIcon.setFitHeight(14);
      hourlyWindIcon.setPreserveRatio(true);

      Text hourlyWindText = weatherDataController.getWeatherWindSpeed();
      hourlyWindText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
      VBox.setMargin(hourlyWindText, new Insets(0, 4, 0, 0));

      ImageView hourlyWindDirectionIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/arrow-thick-top.png")));
      hourlyWindDirectionIcon.setFitHeight(14);
      hourlyWindDirectionIcon.setPreserveRatio(true);
      Rotate hourlyRotate = new Rotate(180, 7, 7);
      hourlyWindDirectionIcon.getTransforms().add(hourlyRotate);

      Text hourlyTemp = weatherDataController.getWeatherTemp();
      hourlyTemp.setFont(Font.font("Futura", FontWeight.BOLD, 14));
      VBox.setMargin(hourlyTemp, new Insets(10, 0, 0, 0));

      Rectangle hourlyTempSpan = new Rectangle(12, 52);
      hourlyTempSpan.setFill(Color.BLACK);
      hourlyTempSpan.setArcWidth(12);
      hourlyTempSpan.setArcHeight(12);
      VBox.setMargin(hourlyTempSpan, new Insets(0, 6, 0, 0));

      hourlyPos.getChildren().addAll(hourlyPosIcon, hourlyPosText);
      hourlyWind.getChildren().addAll(hourlyWindIcon, hourlyWindText, hourlyWindDirectionIcon);
      hourlyWeatherStack.getChildren().addAll(forecastHour, hourlyWeatherIcon, hourlyPos, hourlyWind, hourlyTemp,
          hourlyTempSpan);
      hourlyWeatherStackContainer.getChildren().add(hourlyWeatherStack);
    }

    hourlyWeatherContainer.getChildren().addAll(labelHourly, hourlyWeatherStackContainer);
    hourlyWeatherBox.getChildren().addAll(hourlyWeatherContainer);

    return hourlyWeatherBox;
  }
}
