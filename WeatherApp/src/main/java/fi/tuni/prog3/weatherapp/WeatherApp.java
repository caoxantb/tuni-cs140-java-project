package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import fi.tuni.prog3.weatherapp.models.WeatherData;
import fi.tuni.prog3.weatherapp.services.WeatherDataService;
import java.util.ArrayList;

/**
 * JavaFX Sisu
 */
public class WeatherApp extends Application {

    @Override
    public void start(Stage stage) {
        
        //Creating a new BorderPane.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        //Adding HBox to the center of the BorderPane.
        root.setCenter(getCenterVBox());
        
        //Adding button to the BorderPane and aligning it to the right.
        var quitButton = getQuitButton();
        BorderPane.setMargin(quitButton, new Insets(10, 10, 0, 10));
        root.setBottom(quitButton);
        BorderPane.setAlignment(quitButton, Pos.TOP_RIGHT);
        
        Scene scene = new Scene(root, 500, 700);                      
        stage.setScene(scene);
        stage.setTitle("WeatherApp");
        stage.show();
    }

    public static void main(String[] args) {
        test();
        launch();
    }
    
    private VBox getCenterVBox() {
        //Creating an HBox.
        VBox centerHBox = new VBox(10);
        
        //Adding two VBox to the HBox.
        centerHBox.getChildren().addAll(getTopHBox(), getBottomHBox());
        
        return centerHBox;
    }
    
    private HBox getTopHBox() {
        //Creating a VBox for the left side.
        HBox leftHBox = new HBox();
        leftHBox.setPrefHeight(330);
        leftHBox.setStyle("-fx-background-color: #8fc6fd;");
        
        leftHBox.getChildren().add(new Label("Top Panel"));
        
        return leftHBox;
    }
    
    private HBox getBottomHBox() {
        //Creating a VBox for the right side.
        HBox rightHBox = new HBox();
        rightHBox.setPrefHeight(330);
        rightHBox.setStyle("-fx-background-color: #b1c2d4;");
        
        rightHBox.getChildren().add(new Label("Bottom Panel"));
        
        return rightHBox;
    }
    
    private Button getQuitButton() {
        //Creating a button.
        Button button = new Button("Quit");
        
        //Adding an event to the button to terminate the application.
        button.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        
        return button;
    }
    
    //DEBUGGING APIS
    private static void test(){
    
         WeatherDataService weatherService = new WeatherDataService();

        // Coordinates for a specific location
        float latitude = 44.34f;
        float longitude = 10.99f;

        WeatherData currentWeather = weatherService.getCurrentWeatherData(latitude, longitude);
        if (currentWeather != null) {
            System.out.println("Timestamp: " + currentWeather.getTimestamp());
            System.out.println("Temperature: " + currentWeather.getTemp() + " K");
            System.out.println("Feels Like: " + currentWeather.getTempFeelsLike() + " K");
            System.out.println("Wind Speed: " + currentWeather.getWindSpeed() + " m/s");
            System.out.println("Wind Direction: " + currentWeather.getWindDir() + " degrees");
            System.out.println("Precipitation: " + currentWeather.getPrecipitation() + " mm");
            System.out.println("Latitude: " + currentWeather.getLat());
            System.out.println("Longitude: " + currentWeather.getLon());
            System.out.println("Weather Description: " + currentWeather.getWeatherDesc());
            System.out.println("Icon: " + currentWeather.getIcon());
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
            System.out.println("--------------------------------------");
        }
    }
}