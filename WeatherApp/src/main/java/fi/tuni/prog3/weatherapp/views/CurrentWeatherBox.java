package fi.tuni.prog3.weatherapp.views;

import fi.tuni.prog3.weatherapp.controllers.LocationDataController;
import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.utilities.WeatherUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.io.IOException;


public class CurrentWeatherBox {
  int width, height;
  LocationData location;
  WeatherData currentWeatherData ;

  public CurrentWeatherBox(int width, int height, LocationData location, WeatherData currentWeatherData) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.currentWeatherData = currentWeatherData;
  }

  public HBox getContent() throws IOException {
    LocationDataController locationDataController = new LocationDataController(location);
    WeatherUtils weatherUtils = new WeatherUtils();

    HBox currentWeatherBox = new HBox();
    currentWeatherBox.setPrefWidth(width);
    currentWeatherBox.setPadding(new Insets(20, 0, 20, 0));

    VBox currentWeatherStack = new VBox();
    VBox.setMargin(currentWeatherStack, new Insets(10, 0, 0, 0));
    currentWeatherStack.setSpacing(10);
    currentWeatherStack.setAlignment(Pos.TOP_CENTER);
    currentWeatherStack.setPrefWidth(width);

    int timestamp = currentWeatherData.getTimestamp();
    int timeOffset = currentWeatherData.getTimeOffset();
    Text currentTime = new Text(weatherUtils.getLocalTime(timestamp, timeOffset));
    currentTime.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    HBox rowCity = new HBox();
    rowCity.setPrefWidth(width);
    rowCity.setAlignment(Pos.CENTER);
    rowCity.setSpacing(5);

    ImageView locationIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/location.png")));
    locationIcon.setFitHeight(32);
    locationIcon.setPreserveRatio(true);

    Text cityName = locationDataController.getSearchedLocationCity();

    rowCity.getChildren().addAll(locationIcon, cityName);

    HBox rowCountry = new HBox();
    rowCountry.setPrefWidth(width);
    rowCountry.setAlignment(Pos.CENTER);
    rowCountry.setSpacing(5);

    ImageView countryFlag = locationDataController.getSearchedLocationFlag();

    Text stateAndCountryName = locationDataController.getSearchedLocationCountryAndState();

    rowCountry.getChildren().addAll(countryFlag, stateAndCountryName);

    HBox weatherTempBox = new HBox();
    weatherTempBox.setPrefWidth(width);
    weatherTempBox.setAlignment(Pos.CENTER);
    weatherTempBox.setSpacing(5);

    ImageView weatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/weather-91.png")));
    weatherIcon.setFitHeight(100);
    weatherIcon.setPreserveRatio(true);

    int tempInt = currentWeatherData.getTemp();
    Text temp = new Text(String.format("%d°C", tempInt));
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 60));

    String weatherDesc = currentWeatherData.getWeatherDesc();
    Text desc = new Text(Character.toUpperCase(weatherDesc.charAt(0)) + weatherDesc.substring(1));
    desc.setFont(Font.font("Futura", FontWeight.BOLD, 20));

    HBox weatherDetail = new HBox();
    weatherDetail.setPrefWidth(width);
    weatherDetail.setAlignment(Pos.CENTER);

    ImageView precipitationIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/precipitation.png")));
    precipitationIcon.setFitHeight(14);
    precipitationIcon.setPreserveRatio(true);

    float precipitationFloat = currentWeatherData.getPrecipitation();
    Text precipitation = new Text(String.format(" %.1f mm/h", precipitationFloat));
    precipitation.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(precipitation, new Insets(0, 20, 0, 0));

    int tempFeelsLikeInt = currentWeatherData.getTempFeelsLike();
    Text tempFeelsLike = new Text(String.format("Feels like %d°", tempFeelsLikeInt));
    tempFeelsLike.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(tempFeelsLike, new Insets(0, 12, 0, 0));

    ImageView windIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/wind.png")));
    windIcon.setFitHeight(14);
    windIcon.setPreserveRatio(true);

    float windSpeed = currentWeatherData.getWindSpeed();
    Text wind = new Text(String.format(" %.1f km/h", windSpeed));
    wind.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(wind, new Insets(0, 4, 0, 0));

    int windDir = currentWeatherData.getWindDir();
    ImageView windDirectionIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/arrow-thick-top.png")));
    windDirectionIcon.setFitHeight(14);
    windDirectionIcon.setPreserveRatio(true);
    Rotate rotate = new Rotate(windDir, 7, 7);
    windDirectionIcon.getTransforms().add(rotate);

    weatherTempBox.getChildren().addAll(weatherIcon, temp);
    weatherDetail.getChildren().addAll(precipitationIcon, precipitation, tempFeelsLike, windIcon, wind,
        windDirectionIcon);
    currentWeatherStack.getChildren().addAll(currentTime, rowCity, rowCountry, weatherTempBox, desc,
        weatherDetail);
    currentWeatherBox.getChildren().addAll(currentWeatherStack);

    return currentWeatherBox;
  }
  
}
