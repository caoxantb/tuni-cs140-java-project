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

public class CurrentWeatherBox {
  int width, height;
  LocationData location;
  WeatherData weather;
  String unit;

  public CurrentWeatherBox(int width, int height, LocationData location, WeatherData weather, String unit) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.weather = weather;
    this.unit = unit;
  }

  public HBox getContent() throws IOException {
    LocationDataController locationDataController = new LocationDataController(location);
    WeatherDataController weatherDataController = new WeatherDataController(weather, unit);

    Text currentTime = weatherDataController.getWeatherLocalTime();
    Text cityName = locationDataController.getSearchedLocationCity();
    ImageView countryFlag = locationDataController.getSearchedLocationFlag();
    Text stateAndCountryName = locationDataController.getSearchedLocationCountryAndState();
    ImageView weatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/weather-91.png")));
    weatherIcon.setFitHeight(100);
    weatherIcon.setPreserveRatio(true);
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
        new Image(getClass().getResourceAsStream("/location.png")));
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
        new Image(getClass().getResourceAsStream("/precipitation.png")));
    precipitationIcon.setFitHeight(14);
    precipitationIcon.setPreserveRatio(true);

    ImageView windIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/wind.png")));
    windIcon.setFitHeight(14);
    windIcon.setPreserveRatio(true);

    rowCity.getChildren().addAll(locationIcon, cityName);
    rowCountry.getChildren().addAll(countryFlag, stateAndCountryName);
    weatherTempBox.getChildren().addAll(weatherIcon, temp);
    weatherDetail.getChildren().addAll(precipitationIcon, precipitation, tempFeelsLike, windIcon, wind,
        windDirectionIcon);
    currentWeatherStack.getChildren().addAll(currentTime, rowCity, rowCountry, weatherTempBox, desc,
        weatherDetail);
    currentWeatherBox.getChildren().addAll(currentWeatherStack);

    return currentWeatherBox;
  }

}
