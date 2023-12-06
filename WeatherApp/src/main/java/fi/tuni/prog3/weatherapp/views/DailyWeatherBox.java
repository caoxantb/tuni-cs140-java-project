package fi.tuni.prog3.weatherapp.views;

import fi.tuni.prog3.weatherapp.controllers.WeatherDataController;
import java.util.ArrayList;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DailyWeatherBox {
  int width, height;
  LocationData location;
  ArrayList<WeatherData> dailyWeatherData;
  String unit;

  public DailyWeatherBox(int width, int height, LocationData location, ArrayList<WeatherData> dailyWeatherData,
      String unit) {
    this.width = width;
    this.height = height;
    this.location = location;
    this.dailyWeatherData = dailyWeatherData;
    this.unit = unit;
  }

  public HBox getContent() {
    HBox dailyWeatherBox = new HBox();
    dailyWeatherBox.setPrefWidth(width);
    dailyWeatherBox.setSpacing(10);
    dailyWeatherBox.setPadding(new Insets(20));

    VBox dailyWeatherContainer = new VBox();
    dailyWeatherContainer.setPrefWidth(width);
    dailyWeatherContainer.setSpacing(20);

    Text labelDaily = new Text("1-week Forecast");
    labelDaily.setFont(Font.font("Futura", FontWeight.BOLD, 16));

    HBox dailyWeatherStackContainer = new HBox();
    dailyWeatherStackContainer.setPrefWidth(width);
    dailyWeatherStackContainer.setSpacing(20);

    for (WeatherData dailyWeather : dailyWeatherData) {
      VBox dailyWeatherStack = new VBox();
      dailyWeatherStack.setPrefWidth(width / 8);
      dailyWeatherStack.setSpacing(5);
      dailyWeatherStack.setAlignment(Pos.TOP_CENTER);

      WeatherDataController weatherDataController = new WeatherDataController(dailyWeather, unit);
      Text forecastDay = weatherDataController.getWeatherWeekDay();

      ImageView dailyWeatherIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/weather-91.png")));
      dailyWeatherIcon.setFitHeight(40);
      dailyWeatherIcon.setPreserveRatio(true);

      HBox dailyPos = new HBox();
      dailyPos.setPrefWidth(width / 8);
      dailyPos.setAlignment(Pos.CENTER);

      ImageView dailyPosIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/pos.png")));
      dailyPosIcon.setFitHeight(14);
      dailyPosIcon.setPreserveRatio(true);

      Text dailyPosText = weatherDataController.getWeatherPrecipitation();

      HBox dailyWind = new HBox();
      dailyWind.setPrefWidth(width / 8);
      dailyWind.setAlignment(Pos.CENTER);

      ImageView dailyWindIcon = new ImageView(
          new Image(getClass().getResourceAsStream("/wind.png")));
      dailyWindIcon.setFitHeight(14);
      dailyWindIcon.setPreserveRatio(true);

      Text dailyWindText = weatherDataController.getWeatherWindSpeed();

      ImageView dailyWindDirectionIcon = weatherDataController.getWeatherWindDir();

      Text dailyTempMax = weatherDataController.getWeatherMaxTemp();

      Rectangle dailyTempSpan = new Rectangle(12, 52);
      dailyTempSpan.setFill(Color.BLACK);
      dailyTempSpan.setArcWidth(12);
      dailyTempSpan.setArcHeight(12);
      VBox.setMargin(dailyTempSpan, new Insets(0, 6, 0, 0));

      Text dailyTempMin = weatherDataController.getWeatherMinTemp();
      dailyTempMin.setFont(Font.font("Futura", FontWeight.BOLD, 14));

      dailyPos.getChildren().addAll(dailyPosIcon, dailyPosText);
      dailyWind.getChildren().addAll(dailyWindIcon, dailyWindText, dailyWindDirectionIcon);
      dailyWeatherStack.getChildren().addAll(forecastDay, dailyWeatherIcon, dailyPos, dailyWind, dailyTempMax,
          dailyTempSpan, dailyTempMin);
      dailyWeatherStackContainer.getChildren().addAll(dailyWeatherStack);

    }
    dailyWeatherContainer.getChildren().addAll(labelDaily, dailyWeatherStackContainer);
    dailyWeatherBox.getChildren().addAll(dailyWeatherContainer);

    return dailyWeatherBox;
  }
}
