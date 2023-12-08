
package fi.tuni.prog3.weatherapp.services;

/**
 * The {@code WeatherDataService} class fetches weather data from the OpenWeatherMap API for a specific location.
 * It retrieves current weather, 3-hourly forecasts for 5 days, and a 7-day daily forecast.
 * This class contains methods to obtain weather information in different time intervals and formats.
 * 
 * @author Ayman Khan
 */

import fi.tuni.prog3.weatherapp.models.WeatherData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherDataService {
    
    // Constants for API access
    private static final String baseUrl = "https://api.openweathermap.org/data/2.5/";
    private static final String apiKey = "d3f1720d73d8b9dbf2a82558b1d79103";
    
    // Default constructor
    public WeatherDataService(){}
      
    /**
     * Retrieves the current weather data for a specific location using latitude and longitude coordinates.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @return WeatherData object representing the current weather conditions.
     */
    public WeatherData getCurrentWeatherData(float lat, float lon) {      
        try
        { 
            String apiUrl = baseUrl + "weather?lat=" +lat+ "&lon=" +lon+ "&appid=" + apiKey;      
            StringBuilder response = fetchDataRequest(apiUrl);
            if (response != null && response.length() > 0) {
                JSONObject json = new JSONObject(response.toString());
                
                int timestamp = json.getInt("dt");
                float windSpeed = (float) json.getJSONObject("wind").getDouble("speed");
                String icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");

                double temp = json.getJSONObject("main").getDouble("temp");
                int timeOffset = json.getInt("timezone");
                double tempFeelsLike = json.getJSONObject("main").getDouble("feels_like");
                int windDir = json.getJSONObject("wind").getInt("deg");
                float precipitation = json.optJSONObject("rain") != null ? json.getJSONObject("rain").optInt("1h") : 0;
                float latValue = (float) json.getJSONObject("coord").getDouble("lat");
                float lonValue = (float) json.getJSONObject("coord").getDouble("lon");
                String weatherDesc = json.getJSONArray("weather").getJSONObject(0).getString("description");
                int sunrise = json.getJSONObject("sys").getInt("sunrise");
                int sunset = json.getJSONObject("sys").getInt("sunset");
                int id = json.getJSONArray("weather").getJSONObject(0).getInt("id");
                
                WeatherData weatherData = new WeatherData(timestamp, timeOffset, temp, tempFeelsLike, windDir,
                        windSpeed, precipitation, latValue, lonValue, weatherDesc, icon, sunrise, sunset, id);

                return weatherData;
                
            }       
            else return null;
        } catch (Exception e) {           
            e.printStackTrace();
            return null;
        }   
    }
    
    /**
     * Retrieves the 3-hourly weather forecast for 5 days based on a location's latitude and longitude.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @return ArrayList containing WeatherData objects representing 3-hourly forecasts.
     */
    public ArrayList<WeatherData> get5day3HourlyForecast(float lat, float lon) {
        
        ArrayList<WeatherData> _3HourlyWeatherForecast = new ArrayList<>();
        
        try
        { 
            String apiUrl = baseUrl + "forecast?lat=" +lat+ "&lon=" +lon+ "&appid=" + apiKey;      
            StringBuilder response = fetchDataRequest(apiUrl);
            if (response != null && response.length() > 0) {
                
                JSONObject json = new JSONObject(response.toString());
                JSONArray forecastList = json.getJSONArray("list");
            
                for (int i = 0; i < 8; i++) {
                    JSONObject forecastObj = forecastList.getJSONObject(i);

                    int timestamp = forecastObj.optInt("dt");
                    int timeOffset = json.getJSONObject("city").optInt("timezone");
                    int sunrise = json.getJSONObject("city").optInt("sunrise");
                    int sunset = json.getJSONObject("city").optInt("sunset");

                    float windSpeed = forecastObj.getJSONObject("wind").optFloat("speed");
                    String icon = forecastObj.getJSONArray("weather").getJSONObject(0).optString("icon");
                    double temp = forecastObj.getJSONObject("main").optDouble("temp");
                    float precipitationPerc = forecastObj.optFloat("pop");
                    int windDir = forecastObj.getJSONObject("wind").optInt("deg");
                    int id = forecastObj.getJSONArray("weather").getJSONObject(0).optInt("id");
                    
                    WeatherData weatherData = new WeatherData(timestamp, timeOffset, temp, windSpeed, icon, precipitationPerc, windDir, id, sunrise, sunset);
                    _3HourlyWeatherForecast.add(weatherData);
                }                      
            }       
        } catch (Exception e) {           
            e.printStackTrace();
        }  
        
        return _3HourlyWeatherForecast;
    }
    
    /**
     * Retrieves a 7-day daily weather forecast based on a location's latitude and longitude.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @return ArrayList containing WeatherData objects representing daily forecasts.
     */
    public ArrayList<WeatherData> getWeeklyForecast(float lat, float lon) {
        
        ArrayList<WeatherData> weeklyWeatherForecast = new ArrayList<>();
        
        try
        { 
            String apiUrl = "https://api.openweathermap.org/data/3.0/onecall?lat=" +lat+ "&lon=" +lon+ "&exclude=current,hourly,minutely&appid=" + apiKey;
            StringBuilder response = fetchDataRequest(apiUrl);
            if (response != null && response.length() > 0) {
                
                JSONObject json = new JSONObject(response.toString());              
                JSONArray dailyForecasts = json.getJSONArray("daily");
            
                for (int i = 0; i < dailyForecasts.length(); i++) {
                    JSONObject dailyForecast = dailyForecasts.getJSONObject(i);

                    int timestamp = dailyForecast.getInt("dt");
                    float windSpeed = dailyForecast.getFloat("wind_speed");
                    String icon = dailyForecast.getJSONArray("weather").getJSONObject(0).getString("icon");
                    float precipitationPerc = dailyForecast.optFloat("pop");
                    double minTemp = dailyForecast.getJSONObject("temp").getDouble("min");
                    double maxTemp = dailyForecast.getJSONObject("temp").getDouble("max");
                    int windDir = dailyForecast.optInt("wind_deg");
                                        int id = dailyForecast.getJSONArray("weather").getJSONObject(0).getInt("id");

                    
                    WeatherData weatherData = new WeatherData(timestamp, windSpeed, icon, precipitationPerc, minTemp, maxTemp, windDir, id);
                    weeklyWeatherForecast.add(weatherData);
                }                        
            }       
        } catch (Exception e) {           
            e.printStackTrace();
        }  
        
        return weeklyWeatherForecast;
    }    
    
    // HELPER FUNCTIONS
    
    private StringBuilder fetchDataRequest(String apiUrl) throws Exception{
        
        StringBuilder response = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
        }
        
        return response;
    }    
}

