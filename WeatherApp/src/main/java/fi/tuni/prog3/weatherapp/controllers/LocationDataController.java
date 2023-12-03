/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp.controllers;

import java.io.IOException;
import java.net.URL;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.services.LocationDataService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Xuan-An Cao
 */
public class LocationDataController {
  LocationDataService locationDataService;
  LocationData location;

  public LocationDataController(LocationDataService locationDataService) {
    this.locationDataService = locationDataService;
    this.location = locationDataService.getCurrentLocation();
  }

  public Text getSearchedLocationCity() {
    Text cityName = new Text(location.getCity());
    cityName.setFont(Font.font("Futura", FontWeight.BOLD, 32));

    return cityName;
  }

  public ImageView getSearchedLocationFlag() throws IOException {
    // TO-DO
    ImageView countryFlag = new ImageView(
        new Image(new URL(location.getFlag()).openStream()));
    countryFlag.setFitHeight(14);
    countryFlag.setPreserveRatio(true);

    return countryFlag;
  }

  public Text getSearchedLocationCountryAndState() {
    String country = location.getCountry();
    String state = location.getState();
    String res = !state.equals("") ? String.format("%s, %s", state, country) : country;
    Text stateAndCountryName = new Text(res);
    stateAndCountryName.setFont(Font.font("Futura", FontWeight.NORMAL, 14));

    return stateAndCountryName;
  }

}
