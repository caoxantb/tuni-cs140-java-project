package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * JavaFX Sisu
 */
public class WeatherApp extends Application {

  private static final int WINDOW_HEIGHT = 720;
  private static final int WINDOW_WIDTH = 1080;

  @Override
  public void start(Stage stage) {
    Tab main = new Tab("Weather Report");
    Pane mainContent = new Pane();

    VBox currentWeatherBox = new VBox();
    currentWeatherBox.setPrefHeight(WINDOW_HEIGHT / 3);
    currentWeatherBox.setPrefWidth(WINDOW_WIDTH);
    currentWeatherBox.setPadding(new Insets(20, 0, 20, 0));

    VBox currentWeatherStack = new VBox();
    VBox.setMargin(currentWeatherStack, new Insets(10, 0, 0, 0));
    currentWeatherStack.setSpacing(10);
    currentWeatherStack.setAlignment(Pos.TOP_CENTER);
    currentWeatherStack.setPrefWidth(WINDOW_WIDTH);

    HBox rowCity = new HBox();
    rowCity.setPrefWidth(WINDOW_WIDTH);
    rowCity.setAlignment(Pos.CENTER);
    rowCity.setSpacing(5);

    ImageView locationIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/location.png")));
    locationIcon.setFitHeight(32);
    locationIcon.setPreserveRatio(true);

    Text cityName = new Text("Tampere");
    cityName.setFont(Font.font("Futura", FontWeight.BOLD, 32));

    rowCity.getChildren().addAll(locationIcon, cityName);

    Text stateAndCountryName = new Text("Pirkanmaa, Finland");
    stateAndCountryName.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    HBox weatherTempBox = new HBox();
    weatherTempBox.setPrefWidth(WINDOW_WIDTH);
    weatherTempBox.setAlignment(Pos.CENTER);
    weatherTempBox.setSpacing(5);

    ImageView weatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/weather-91.png")));
    weatherIcon.setFitHeight(100);
    weatherIcon.setPreserveRatio(true);

    Text temp = new Text("26°");
    temp.setFont(Font.font("Futura", FontWeight.BOLD, 60));

    Text currentTime = new Text("16:10");
    currentTime.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    Text desc = new Text("Sunny with Cloud");
    desc.setFont(Font.font("Futura", FontWeight.BOLD, 20));

    HBox weatherDetail = new HBox();
    weatherDetail.setPrefWidth(WINDOW_WIDTH);
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
    currentWeatherStack.getChildren().addAll(currentTime, rowCity, stateAndCountryName, weatherTempBox, desc,
        weatherDetail);
    currentWeatherBox.getChildren().addAll(currentWeatherStack);

    // ============================================================================

    HBox hourlyWeatherBox = new HBox();
    hourlyWeatherBox.setPrefHeight(WINDOW_HEIGHT / 3);
    hourlyWeatherBox.setPrefWidth(WINDOW_WIDTH);
    hourlyWeatherBox.setSpacing(10);
    hourlyWeatherBox.setPadding(new Insets(20));

    VBox hourlyWeatherContainer = new VBox();
    hourlyWeatherContainer.setPrefWidth(WINDOW_WIDTH);
    hourlyWeatherContainer.setSpacing(20);

    Text labelHourly = new Text("Hourly Forecast");
    labelHourly.setFont(Font.font("Futura", FontWeight.BOLD, 16));

    HBox hourlyWeatherStackContainer = new HBox();
    hourlyWeatherStackContainer.setPrefWidth(WINDOW_WIDTH);
    hourlyWeatherStackContainer.setSpacing(20);

    VBox hourlyWeatherStack = new VBox();
    hourlyWeatherStack.setPrefWidth((WINDOW_WIDTH - 180) / 8);
    hourlyWeatherStack.setSpacing(5);
    hourlyWeatherStack.setAlignment(Pos.TOP_CENTER);

    Text forecastHour = new Text("17:00");
    forecastHour.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    ImageView hourlyWeatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/weather-91.png")));
    hourlyWeatherIcon.setFitHeight(40);
    hourlyWeatherIcon.setPreserveRatio(true);

    HBox hourlyPos = new HBox();
    hourlyPos.setPrefWidth((WINDOW_WIDTH - 180) / 8);
    hourlyPos.setAlignment(Pos.CENTER);

    ImageView hourlyPosIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/pos.png")));
    hourlyPosIcon.setFitHeight(14);
    hourlyPosIcon.setPreserveRatio(true);

    Text hourlyPosText = new Text(" 17%");
    hourlyPosText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    HBox hourlyWind = new HBox();
    hourlyWind.setPrefWidth((WINDOW_WIDTH - 180) / 8);
    hourlyWind.setAlignment(Pos.CENTER);

    ImageView hourlyWindIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/wind.png")));
    hourlyWindIcon.setFitHeight(14);
    hourlyWindIcon.setPreserveRatio(true);

    Text hourlyWindText = new Text(" 8 km/h");
    hourlyWindText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    VBox.setMargin(hourlyWindText, new Insets(0, 4, 0, 0));

    ImageView hourlyWindDirectionIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/arrow-thick-top.png")));
    hourlyWindDirectionIcon.setFitHeight(14);
    hourlyWindDirectionIcon.setPreserveRatio(true);
    Rotate hourlyRotate = new Rotate(180, 7, 7);
    hourlyWindDirectionIcon.getTransforms().add(hourlyRotate);

    Text hourlyTemp = new Text("29°");
    hourlyTemp.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    VBox.setMargin(hourlyTemp, new Insets(10, 0, 0, 0));

    Rectangle hourlyTempSpan = new Rectangle(12, 52);
    hourlyTempSpan.setFill(Color.BLACK);
    hourlyTempSpan.setArcWidth(12);
    hourlyTempSpan.setArcHeight(12);
    VBox.setMargin(hourlyTempSpan, new Insets(0, 6, 0, 0));

    hourlyPos.getChildren().addAll(hourlyPosIcon, hourlyPosText);
    hourlyWind.getChildren().addAll(hourlyWindIcon, hourlyWindText, hourlyWindDirectionIcon);
    hourlyWeatherStack.getChildren().addAll(forecastHour, hourlyWeatherIcon, hourlyPos, hourlyWind, hourlyTemp,
        hourlyTempSpan);
    hourlyWeatherStackContainer.getChildren().addAll(hourlyWeatherStack);
    hourlyWeatherContainer.getChildren().addAll(labelHourly, hourlyWeatherStackContainer);
    hourlyWeatherBox.getChildren().addAll(hourlyWeatherContainer);

    // ============================================================================

    HBox dailyWeatherBox = new HBox();
    dailyWeatherBox.setPrefHeight(WINDOW_HEIGHT / 3);
    dailyWeatherBox.setPrefWidth(WINDOW_WIDTH);
    dailyWeatherBox.setSpacing(10);
    dailyWeatherBox.setPadding(new Insets(20));

    VBox dailyWeatherContainer = new VBox();
    dailyWeatherContainer.setPrefWidth(WINDOW_WIDTH);
    dailyWeatherContainer.setSpacing(20);

    Text labelDaily = new Text("1-week Forecast");
    labelDaily.setFont(Font.font("Futura", FontWeight.BOLD, 16));

    HBox dailyWeatherStackContainer = new HBox();
    dailyWeatherStackContainer.setPrefWidth(WINDOW_WIDTH);
    dailyWeatherStackContainer.setSpacing(20);

    VBox dailyWeatherStack = new VBox();
    dailyWeatherStack.setPrefWidth((WINDOW_WIDTH - 180) / 8);
    dailyWeatherStack.setSpacing(5);
    dailyWeatherStack.setAlignment(Pos.TOP_CENTER);

    Text forecastDay = new Text("17:00");
    forecastDay.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    ImageView dailyWeatherIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/weather-91.png")));
    dailyWeatherIcon.setFitHeight(40);
    dailyWeatherIcon.setPreserveRatio(true);

    HBox dailyPos = new HBox();
    dailyPos.setPrefWidth((WINDOW_WIDTH - 180) / 8);
    dailyPos.setAlignment(Pos.CENTER);

    ImageView dailyPosIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/pos.png")));
    dailyPosIcon.setFitHeight(14);
    dailyPosIcon.setPreserveRatio(true);

    Text dailyPosText = new Text(" 17%");
    dailyPosText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    HBox dailyWind = new HBox();
    dailyWind.setPrefWidth((WINDOW_WIDTH - 180) / 8);
    dailyWind.setAlignment(Pos.CENTER);

    ImageView dailyWindIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/wind.png")));
    dailyWindIcon.setFitHeight(14);
    dailyWindIcon.setPreserveRatio(true);

    Text dailyWindText = new Text(" 8 km/h");
    dailyWindText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
    VBox.setMargin(hourlyWindText, new Insets(0, 4, 0, 0));

    ImageView dailyWindDirectionIcon = new ImageView(
        new Image(getClass().getResourceAsStream("/arrow-thick-top.png")));
    dailyWindDirectionIcon.setFitHeight(14);
    dailyWindDirectionIcon.setPreserveRatio(true);
    Rotate dailyRotate = new Rotate(180, 7, 7);
    dailyWindDirectionIcon.getTransforms().add(dailyRotate);

    Text dailyTempMax = new Text("31°");
    dailyTempMax.setFont(Font.font("Futura", FontWeight.BOLD, 14));
    VBox.setMargin(dailyTempMax, new Insets(10, 0, 0, 0));

    Rectangle dailyTempSpan = new Rectangle(12, 52);
    dailyTempSpan.setFill(Color.BLACK);
    dailyTempSpan.setArcWidth(12);
    dailyTempSpan.setArcHeight(12);
    VBox.setMargin(dailyTempSpan, new Insets(0, 6, 0, 0));

    Text dailyTempMin = new Text("21°");
    dailyTempMin.setFont(Font.font("Futura", FontWeight.BOLD, 14));

    dailyPos.getChildren().addAll(dailyPosIcon, dailyPosText);
    dailyWind.getChildren().addAll(dailyWindIcon, dailyWindText, dailyWindDirectionIcon);
    dailyWeatherStack.getChildren().addAll(forecastDay, dailyWeatherIcon, dailyPos, dailyWind, dailyTempMax,
        dailyTempSpan, dailyTempMin);
    dailyWeatherStackContainer.getChildren().addAll(dailyWeatherStack);
    dailyWeatherContainer.getChildren().addAll(labelDaily, dailyWeatherStackContainer);
    dailyWeatherBox.getChildren().addAll(dailyWeatherContainer);

    VBox container = new VBox();
    container.getChildren().addAll(currentWeatherBox, hourlyWeatherBox, dailyWeatherBox);
    container.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(242, 226, 186, 0.7), transparent);");

