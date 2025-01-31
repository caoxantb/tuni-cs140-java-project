package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

/**
 * The main class representing the Weather Application.
 * This application fetches weather information, displays it,
 * and allows users to search for locations and view corresponding weather
 * details.
 */

public class WeatherApp extends Application {
  // Using Simple Property to set the original state of data -- Work somewhat like
  // useState() in React
  private static final int WINDOW_HEIGHT = 720;
  private static final int WINDOW_WIDTH = 1280;
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
  SimpleListProperty<LocationData> searchResults = new SimpleListProperty<>(
      FXCollections.emptyObservableList());
  SimpleStringProperty cityQueryValue = new SimpleStringProperty("");
  SimpleStringProperty countryQueryValue = new SimpleStringProperty("Choose a country (optional)");
  SimpleStringProperty stateQueryValue = new SimpleStringProperty("Choose a state (optional)");
  SimpleStringProperty setFavoriteLabel = new SimpleStringProperty(
      locationDataService.isFavoriteLocation(latestSearchLocation.get().getId())
          ? "Remove from favorites"
          : "Add to favorites");
  SimpleIntegerProperty originalTemp = new SimpleIntegerProperty(
      (int) Math.round(currentWeatherData.get().getTemp() - 273.15));

  TabPane tabpane = new TabPane();
  Tab main = new Tab("Weather Report");
  Button unitSystemButton = new Button();
  Button setFavoriteButton = new Button();

  @Override
  public void start(Stage stage) throws IOException {
    // Main tab UI
    unitSystemButton.setText("Convert unit system");
    setFavoriteButton.setText(setFavoriteLabel.get());

    ScrollPane mainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT, latestSearchLocation.get(),
        currentWeatherData.get(),
        new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(), unitSystemButton,
        setFavoriteButton, originalTemp.get())
        .getContent();

