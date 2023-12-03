package fi.tuni.prog3.weatherapp.views;

import java.io.IOException;

import fi.tuni.prog3.weatherapp.services.LocationDataService;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainContent {
  int width, height;
  LocationDataService locationDataService;

  public MainContent (int width, int height, LocationDataService locationDataService) {
    this.width = width;
    this.height = height;
    this.locationDataService = locationDataService;
  }

  public Pane getContent() throws IOException {
    Pane mainContent = new Pane();

    HBox currentWeatherBox = new CurrentWeatherBox(width, height, locationDataService).getContent();
    HBox hourlyWeatherBox = new HourlyWeatherBox(width, height, locationDataService).getContent();
    HBox dailyWeatherBox = new DailyWeatherBox(width, height, locationDataService).getContent();

    VBox container = new VBox();
    container.getChildren().addAll(currentWeatherBox, hourlyWeatherBox, dailyWeatherBox);
    container.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(242, 226, 186, 0.7), transparent);");

    mainContent.getChildren().add(container);
    // main.setContent(mainContent);

    return mainContent;
  }
}
