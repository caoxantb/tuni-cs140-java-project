
package fi.tuni.prog3.weatherapp.services;

/**
 * Service class responsible for handling location data, querying APIs, and managing file operations.
 * Implements functionality to interact with location information, favorites, history, and file I/O.
 * 
 * @author Xuan-An Cao
 */

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.interfaces.iReadAndWriteToFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Xuan-An Cao
 */
public class LocationDataService implements iReadAndWriteToFile {
  private static final String API_KEY = "d3f1720d73d8b9dbf2a82558b1d79103";
  private static final String LOCATION_API_PREFIX = "https://api.openweathermap.org/geo/1.0/direct?q=";
  private static final String LOCATION_API_SUFFIX = "&limit=5&appid=";
  private static final String COUNTRY_API_PREFIX = "https://restcountries.com/v3.1/alpha/";
  private static final String FAVORITE_PATH = "./json/favorites.json";
  private static final String HISTORY_PATH = "./json/history.json";

  // Constants and fields...

    /**
     * Constructs a LocationDataService object.
     */
  public LocationDataService() {
    //
  }

  // Methods for API calls, handling responses, file operations, and data manipulation
  
  private String getLocationRequest(String apiUrl) throws IOException {
    URL url = new URL(apiUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    int responseCode = connection.getResponseCode();

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();
      connection.disconnect();
      return response.toString();
    }
    throw new IOException("Failed to retrieve data from the API. Response code: " + responseCode);
  }

