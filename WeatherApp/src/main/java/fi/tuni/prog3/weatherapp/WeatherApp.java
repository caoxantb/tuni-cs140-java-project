package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.services.LocationDataService;
import fi.tuni.prog3.weatherapp.services.WeatherDataService;
import fi.tuni.prog3.weatherapp.utilities.LocationUtils;
import fi.tuni.prog3.weatherapp.views.MainContent;
import fi.tuni.prog3.weatherapp.views.SearchContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WeatherApp extends Application {

  private static final int WINDOW_HEIGHT = 720;
  private static final int WINDOW_WIDTH = 1080;
  LocationDataService locationDataService = new LocationDataService();
  WeatherDataService weatherDataService = new WeatherDataService();
  LocationUtils locationUtils = new LocationUtils();
  Map<String, String> mapCountry = locationUtils.getMapFromFile("./json/countries.json");

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
  SimpleListProperty<LocationData> history = new SimpleListProperty<>(
      FXCollections.observableList(locationDataService.getHistory()));
  SimpleListProperty<LocationData> favorites = new SimpleListProperty<>(
      FXCollections.observableList(locationDataService.getAllFavoriteLocations()));
  SimpleStringProperty cityQueryValue = new SimpleStringProperty();
  SimpleStringProperty countryQueryValue = new SimpleStringProperty("Choose a country (optional)");
  SimpleStringProperty stateQueryValue = new SimpleStringProperty("Choose a state (optional)");

  TabPane tabpane = new TabPane();

  Tab main = new Tab("Weather Report");
  Button unitSystemButton = new Button();

  @Override
  public void start(Stage stage) throws IOException {
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

    // =======================================================================

    Tab search = new Tab("Search and History");

    VBox searchBox = new VBox(10);
    searchBox
        .setStyle("-fx-background-color: linear-gradient(to bottom right, rgba(196, 196, 196, 0.7), transparent);");
    Text labelSearch = new Text("Search");
    searchBox.setPadding(new Insets(10));
    labelSearch.setFont(Font.font("Futura", FontWeight.BOLD, 16));
    searchBox.getChildren().add(labelSearch);
    searchBox.setPrefWidth(1080 / 2);
    searchBox.setPrefHeight(720 / 2);

    Text labelCity = new Text("City");
    Region spacer1 = new Region();
    HBox.setHgrow(spacer1, Priority.ALWAYS);
    TextField cityTextField = new TextField();
    cityTextField.setPrefWidth(250);
    HBox cityQuery = new HBox(labelCity, spacer1, cityTextField);
    cityQuery.setSpacing(10);
    cityQuery.setAlignment(Pos.CENTER);
    cityQuery.setPrefWidth(1080 / 2);

    Text labelCountry = new Text("Country");
    Region spacer2 = new Region();
    HBox.setHgrow(spacer2, Priority.ALWAYS);
    ObservableList<String> optionsCountry = FXCollections.observableArrayList(
        "Choose a country (optional)");
    String[] allCountries = locationUtils.getKeysFromFile("./json/countries.json");
    optionsCountry.addAll(allCountries);
    ComboBox<String> comboBoxCountry = new ComboBox<>(optionsCountry);
    comboBoxCountry.setPrefWidth(250);
    comboBoxCountry.setValue("Choose a country (optional)");
    HBox countryQuery = new HBox(labelCountry, spacer2, comboBoxCountry);
    countryQuery.setSpacing(10);
    countryQuery.setAlignment(Pos.CENTER);
    countryQuery.setPrefWidth(1080 / 2);

    Text labelState = new Text("State");
    Region spacer3 = new Region();
    HBox.setHgrow(spacer3, Priority.ALWAYS);
    ObservableList<String> optionsState = FXCollections.observableArrayList(
        "Choose a state (optional)");
    String[] states = locationUtils.getStatesCode();
    optionsState.addAll(states);
    ComboBox<String> comboBoxState = new ComboBox<>(optionsState);
    comboBoxState.setDisable(true);
    comboBoxState.setPrefWidth(250);
    comboBoxState.setValue("Choose a state (optional)");
    HBox stateQuery = new HBox(labelState, spacer3, comboBoxState);
    stateQuery.setSpacing(10);
    stateQuery.setAlignment(Pos.CENTER);
    stateQuery.setPrefWidth(1080 / 2);
    comboBoxState.setOnAction(event -> {
      stateQueryValue.set(comboBoxState.getValue());
    });
    comboBoxCountry.setOnAction(event -> {
      countryQueryValue.set(comboBoxCountry.getValue());
      if (!countryQueryValue.get().equals("United States")) {
        comboBoxState.setDisable(true);
      } else {
        comboBoxState.setDisable(false);
      }
    });

    Button searchButton = new Button("Search");
    HBox searchButtonBox = new HBox(searchButton);
    searchButtonBox.setAlignment(Pos.CENTER_RIGHT);
    searchButtonBox.setPrefWidth(1080 / 2);

    searchBox.getChildren().addAll(cityQuery, countryQuery, stateQuery, searchButtonBox);

    EventHandler<MouseEvent> searchEvent = event -> {
      unit.set("initial");
      try {
        HBox searchTabBox = (HBox) event.getSource();
        LocationData newLocation = (LocationData) searchTabBox.getProperties().get("location");
        latestSearchLocation.set(newLocation);
        locationDataService.addToHistory(newLocation);
        latitude.set(latestSearchLocation.get().getLat());
        longitude.set(latestSearchLocation.get().getLon());
        currentWeatherData.set(weatherDataService.getCurrentWeatherData(latitude.get(),
            longitude.get()));
        hourlyWeatherData.set(
            FXCollections.observableList(weatherDataService.get5day3HourlyForecast(latitude.get(),
                longitude.get())));
        dailyWeatherData
            .set(FXCollections.observableList(weatherDataService.getWeeklyForecast(latitude.get(),
                longitude.get())));
        history
            .set(FXCollections.observableList(locationDataService.getHistory()));

        Pane newMainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT,
            latestSearchLocation.get(),
            currentWeatherData.get(),
            new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(),
            unitSystemButton)
            .getContent();
        main.setContent(newMainContent);
      } catch (IOException e) {
        e.printStackTrace();
      }
      tabpane.getSelectionModel().select(0);
    };

    search.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        SearchContent newSearchContent = new SearchContent(WINDOW_WIDTH, searchBox, new ArrayList<>(history),
            new ArrayList<>(favorites),
            searchEvent);
        search.setContent(newSearchContent.getContent());
      }
    });

    SearchContent searchContent = new SearchContent(WINDOW_WIDTH, searchBox, new ArrayList<>(history),
        new ArrayList<>(favorites),
        searchEvent);

    search.setContent(searchContent.getContent());

    ///

    tabpane.getTabs().addAll(main, search);

    ScrollPane scrollPane = new ScrollPane(tabpane);
    scrollPane.setPrefSize(1080, 720);
    scrollPane.setFitToWidth(true);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    System.out.println(gson.toJson(locationDataService.queryLocation("Hanoi").get(0)));
    System.out.println(gson.toJson(locationDataService.queryLocation("London").get(0)));
    System.out.println(gson.toJson(locationDataService.queryLocation("Paris").get(0)));

    Scene scene = new Scene(scrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Weather Application");
    stage.show();
  }

  public static void main(String[] args) {
    // test();
    launch();
  }

  // DEBUGGING APIS
  private static void test() {

    WeatherDataService weatherService = new WeatherDataService();

    // Coordinates for a specific location
    float latitude = (float) 61.498020;
    float longitude = (float) 23.760311;

    // WeatherData currentWeather = weatherService.getCurrentWeatherData(latitude,
    // longitude);
    // if (currentWeather != null) {
    // System.out.println("Timestamp: " + currentWeather.getTimestamp());
    // System.out.println("Time Offset: " + currentWeather.getTimeOffset());
    // System.out.println("Temperature: " + currentWeather.getTemp() + " K");
    // System.out.println("Feels Like: " + currentWeather.getTempFeelsLike() + "
    // K");
    // System.out.println("Wind Speed: " + currentWeather.getWindSpeed() + " m/s");
    // System.out.println("Wind Direction: " + currentWeather.getWindDir() + "
    // degrees");
    // System.out.println("Precipitation: " + currentWeather.getPrecipitation() + "
    // mm");
    // System.out.println("Latitude: " + currentWeather.getLat());
    // System.out.println("Longitude: " + currentWeather.getLon());
    // System.out.println("Weather Description: " +
    // currentWeather.getWeatherDesc());
    // System.out.println("Icon: " + currentWeather.getIcon());
    // System.out.println("Sunrise: " + currentWeather.getSunrise());
    // System.out.println("Sunset: " + currentWeather.getSunset());
    // } else {
    // System.out.println("Failed to fetch weather data for the specified
    // location.");
    // }

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

    // ArrayList<WeatherData> weeklyForecast =
    // weatherService.getWeeklyForecast(latitude, longitude);
    // for (WeatherData data : weeklyForecast) {
    // System.out.println("Timestamp: " + data.getTimestamp());
    // System.out.println("Wind Speed: " + data.getWindSpeed());
    // System.out.println("Icon: " + data.getIcon());
    // System.out.println("Precipitation Percentage: " +
    // data.getPrecipitationPerc());
    // System.out.println("Min Temp: " + data.getMinTemp());
    // System.out.println("Max Temp: " + data.getMaxTemp());
    // System.out.println("Wind Direction: " + data.getWindDir() + " degrees");
    // System.out.println("---------------------------------");
    // }
  }
}