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

/**
 * The {@code HourlyWeatherBox} class generates a graphical representation
 * of the hourly weather forecast.
 */
public class HourlyWeatherBox {
  int width, height;
  LocationData location;
  ArrayList<WeatherData> hourlyWeatherData;
  String unit;

  /**
   * Constructs an HourlyWeatherBox object with specified dimensions, location data,
   * hourly weather data, and unit system.
   *
   * @param width             The width of the weather box.
   * @param height            The height of the weather box.
   * @param location          The location data.
   * @param hourlyWeatherData The hourly weather data.
   * @param unit              The unit system for temperature.
   */
  public HourlyWeatherBox(int width, int height, LocationData location, ArrayList<WeatherData> hourlyWeatherData,
      String unit) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.hourlyWeatherData = hourlyWeatherData;
    this.unit = unit;
  }

  /**
   * Retrieves an HBox containing the graphical representation of the hourly weather forecast.
   *
   * @return An HBox containing the hourly weather forecast representation.
   */
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
      forecastHour.setFont(Font.font("Futura", FontWeight.BOLD, 14));

      ImageView hourlyWeatherIcon = weatherDataController.getHourlyWeatherIcon();
      
      HBox hourlyPop = new HBox();
      hourlyPop.setPrefWidth(width / 8);
      hourlyPop.setAlignment(Pos.CENTER);

      ImageView hourlyPopIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/label-icons/pos.png")));
      hourlyPopIcon.setFitHeight(14);
      hourlyPopIcon.setPreserveRatio(true);

      Text hourlyPopText = weatherDataController.getWeatherPrecipitationPercentage();

      HBox hourlyWind = new HBox();
      hourlyWind.setPrefWidth(width / 8);
      hourlyWind.setAlignment(Pos.CENTER);

      ImageView hourlyWindIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/label-icons/wind.png")));
      hourlyWindIcon.setFitHeight(14);
      hourlyWindIcon.setPreserveRatio(true);

      Text hourlyWindText = weatherDataController.getWeatherWindSpeed();

      ImageView hourlyWindDirectionIcon = weatherDataController.getWeatherWindDir();

      Text hourlyTemp = weatherDataController.getWeatherTemp();
      hourlyTemp.setFont(Font.font("Futura", FontWeight.BOLD, 14));

      Rectangle hourlyTempSpan = new Rectangle(12, 52);
      hourlyTempSpan.setFill(Color.BLACK);
      hourlyTempSpan.setArcWidth(12);
      hourlyTempSpan.setArcHeight(12);
      VBox.setMargin(hourlyTempSpan, new Insets(0, 6, 0, 0));

      hourlyPop.getChildren().addAll(hourlyPopIcon, hourlyPopText);
      hourlyWind.getChildren().addAll(hourlyWindIcon, hourlyWindText, hourlyWindDirectionIcon);
      hourlyWeatherStack.getChildren().addAll(forecastHour, hourlyWeatherIcon, hourlyPop, hourlyWind, hourlyTemp,
          hourlyTempSpan);
      hourlyWeatherStackContainer.getChildren().add(hourlyWeatherStack);
    }

    hourlyWeatherContainer.getChildren().addAll(labelHourly, hourlyWeatherStackContainer);
    hourlyWeatherBox.getChildren().addAll(hourlyWeatherContainer);

    return hourlyWeatherBox;
  }
}
