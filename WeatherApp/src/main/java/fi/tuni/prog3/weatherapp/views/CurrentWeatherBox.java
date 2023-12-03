package fi.tuni.prog3.weatherapp.views;

import fi.tuni.prog3.weatherapp.controllers.LocationDataController;
import fi.tuni.prog3.weatherapp.services.LocationDataService;
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
  LocationDataService locationDataService;

  public CurrentWeatherBox(int width, int height, LocationDataService locationDataService) {
    this.width = width;
    this.height = height;
    this.locationDataService = locationDataService;
  }

  public HBox getContent() throws IOException {
    LocationDataController locationDataController = new LocationDataController(locationDataService);

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

    //TO-DO
    Text cityName = locationDataController.getSearchedLocationCity();

    rowCity.getChildren().addAll(locationIcon, cityName);

    HBox rowCountry = new HBox();
    rowCountry.setPrefWidth(width);
    rowCountry.setAlignment(Pos.CENTER);
    rowCountry.setSpacing(5);

    //TO-DO
    ImageView countryFlag = locationDataController.getSearchedLocationFlag();

    //TO-DO
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

    Text temp = new Text("26°C");
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 60));

    Text currentTime = new Text("16:10");
    currentTime.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    Text desc = new Text("Sunny with Cloud");
    desc.setFont(Font.font("Futura", FontWeight.BOLD, 20));

    HBox weatherDetail = new HBox();
    weatherDetail.setPrefWidth(width);
    weatherDetail.setAlignment(Pos.CENTER);

    ImageView precipitationIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/precipitation.png")));
    precipitationIcon.setFitHeight(14);
    precipitationIcon.setPreserveRatio(true);

    Text precipitation = new Text(" 0 mm/h");
    precipitation.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(precipitation, new Insets(0, 20, 0, 0));

    Text tempFeelsLike = new Text("Feels like 28°");
    tempFeelsLike.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(tempFeelsLike, new Insets(0, 12, 0, 0));

    ImageView windIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/wind.png")));
    windIcon.setFitHeight(14);
    windIcon.setPreserveRatio(true);

    Text wind = new Text(" 5 km/h");
    wind.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    HBox.setMargin(wind, new Insets(0, 4, 0, 0));

    ImageView windDirectionIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/arrow-thick-top.png")));
    windDirectionIcon.setFitHeight(14);
    windDirectionIcon.setPreserveRatio(true);
    Rotate rotate = new Rotate(180, 7, 7);
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
