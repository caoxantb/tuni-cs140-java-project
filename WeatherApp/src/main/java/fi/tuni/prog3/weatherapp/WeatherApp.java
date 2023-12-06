package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.services.LocationDataService;
import fi.tuni.prog3.weatherapp.services.WeatherDataService;
import fi.tuni.prog3.weatherapp.views.MainContent;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherApp extends Application {

  private static final int WINDOW_HEIGHT = 720;
  private static final int WINDOW_WIDTH = 1080;

  @Override
  public void start(Stage stage) throws IOException {
    LocationDataService locationDataService = new LocationDataService();
    WeatherDataService weatherDataService = new WeatherDataService();
    SimpleStringProperty unit = new SimpleStringProperty("initial");

    SimpleObjectProperty<LocationData> latestSearchLocation = new SimpleObjectProperty<>(
        locationDataService.getCurrentLocation());
    SimpleFloatProperty latitude = new SimpleFloatProperty(latestSearchLocation.get().getLat());
    SimpleFloatProperty longitude = new SimpleFloatProperty(latestSearchLocation.get().getLon());
    SimpleObjectProperty<WeatherData> currentWeatherData = new SimpleObjectProperty<>(
        weatherDataService.getCurrentWeatherData(latitude.get(), longitude.get()));
    SimpleListProperty<WeatherData> hourlyWeatherData = new SimpleListProperty<>(
        FXCollections.observableList(weatherDataService.get5day3HourlyForecast(latitude.get(), longitude.get())));
    SimpleListProperty<WeatherData> dailyWeatherData = new SimpleListProperty<>(
        FXCollections.observableList(weatherDataService.getWeeklyForecast(latitude.get(), longitude.get())));

    TabPane tabpane = new TabPane();

    Tab main = new Tab("Weather Report");
    Button unitSystemButton = new Button();
    unitSystemButton.setText("Unit system!");
    Pane mainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT, latestSearchLocation.get(),
        currentWeatherData.get(),
        new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(), unitSystemButton)
        .getContent();

    EventHandler<ActionEvent> unitSystemConverter = event -> {
      if (unit.get().equals("imperial")) {
        unit.set("metric");
      } else {
        unit.set("imperial");
      }
      try {
        Pane newMainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT, latestSearchLocation.get(),
            currentWeatherData.get(),
            new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(),
            unitSystemButton)
            .getContent();
        main.setContent(newMainContent);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("Unit system changed to: " + unit.get());
    };

    unitSystemButton.setOnAction(unitSystemConverter);

    main.setContent(mainContent);

    Tab search = new Tab("Search and History");

    // =======================================================================
    // THIS CODE SECTION IS FOR TESTING PURPOSES ONLY. SHOULD BE REMOVED ONCE
    // THE SEARCH AND HISTORY PAGE HAS BEEN IMPLEMENTED.
    VBox quadrant1 = new VBox();
    VBox quadrant2 = new VBox();
    VBox quadrant3 = new VBox();
    VBox quadrant4 = new VBox();
    quadrant1.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");
    quadrant1.setPrefWidth(1080 / 2);
    quadrant1.setPrefHeight(720 / 2);

    quadrant2.setStyle("-fx-background-color: lightgreen; -fx-padding: 10px;");
    quadrant2.setPrefWidth(1080 / 2);
    quadrant2.setPrefHeight(720 / 2);

    quadrant3.setStyle("-fx-background-color: lightcoral; -fx-padding: 10px;");
    quadrant3.setPrefWidth(1080 / 2);
    quadrant3.setPrefHeight(720 / 2);

    quadrant4.setStyle("-fx-background-color: lightgoldenrodyellow; -fx-padding: 10px;");
    quadrant4.setPrefWidth(1080 / 2);
    quadrant4.setPrefHeight(720 / 2);

    for (int i = 0; i < 3; i++) {
      int x = i;
      HBox hbox = new HBox();
      hbox.getChildren().add(new Label("hello"));
      hbox.setOnMouseClicked(event -> {
        unit.set("initial");
        try {
          LocationData newLocation = locationDataService.queryLocation("London").get(x);
          latestSearchLocation.set(newLocation);
          locationDataService.addToHistory(newLocation);
          latitude.set(latestSearchLocation.get().getLat());
          longitude.set(latestSearchLocation.get().getLon());
          currentWeatherData.set(weatherDataService.getCurrentWeatherData(latitude.get(), longitude.get()));
          hourlyWeatherData.set(
              FXCollections.observableList(weatherDataService.get5day3HourlyForecast(latitude.get(), longitude.get())));
          dailyWeatherData
              .set(FXCollections.observableList(weatherDataService.getWeeklyForecast(latitude.get(), longitude.get())));

          Pane newMainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT, latestSearchLocation.get(),
              currentWeatherData.get(),
              new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(),
              unitSystemButton)
              .getContent();
          main.setContent(newMainContent);
        } catch (IOException e) {
          e.printStackTrace();
        }
        tabpane.getSelectionModel().select(0);
      });
      hbox.setPrefWidth(1080 / 2);
      hbox.setPrefHeight(30);
      hbox.setStyle("-fx-background-color: lightcoral;");
      quadrant4.getChildren().add(hbox);
    }

    HBox top = new HBox(quadrant1, quadrant2);
    HBox bottom = new HBox(quadrant3, quadrant4);
    VBox all = new VBox(top, bottom);

    search.setContent(all);
    // ====================================================================

    tabpane.getTabs().addAll(main, search);

    ScrollPane scrollPane = new ScrollPane(tabpane);
    scrollPane.setPrefSize(1080, 720);
    scrollPane.setFitToWidth(true);

    Scene scene = new Scene(scrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Weather Application");
    stage.show();
  }

  public static void main(String[] args) {
    test();
    launch();
  }

  // DEBUGGING APIS
  private static void test() {

    WeatherDataService weatherService = new WeatherDataService();

    // Coordinates for a specific location
    float latitude = 44.34f;
    float longitude = 10.99f;

    WeatherData currentWeather = weatherService.getCurrentWeatherData(latitude, longitude);
    if (currentWeather != null) {
      System.out.println("Timestamp: " + currentWeather.getTimestamp());
      System.out.println("Time Offset: " + currentWeather.getTimeOffset());
      System.out.println("Temperature: " + currentWeather.getTemp() + " K");
      System.out.println("Feels Like: " + currentWeather.getTempFeelsLike() + " K");
      System.out.println("Wind Speed: " + currentWeather.getWindSpeed() + " m/s");
      System.out.println("Wind Direction: " + currentWeather.getWindDir() + " degrees");
      System.out.println("Precipitation: " + currentWeather.getPrecipitation() + " mm");
      System.out.println("Latitude: " + currentWeather.getLat());
      System.out.println("Longitude: " + currentWeather.getLon());
      System.out.println("Weather Description: " + currentWeather.getWeatherDesc());
      System.out.println("Icon: " + currentWeather.getIcon());
      System.out.println("Sunrise: " + currentWeather.getSunrise());
      System.out.println("Sunset: " + currentWeather.getSunset());
    } else {
      System.out.println("Failed to fetch weather data for the specified location.");
    }

    ArrayList<WeatherData> forecastData = weatherService.get5day3HourlyForecast(latitude, longitude);
    for (WeatherData data : forecastData) {
      System.out.println("Timestamp: " + data.getTimestamp());
      System.out.println("Wind Speed: " + data.getWindSpeed());
      System.out.println("Icon: " + data.getIcon());
      System.out.println("Temperature: " + data.getTemp());
      System.out.println("Precipitation Percentage: " + data.getPrecipitationPerc());
      System.out.println("Wind Direction: " + data.getWindDir() + " degrees");
      System.out.println("--------------------------------------------");
    }

    ArrayList<WeatherData> weeklyForecast = weatherService.getWeeklyForecast(latitude, longitude);
    for (WeatherData data : weeklyForecast) {
      System.out.println("Timestamp: " + data.getTimestamp());
      System.out.println("Wind Speed: " + data.getWindSpeed());
      System.out.println("Icon: " + data.getIcon());
      System.out.println("Precipitation Percentage: " + data.getPrecipitationPerc());
      System.out.println("Min Temp: " + data.getMinTemp());
      System.out.println("Max Temp: " + data.getMaxTemp());
      System.out.println("Wind Direction: " + data.getWindDir() + " degrees");
      System.out.println("---------------------------------");
    }
  }
}