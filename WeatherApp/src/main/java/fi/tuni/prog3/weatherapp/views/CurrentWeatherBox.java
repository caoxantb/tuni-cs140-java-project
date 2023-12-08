package fi.tuni.prog3.weatherapp.views;

import fi.tuni.prog3.weatherapp.controllers.LocationDataController;
import fi.tuni.prog3.weatherapp.controllers.WeatherDataController;
import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * The {@code CurrentWeatherBox} class generates a graphical representation
 * of the current weather conditions and location details.
 */
public class CurrentWeatherBox {
  int width, height;
  LocationData location;
  WeatherData weather;
  String unit;

  /**
   * Constructs a CurrentWeatherBox object with specified dimensions,
   * location data, weather data, and unit system.
   *
   * @param width    The width of the weather box.
   * @param height   The height of the weather box.
   * @param location The location data.
   * @param weather  The weather data.
   * @param unit     The unit system for temperature.
   */
  public CurrentWeatherBox(int width, int height, LocationData location, WeatherData weather, String unit) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.weather = weather;
    this.unit = unit;
  }

  /**
   * Retrieves an HBox containing the graphical representation of the current weather conditions.
   *
   * @return An HBox containing the current weather representation.
   * @throws IOException If an error occurs while loading resources.
   */
  public HBox getContent() throws IOException {
    LocationDataController locationDataController = new LocationDataController(location);
    WeatherDataController weatherDataController = new WeatherDataController(weather, unit);

    Text currentDay = weatherDataController.getWeatherLocalDay();
    Text currentTime = weatherDataController.getWeatherLocalTime();
    Text cityName = locationDataController.getSearchedLocationCity();
    ImageView countryFlag = locationDataController.getSearchedLocationFlag();
    Text stateAndCountryName = locationDataController.getSearchedLocationCountryAndState();
    ImageView weatherIcon = weatherDataController.getWeatherIcon();
    Text temp = weatherDataController.getWeatherTemp();
    Text desc = weatherDataController.getWeatherDescription();
    Text precipitation = weatherDataController.getWeatherPrecipitation();
    Text tempFeelsLike = weatherDataController.getWeatherTempFeelsLike();
    Text wind = weatherDataController.getWeatherWindSpeed();
    ImageView windDirectionIcon = weatherDataController.getWeatherWindDir();

    HBox currentWeatherBox = new HBox();
    currentWeatherBox.setPrefWidth(width);
    currentWeatherBox.setPadding(new Insets(20, 0, 20, 0));

    VBox currentWeatherStack = new VBox();
    VBox.setMargin(currentWeatherStack, new Insets(10, 0, 0, 0));
    currentWeatherStack.setSpacing(10);
    currentWeatherStack.setAlignment(Pos.TOP_CENTER);
    currentWeatherStack.setPrefWidth(width);

    HBox rowCity = new HBox();
    rowCity.setPrefWidth(width);
    rowCity.setAlignment(Pos.CENTER);
    rowCity.setSpacing(5);

    ImageView locationIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/label-icons/location.png")));
    locationIcon.setFitHeight(32);
    locationIcon.setPreserveRatio(true);

    HBox rowCountry = new HBox();
    rowCountry.setPrefWidth(width);
    rowCountry.setAlignment(Pos.CENTER);
    rowCountry.setSpacing(5);

    HBox weatherTempBox = new HBox();
    weatherTempBox.setPrefWidth(width);
    weatherTempBox.setAlignment(Pos.CENTER);
    weatherTempBox.setSpacing(5);

    HBox weatherDetail = new HBox();
    weatherDetail.setPrefWidth(width);
    weatherDetail.setAlignment(Pos.CENTER);

    ImageView precipitationIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/label-icons/precipitation.png")));
    precipitationIcon.setFitHeight(14);
    precipitationIcon.setPreserveRatio(true);

    ImageView windIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/label-icons/wind.png")));
    windIcon.setFitHeight(14);
    windIcon.setPreserveRatio(true);

    rowCity.getChildren().addAll(locationIcon, cityName);
    rowCountry.getChildren().addAll(countryFlag, stateAndCountryName);
    weatherTempBox.getChildren().addAll(weatherIcon, temp);
    weatherDetail.getChildren().addAll(precipitationIcon, precipitation, tempFeelsLike, windIcon, wind,
        windDirectionIcon);
    currentWeatherStack.getChildren().addAll(currentDay, currentTime, rowCity, rowCountry, weatherTempBox, desc,
        weatherDetail);
    currentWeatherBox.getChildren().addAll(currentWeatherStack);

    return currentWeatherBox;
  }

}
