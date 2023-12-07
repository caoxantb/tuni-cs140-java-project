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

public class LocationUtils {
  public LocationUtils() {
    //
  }

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