  private ArrayList<LocationData> handleLocationResponse(String response, String cityQuery) {
    ArrayList<LocationData> searchedLocations = new ArrayList<>();
    Gson gson = new Gson();
    JsonArray jsonResponse = gson.fromJson(response.toString(), JsonArray.class);
    jsonResponse.forEach(element -> {
      JsonObject location = element.getAsJsonObject();
      String city = location.get("name").getAsString();
      if (!city.toLowerCase().equals(cityQuery.toLowerCase()))
        return;
      String countryCode = location.get("country").getAsString();
      JsonElement stateElement = location.get("state");
      String state = (stateElement != null && !stateElement.isJsonNull()) ? stateElement.getAsString() : "";
      float lat = location.get("lat").getAsFloat();
      float lon = location.get("lon").getAsFloat();
      String id = String.format("%f&%f", lat, lon);
      try {
        String countryApiUrl = String.format("%s%s", COUNTRY_API_PREFIX, countryCode);
        String countryJson = getLocationRequest(countryApiUrl);
        JsonObject countryJsonObject = gson.fromJson(countryJson.toString(), JsonArray.class).get(0).getAsJsonObject();
        String country = countryJsonObject.getAsJsonObject("name").get("common").getAsString();
        String flag = countryJsonObject.getAsJsonObject("flags").get("png").getAsString();

        searchedLocations.add(new LocationData(id, city, country, countryCode, state, flag, lat, lon));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    ArrayList<LocationData> filteredLocations = searchedLocations.stream()
        .distinct()
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    return filteredLocations;
  }
  
  // Methods for reading from and writing to files
  
  /**
     * Reads data from a JSON file located at the given file path.
     *
     * @param filePath The path of the JSON file to be read.
     * @return The JSON data read from the file as a JsonArray.
     */
  public JsonArray readFromFile(String filePath) {
    try (FileReader fileReader = new FileReader(filePath)) {
      JsonElement jsonElement = JsonParser.parseReader(fileReader);

      if (jsonElement.isJsonArray()) {
        return jsonElement.getAsJsonArray();
      } else {
        throw new IllegalStateException("The file does not contain a JSON array.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new JsonArray();
    }
  }

  /**
     * Writes the provided JSON data to a file at the specified file path.
     *
     * @param filePath The path where the JSON data will be written.
     * @param data     The JSON data to be written to the file as a JsonArray.
     */
  public void writeToFile(String filePath, JsonArray data) {
    // Create a Gson object
    Gson gson = new Gson();

    try (FileWriter fileWriter = new FileWriter(filePath)) {
      gson.toJson(data, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

   /**
     * Queries the location based on the provided city.
     * 
     * @param city The name of the city to query.
     * @return An ArrayList of LocationData objects based on the city query.
     * @throws IOException If an error occurs during the API request.
     */
  public ArrayList<LocationData> queryLocation(String city) throws IOException {
    String locationApiUrl = String.format(
        "%s%s%s%s", LOCATION_API_PREFIX, city, LOCATION_API_SUFFIX, API_KEY);
    String response = getLocationRequest(locationApiUrl);
    ArrayList<LocationData> searchedLocations = handleLocationResponse(response, city);
    return searchedLocations;
  }

  /**
     * Queries the location based on the provided city and country.
     * 
     * @param city    The name of the city to query.
     * @param country The name of the country associated with the city.
     * @return An ArrayList of LocationData objects based on the city and country query.
     * @throws IOException If an error occurs during the API request.
     */
  public ArrayList<LocationData> queryLocation(String city, String country) throws IOException {
    String locationApiUrl = String.format(
        "%s%s,%s%s%s", LOCATION_API_PREFIX, city, country, LOCATION_API_SUFFIX, API_KEY);
    String response = getLocationRequest(locationApiUrl);
    ArrayList<LocationData> searchedLocations = handleLocationResponse(response, city);
    return searchedLocations;
  }

   /**
     * Queries the location based on the provided city, country, and state.
     * 
     * @param city    The name of the city to query.
     * @param country The name of the country associated with the city.
     * @param state   The name of the state or region associated with the city.
     * @return An ArrayList of LocationData objects based on the city, country, and state query.
     * @throws IOException If an error occurs during the API request.
     */
  public ArrayList<LocationData> queryLocation(String city, String country, String state) throws IOException {
    String locationApiUrl = String.format(
        "%s%s,%s,%s%s%s", LOCATION_API_PREFIX, city, country, state, LOCATION_API_SUFFIX, API_KEY);
    String response = getLocationRequest(locationApiUrl);
    ArrayList<LocationData> searchedLocations = handleLocationResponse(response, city);
    return searchedLocations;
  }

   // Methods for managing favorite locations
  
  /**
     * Retrieves all the favorite locations stored in the file.
     *
     * @return An ArrayList of LocationData containing all the favorite locations.
     */
  public ArrayList<LocationData> getAllFavoriteLocations() {
    Gson gson = new Gson();

    JsonArray favoriteLocationsAsJsonArray = readFromFile(FAVORITE_PATH);
    ArrayList<LocationData> favoriteLocations = new ArrayList<>();

    favoriteLocationsAsJsonArray.forEach(jsonElement -> {
      LocationData location = gson.fromJson(jsonElement, LocationData.class);
      favoriteLocations.add(location);
    });

    return favoriteLocations;
  }

  /**
     * Adds a new location to the favorite locations.
     *
     * @param newLocation The LocationData object to be added as a favorite.
     */
  public void addFavoriteLocation(LocationData newLocation) {
    Gson gson = new Gson();
    File file = new File(FAVORITE_PATH);

    if (!file.exists()) {
      JsonArray initialData = new JsonArray();
      writeToFile(FAVORITE_PATH, initialData);
    }

    JsonArray favoriteLocations = readFromFile(FAVORITE_PATH);
    JsonElement newLocationAsJsonElement = gson.toJsonTree(newLocation);

    favoriteLocations.add(newLocationAsJsonElement);
    writeToFile(FAVORITE_PATH, favoriteLocations);
  }

  /**
     * Removes a favorite location by ID.
     *
     * @param id The ID of the location to be removed.
     */
  public void removeFavoriteLocation(String id) {
    JsonArray favoriteLocations = readFromFile(FAVORITE_PATH);
    JsonArray updatedFavoriteLocations = new JsonArray();
    favoriteLocations.forEach(location -> {
      JsonObject locationObject = location.getAsJsonObject();
      String locationId = locationObject.get("id").getAsString();
      if (!locationId.equals(id)) {
        updatedFavoriteLocations.add(locationObject);
      }
    });
    writeToFile(FAVORITE_PATH, updatedFavoriteLocations);
  }

  /**
     * Checks if a location with a given ID is marked as a favorite.
     *
     * @param id The ID of the location to check for as a favorite.
     * @return True if the location is marked as a favorite, otherwise false.
     */
  public boolean isFavoriteLocation(String id) {
    JsonArray favoriteLocations = readFromFile(FAVORITE_PATH);
    AtomicBoolean isFavorite = new AtomicBoolean(false);
    favoriteLocations.forEach(location -> {
      JsonObject locationObject = location.getAsJsonObject();
      String locationId = locationObject.get("id").getAsString();
      if (locationId.equals(id)) {
        isFavorite.set(true);
        ;
      }
    });

    return isFavorite.get();
  }

  // Methods for managing history
  
  /**
     * Adds a new location to the search history.
     *
     * @param newLocation The LocationData object to be added to the history.
     */
  public void addToHistory(LocationData newLocation) {
    Gson gson = new Gson();
    File file = new File(HISTORY_PATH);

    if (!file.exists()) {
      JsonArray initialData = new JsonArray();
      writeToFile(HISTORY_PATH, initialData);
    }

    JsonArray history = readFromFile(HISTORY_PATH);
    JsonElement newlySearchElement = gson.toJsonTree(newLocation);

    JsonArray updatedHistoryLocations = new JsonArray();

    history.forEach(location -> {
      JsonObject locationObject = location.getAsJsonObject();
      String locationId = locationObject.get("id").getAsString();
      if (!locationId.equals(newLocation.getId())) {
        updatedHistoryLocations.add(locationObject);
      }
    });

    updatedHistoryLocations.add(newlySearchElement);

    writeToFile(HISTORY_PATH, updatedHistoryLocations);
  }

  /**
     * Retrieves the search history containing previously searched locations.
     *
     * @return An ArrayList of LocationData containing the search history.
     */
  public ArrayList<LocationData> getHistory() {
    Gson gson = new Gson();
    JsonArray historyAsJsonArray = readFromFile(HISTORY_PATH);
    ArrayList<LocationData> historyLocations = new ArrayList<>();

    historyAsJsonArray.forEach(jsonElement -> {
      LocationData location = gson.fromJson(jsonElement, LocationData.class);
      historyLocations.add(location);
    });

    return historyLocations;
  }

  //Utility Methods
  
   /**
     * Retrieves the latest searched location from the history.
     *
     * @return The LocationData of the latest searched location.
     */
  public LocationData getCurrentLocation() {
    Gson gson = new Gson();
    JsonArray history = readFromFile(HISTORY_PATH);
    JsonElement lastElement = history.get(history.size() - 1);
    LocationData latestSearchLocation = gson.fromJson(lastElement, LocationData.class);

    return latestSearchLocation;
  }
}
