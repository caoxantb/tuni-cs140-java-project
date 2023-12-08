package fi.tuni.prog3.weatherapp.views;

import java.io.IOException;
import java.util.ArrayList;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

public class MainContent {
  int width, height, originalTemp;
  LocationData location;
  WeatherData currentWeatherData;
  ArrayList<WeatherData> hourlyWeatherData;
  ArrayList<WeatherData> dailyWeatherData;
  String unit;
  Button button, setFavoriteButton;

  public MainContent(int width, int height, LocationData location, WeatherData currentWeatherData,
      ArrayList<WeatherData> hourlyWeatherData, ArrayList<WeatherData> dailyWeatherData, String unit, Button button,
      Button setFavoriteButton, int originalTemp) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.currentWeatherData = currentWeatherData;
    this.hourlyWeatherData = hourlyWeatherData;
    this.dailyWeatherData = dailyWeatherData;
    this.unit = unit;
    this.button = button;
    this.setFavoriteButton = setFavoriteButton;
    this.originalTemp = originalTemp;
  }

  public ScrollPane getContent() throws IOException {
    Pane mainContent = new Pane();

    HBox currentWeatherBox = new CurrentWeatherBox(width, height, location, currentWeatherData, unit).getContent();
    HBox hourlyWeatherBox = new HourlyWeatherBox(width, height, location, hourlyWeatherData, unit).getContent();
    HBox dailyWeatherBox = new DailyWeatherBox(width, height, location, dailyWeatherData, unit).getContent();
    HBox utils = new HBox(10);
    utils.setAlignment(Pos.CENTER);
    utils.getChildren().addAll(button, setFavoriteButton);

    VBox container = new VBox();
    container.getChildren().addAll(currentWeatherBox, utils, hourlyWeatherBox, dailyWeatherBox);
    setTempColor(container, originalTemp);

    mainContent.getChildren().add(container);
    ScrollPane scrollPane = new ScrollPane(mainContent);
    scrollPane.setFitToWidth(true);

    return scrollPane;
  }

  private void setTempColor(VBox container, int temp) {
    String[] colors = {"rgba(232, 160, 191, 0.7)", "rgba(96, 150, 180, 0.7)", "rgba(185, 243, 252, 0.7)", 
                      "rgba(203, 255, 169, 0.7)", "rgba(242, 226, 186, 0.7)", "rgba(249, 181, 114, 0.7)",
                      "rgba(233, 119, 119, 0.7)"};
    int colorIndex = (temp + 25)/10;
    colorIndex = colorIndex < 0 ? 0 : colorIndex > 6 ? 6 : colorIndex;
    System.out.println(colorIndex);

    String colorCode = String.format("-fx-background-color: linear-gradient(to bottom, %s, transparent)", colors[colorIndex]);
    container.setStyle(colorCode);
  }
}
