package fi.tuni.prog3.weatherapp.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The {@code LocationUtils} class provides utility methods for handling location-related operations,
 * such as parsing keys, key-value pairs from JSON files, and retrieving state codes.
 * It offers functionalities to retrieve keys and key-value pairs from JSON files,
 * as well as obtaining an array of state codes.
 */
public class LocationUtils {
    
  /**
   * Default constructor for LocationUtils.
   */  
  public LocationUtils() {
    //
  }

  /**
   * Retrieves keys from a JSON file and returns them as an array of strings.
   *
   * @param filePath The path of the JSON file.
   * @return An array of strings containing keys from the JSON file.
   */
  public String[] getKeysFromFile(String filePath) {
    List<String> keysList = new ArrayList<>();

    try (FileReader fileReader = new FileReader(filePath)) {
      // Parse the JSON file
      JsonElement jsonElement = JsonParser.parseReader(fileReader);

      if (jsonElement.isJsonArray()) {
        // Handle JSON array
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement arrayElement : jsonArray) {
          // Check if the array element is an object
          if (arrayElement.isJsonObject()) {
            JsonObject jsonObject = arrayElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
              String key = entry.getKey();
              keysList.add(key);
            }
          } else {
            System.out.println("Array element is not a JSON object");
          }
        }
      } else {
        System.out.println("Unsupported JSON structure");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    // Convert the list to an array
    return keysList.toArray(new String[0]);
  }

  /**
   * Retrieves key-value pairs from a JSON file and returns them as a map.
   *
   * @param filePath The path of the JSON file.
   * @return A map containing key-value pairs from the JSON file.
   */
  public Map<String, String> getMapFromFile(String filePath) {
    Map<String, String> keyValueMap = new HashMap<>();

    try (FileReader fileReader = new FileReader(filePath)) {
      // Parse the JSON file
      JsonElement jsonElement = JsonParser.parseReader(fileReader);

      if (jsonElement.isJsonArray()) {
        // Handle JSON array
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement arrayElement : jsonArray) {
          // Check if the array element is an object
          if (arrayElement.isJsonObject()) {
            JsonObject jsonObject = arrayElement.getAsJsonObject();

            // Merge the current JsonObject into the main map
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
              // Convert JsonElement to String
              String value = entry.getValue().getAsString();
              keyValueMap.put(entry.getKey(), value);
            }
          } else {
            System.out.println("Array element is not a JSON object");
          }
        }
      } else {
        System.out.println("Unsupported JSON structure");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return keyValueMap;
  }

  /**
   * Returns an array of state codes.
   *
   * @return An array of strings containing state codes.
   */
  public String[] getStatesCode() {
    String[] stateCodes = {
        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
        "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
        "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
        "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    };
    return stateCodes;
  }
}