    // Event handler for unit conversion
    EventHandler<ActionEvent> unitSystemConverter = event -> {
      if (unit.get().equals("imperial")) {
        unit.set("metric");
      } else {
        unit.set("imperial");
      }
      try {
        ScrollPane newMainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT, latestSearchLocation.get(),
            currentWeatherData.get(),
            new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(),
            unitSystemButton, setFavoriteButton, originalTemp.get())
            .getContent();
        main.setContent(newMainContent);
      } catch (IOException e) {
        e.printStackTrace();
      }
    };

    // Event handler for changing favorite status
    EventHandler<ActionEvent> changeFavoriteStatus = event -> {
      if (setFavoriteLabel.get().equals("Add to favorites")) {
        locationDataService.addFavoriteLocation(latestSearchLocation.get());
        setFavoriteLabel.set("Remove from favorites");
      } else {
        locationDataService.removeFavoriteLocation(latestSearchLocation.get().getId());
        setFavoriteLabel.set("Add to favorites");
      }
      favorites
          .set(FXCollections.observableList(locationDataService.getAllFavoriteLocations()));
      setFavoriteButton.setText(setFavoriteLabel.get());
    };

    unitSystemButton.setOnAction(unitSystemConverter);
    unitSystemButton.setOnMouseEntered(event -> unitSystemButton.setCursor(Cursor.HAND));
    unitSystemButton.setOnMouseExited(event -> unitSystemButton.setCursor(Cursor.DEFAULT));
    setFavoriteButton.setOnAction(changeFavoriteStatus);
    setFavoriteButton.setOnMouseEntered(event -> setFavoriteButton.setCursor(Cursor.HAND));
    setFavoriteButton.setOnMouseExited(event -> setFavoriteButton.setCursor(Cursor.DEFAULT));

    main.setContent(mainContent);

    // =======================================================================

    // Search tab UI
    Tab search = new Tab("Search and History");

    // Event handler when click on a location name in the search tab
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
        setFavoriteLabel.set(locationDataService.isFavoriteLocation(latestSearchLocation.get().getId())
            ? "Remove from favorites"
            : "Add to favorites");
        setFavoriteButton.setText(setFavoriteLabel.get());
        originalTemp.set((int) Math.round(currentWeatherData.get().getTemp() - 273.15));
        ScrollPane newMainContent = new MainContent(WINDOW_WIDTH, WINDOW_HEIGHT,
            latestSearchLocation.get(),
            currentWeatherData.get(),
            new ArrayList<>(hourlyWeatherData.get()), new ArrayList<>(dailyWeatherData.get()), unit.get(),
            unitSystemButton, setFavoriteButton, originalTemp.get())
            .getContent();
        main.setContent(newMainContent);
      } catch (IOException e) {
        e.printStackTrace();
      }
      tabpane.getSelectionModel().select(0);
    };

    // Search panel for the search tab
    VBox searchBox = new VBox(10);
    searchBox
        .setStyle("-fx-background-color: linear-gradient(to bottom right, rgba(196, 196, 196, 0.7), transparent);");
    Text labelSearch = new Text("Search");
    searchBox.setPadding(new Insets(10));
    labelSearch.setFont(Font.font("Futura", FontWeight.BOLD, 20));
    searchBox.getChildren().add(labelSearch);
    searchBox.setPrefWidth(WINDOW_WIDTH / 2);
    searchBox.setPrefHeight(720 / 2);

    // City text field for user input
    Text labelCity = new Text("City");
    Region spacer1 = new Region();
    HBox.setHgrow(spacer1, Priority.ALWAYS);
    TextField cityTextField = new TextField();
    cityTextField.setPrefWidth(250);
    HBox cityQuery = new HBox(labelCity, spacer1, cityTextField);
    cityQuery.setSpacing(10);
    cityQuery.setAlignment(Pos.CENTER);
    cityQuery.setPrefWidth(WINDOW_WIDTH / 2);

    // Country dropdown for user selection
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
    countryQuery.setPrefWidth(WINDOW_WIDTH / 2);

    // State dropdown for user selection
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
    stateQuery.setPrefWidth(WINDOW_WIDTH / 2);
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

    // Search button
    Button searchButton = new Button("Search");
    HBox searchButtonBox = new HBox(searchButton);
    searchButtonBox.setAlignment(Pos.CENTER_RIGHT);
    searchButtonBox.setPrefWidth(WINDOW_WIDTH / 2);

    searchButton.setOnMouseEntered(event -> searchButton.setCursor(Cursor.HAND));
    searchButton.setOnMouseExited(event -> searchButton.setCursor(Cursor.DEFAULT));

    // Event handler for query location
    searchButton.setOnAction(event -> {
      cityQueryValue.set(cityTextField.getText());
      try {
        if (countryQueryValue.get().equals("Choose a country (optional)")) {
          searchResults
              .set(FXCollections.observableList(locationDataService.queryLocation(cityQueryValue.get())));

        } else if (!countryQueryValue.get().equals("United States") || (countryQueryValue.get().equals("United States")
            && stateQueryValue.get().equals("Choose a state (optional)"))) {
          searchResults
              .set(FXCollections
                  .observableList(locationDataService.queryLocation(cityQueryValue.get(),
                      mapCountry.get(countryQueryValue.get()))));
        } else {
          searchResults
              .set(FXCollections
                  .observableList(
                      locationDataService.queryLocation(cityQueryValue.get(), mapCountry.get(countryQueryValue.get()),
                          stateQueryValue.get())));
        }
        SearchContent newSearchContent = new SearchContent(WINDOW_WIDTH, searchBox, new ArrayList<>(searchResults),
            new ArrayList<>(history),
            new ArrayList<>(favorites),
            searchEvent);

        search.setContent(newSearchContent.getContent());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    searchBox.getChildren().addAll(cityQuery, countryQuery, stateQuery, searchButtonBox);

    // Update search tab whenever clicked
    search.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        SearchContent newSearchContent = new SearchContent(WINDOW_WIDTH, searchBox, new ArrayList<>(searchResults),
            new ArrayList<>(history),
            new ArrayList<>(favorites),
            searchEvent);
        search.setContent(newSearchContent.getContent());
      }
    });

    SearchContent searchContent = new SearchContent(WINDOW_WIDTH, searchBox, new ArrayList<>(searchResults),
        new ArrayList<>(history),
        new ArrayList<>(favorites),
        searchEvent);

    search.setContent(searchContent.getContent());

    tabpane.getTabs().addAll(main, search);

    ScrollPane scrollPane = new ScrollPane(tabpane);
    scrollPane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    scrollPane.setFitToWidth(true);

    Scene scene = new Scene(tabpane, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Weather Application");
    stage.show();
  }

  public static void main(String[] args) {
    System.out.println("Welcome to the Weather Application!");
    launch();
  }
}