    mainContent.getChildren().add(container);
    main.setContent(mainContent);

    Tab search = new Tab("Search and History");
    TabPane tabpane = new TabPane(main, search);

    // // Creating a new BorderPane.
    // BorderPane root = new BorderPane();
    // root.setPadding(new Insets(10, 10, 10, 10));

    // // Adding HBox to the center of the BorderPane.
    // root.setCenter(getCenterVBox())

    // Adding button to the TabPane and aligning it to the right.
    // var quitButton = getQuitButton();
    // BorderPane.setMargin(quitButton, new Insets(10, 10, 0, 10));
    // main.setBottom(quitButton);
    // BorderPane.setAlignment(quitButton, Pos.TOP_RIGHT);

    ScrollPane scrollPane = new ScrollPane(tabpane);
    scrollPane.setPrefSize(1080, 720);
    scrollPane.setFitToWidth(true);

    Scene scene = new Scene(scrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Weather Application");
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

  private VBox getCenterVBox() {
    // Creating an HBox.
    VBox centerHBox = new VBox(10);

    // Adding two VBox to the HBox.
    centerHBox.getChildren().addAll(getTopHBox(), getBottomHBox());

    return centerHBox;
  }

  private HBox getTopHBox() {
    // Creating a VBox for the left side.
    HBox leftHBox = new HBox();
    leftHBox.setPrefHeight(330);
    leftHBox.setStyle("-fx-background-color: #8fc6fd;");

    leftHBox.getChildren().add(new Label("Top Panel"));

    return leftHBox;
  }

  private HBox getBottomHBox() {
    // Creating a VBox for the right side.
    HBox rightHBox = new HBox();
    rightHBox.setPrefHeight(330);
    rightHBox.setStyle("-fx-background-color: #b1c2d4;");

    rightHBox.getChildren().add(new Label("Bottom Panel"));

    return rightHBox;
  }

  private Button getQuitButton() {
    // Creating a button.
    Button button = new Button("Quit");

    // Adding an event to the button to terminate the application.
    button.setOnAction((ActionEvent event) -> {
      Platform.exit();
    });

    return button;
  }
}