package fi.tuni.prog3.weatherapp.controllers;

import java.io.IOException;
import java.net.URL;

import fi.tuni.prog3.weatherapp.models.LocationData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Controller class responsible for managing and handling LocationData in the UI.
 * Contains methods to retrieve specific details of a location for display purposes.
 * 
 * @author Xuan-An Cao
 */
public class LocationDataController {
    LocationData location;

    /**
     * Constructs a LocationDataController object with the specified LocationData.
     * 
     * @param location The LocationData object to be managed by this controller.
     */
    public LocationDataController(LocationData location) {
        this.location = location;
    }

    /**
     * Retrieves the city name of the searched location.
     * 
     * @return A Text object representing the city name.
     */
    public Text getSearchedLocationCity() {
        Text cityName = new Text(location.getCity());
        cityName.setFont(Font.font("Futura", FontWeight.BOLD, 32));
        return cityName;
    }

    /**
     * Retrieves the country flag image of the searched location.
     * 
     * @return An ImageView representing the country flag image.
     * @throws IOException If an error occurs during the retrieval of the flag image.
     */
    public ImageView getSearchedLocationFlag() throws IOException {
        // TO-DO
        ImageView countryFlag = new ImageView(
                new Image(new URL(location.getFlag()).openStream()));
        countryFlag.setFitHeight(14);
        countryFlag.setPreserveRatio(true);
        return countryFlag;
    }

    /**
     * Retrieves the country and state/region name of the searched location.
     * 
     * @return A Text object representing the country and state/region name.
     */
    public Text getSearchedLocationCountryAndState() {
        String country = location.getCountry();
        String state = location.getState();
        String res = !state.equals("") ? String.format("%s, %s", state, country) : country;
        Text stateAndCountryName = new Text(res);
        stateAndCountryName.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
        return stateAndCountryName;
    }
}
