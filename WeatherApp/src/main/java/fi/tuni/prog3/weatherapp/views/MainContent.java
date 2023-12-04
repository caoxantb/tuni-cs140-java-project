package fi.tuni.prog3.weatherapp.views;

import java.io.IOException;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainContent {
  int width, height;
  LocationData location;
  WeatherData currentWeatherData;

  public MainContent (int width, int height, LocationData location, WeatherData currentWeatherData) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.currentWeatherData = currentWeatherData;
  }

  public Pane getContent() throws IOException {
    Pane mainContent = new Pane();

    HBox currentWeatherBox = new CurrentWeatherBox(width, height, location, currentWeatherData).getContent();
    HBox hourlyWeatherBox = new HourlyWeatherBox(width, height, location).getContent();
    HBox dailyWeatherBox = new DailyWeatherBox(width, height, location).getContent();

    VBox container = new VBox();
    container.getChildren().addAll(currentWeatherBox, hourlyWeatherBox, dailyWeatherBox);
    container.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(242, 226, 186, 0.7), transparent);");

    mainContent.getChildren().add(container);
    // main.setContent(mainContent);

    return mainContent;
  }
}
