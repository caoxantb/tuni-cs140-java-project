package fi.tuni.prog3.weatherapp.views;

import java.util.ArrayList;
import java.util.Collections;

import fi.tuni.prog3.weatherapp.models.LocationData;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SearchContent {
  ArrayList<LocationData> searchResults, history, favorites;
  int width;
  EventHandler<MouseEvent> searchEvent;
  VBox searchBox;

  public SearchContent(int width, VBox searchBox, ArrayList<LocationData> searchResults,
      ArrayList<LocationData> history, ArrayList<LocationData> favorites,
      EventHandler<MouseEvent> searchEvent) {
    this.width = width;
    this.searchResults = searchResults;
    this.history = history;
    this.favorites = favorites;
    this.searchEvent = searchEvent;
    this.searchBox = searchBox;
  }

  public ScrollPane getContent() {
    VBox searchResultsBox = new VBox(10);
    VBox historyBox = new VBox(10);
    VBox favoritesBox = new VBox(10);

    ArrayList<LocationData> reversedHistory = new ArrayList<>(history);
    Collections.reverse(reversedHistory);

    getPanel(searchResultsBox, searchResults, "top right", "Search Results", "search");
    getPanel(favoritesBox, favorites, "bottom left", "Favorites", "favorite");
    getPanel(historyBox, reversedHistory, "top left", "History", "history");

    VBox firstPanel = new VBox(searchBox, searchResultsBox);

    ScrollPane favoritePane = new ScrollPane(favoritesBox);
    favoritePane.setPrefSize(1280 / 2, 720 / 2);
    favoritePane.setFitToWidth(true);
    ScrollPane historyPane = new ScrollPane(historyBox);
    historyPane.setPrefSize(1280 / 2, 720 / 2);
    historyPane.setFitToWidth(true);
    VBox secondPanel = new VBox(favoritePane, historyPane);

    HBox all = new HBox(firstPanel, secondPanel);
    ScrollPane nonScrollPane = new ScrollPane(all);
    nonScrollPane.setPrefSize(1280, 720);
    nonScrollPane.setFitToWidth(true);
    nonScrollPane.setFitToHeight(true);

    return nonScrollPane;
  }

  private void getPanel(VBox box, ArrayList<LocationData> dataList, String direction, String labelName, String icon) {
    box.setStyle(String.format("-fx-background-color: linear-gradient(to %s, rgba(196, 196, 196, 0.7), transparent);",
        direction));
    Text label = new Text(labelName);
    box.setPadding(new Insets(10));
    label.setFont(Font.font("Futura", FontWeight.BOLD, 20));
    box.getChildren().add(label);
    box.setPrefWidth(1280 / 2);
    box.setPrefHeight(720 / 2);

    for (LocationData data : dataList) {
      String text = data.getState().equals("") ? String.format("%s, %s", data.getCity(), data.getCountry())
          : String.format("%s, %s, %s", data.getCity(), data.getState(), data.getCountry());
      HBox searchTabBox = new HBox(5);
      searchTabBox.setPrefWidth(width);
      searchTabBox.getProperties().put("location", data);
      searchTabBox.setOnMouseClicked(searchEvent);
      searchTabBox.setAlignment(Pos.CENTER_LEFT);

      ImageView boxIcon = new ImageView(
          new Image(getClass().getResourceAsStream(String.format("/label-icons/%s.png", icon))));
      boxIcon.setFitHeight(14);
      boxIcon.setPreserveRatio(true);

      if (searchTabBox.getChildren().size() == 0) {
        Text searchTabText = new Text(text);
        searchTabText.setFont(Font.font("Futura", FontWeight.NORMAL, 14));
        searchTabBox.getChildren().addAll(boxIcon, searchTabText);
      }
      box.getChildren().add(searchTabBox);
    }
  }
}
