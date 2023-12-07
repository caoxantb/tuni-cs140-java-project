package fi.tuni.prog3.weatherapp.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.services.LocationDataService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SearchContent {
  ArrayList<LocationData> history, favorites;
  int width;
  EventHandler<MouseEvent> searchEvent;

  public SearchContent(int width, ArrayList<LocationData> history, ArrayList<LocationData> favorites,
      EventHandler<MouseEvent> searchEvent) {
    this.width = width;
    this.history = history;
    this.favorites = favorites;
    this.searchEvent = searchEvent;
    // this.latestSearchLocation = latestSearchLocation;
    // this.locationDataService = locationDataService;
  }

  public HBox getContent() {
    VBox searchBox = new VBox(10);
    VBox searchResultsBox = new VBox(10);
    VBox historyBox = new VBox(10);
    VBox favoritesBox = new VBox(10);

    searchBox
        .setStyle("-fx-background-color: linear-gradient(to bottom right, rgba(196, 196, 196, 0.7), transparent);");
    Text labelSearch = new Text("Search");
    searchBox.setPadding(new Insets(10));
    labelSearch.setFont(Font.font("Futura", FontWeight.BOLD, 16));
    searchBox.getChildren().add(labelSearch);
    searchBox.setPrefWidth(1080 / 2);
    searchBox.setPrefHeight(720 / 2);

    searchResultsBox
        .setStyle("-fx-background-color: linear-gradient(to top right, rgba(196, 196, 196, 0.7), transparent);");
    Text labelSearchResults = new Text("Search Results");
    searchResultsBox.setPadding(new Insets(10));
    labelSearchResults.setFont(Font.font("Futura", FontWeight.BOLD, 16));
    searchResultsBox.getChildren().add(labelSearchResults);
    searchResultsBox.setPrefWidth(1080 / 2);
    searchResultsBox.setPrefHeight(720 / 2);

    favoritesBox
        .setStyle("-fx-background-color: linear-gradient(to bottom left, rgba(196, 196, 196, 0.7), transparent);");
    Text labelFavorite = new Text("Favorites");
    favoritesBox.setPadding(new Insets(10));
    labelFavorite.setFont(Font.font("Futura", FontWeight.BOLD, 16));
    favoritesBox.getChildren().add(labelFavorite);
    favoritesBox.setPrefWidth(1080 / 2);
    favoritesBox.setPrefHeight(720 / 2);

    for (LocationData data : favorites) {
      String text = data.getState().equals("") ? String.format("%s, %s", data.getCity(), data.getCountry())
          : String.format("%s, %s, %s", data.getCity(), data.getState(), data.getCountry());
      HBox searchTabBox = new HBox();
      searchTabBox.setPrefWidth(width);
      searchTabBox.getProperties().put("location", data);
      searchTabBox.setOnMouseClicked(searchEvent);
      if (searchTabBox.getChildren().size() == 0) {
        Text searchTabText = new Text(text);
        searchTabText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
        searchTabBox.getChildren().add(searchTabText);
      }
      favoritesBox.getChildren().add(searchTabBox);
    }

    historyBox
        .setStyle("-fx-background-color: linear-gradient(to top left, rgba(196, 196, 196, 0.7), transparent);");
    Text labelHistory = new Text("History");
    historyBox.setPadding(new Insets(10));
    labelHistory.setFont(Font.font("Futura", FontWeight.BOLD, 16));
    historyBox.getChildren().add(labelHistory);
    historyBox.setPrefWidth(1080 / 2);
    historyBox.setPrefHeight(720 / 2);

    ArrayList<LocationData> reversedHistory = new ArrayList<>(history);
    Collections.reverse(reversedHistory);

    for (LocationData data : reversedHistory) {
      String text = data.getState().equals("") ? String.format("%s, %s", data.getCity(), data.getCountry())
          : String.format("%s, %s, %s", data.getCity(), data.getState(), data.getCountry());
      HBox searchTabBox = new HBox();
      searchTabBox.setPrefWidth(width);
      searchTabBox.getProperties().put("location", data);
      searchTabBox.setOnMouseClicked(searchEvent);
      if (searchTabBox.getChildren().size() == 0) {
        Text searchTabText = new Text(text);
        searchTabText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
        searchTabBox.getChildren().add(searchTabText);
      }
      historyBox.getChildren().add(searchTabBox);
    }

    VBox firstPanel = new VBox(searchBox, searchResultsBox);
    VBox secondPanel = new VBox(favoritesBox, historyBox);
    HBox all = new HBox(firstPanel, secondPanel);
    return all;
  }
}
