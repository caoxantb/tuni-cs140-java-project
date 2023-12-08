
package fi.tuni.prog3.weatherapp.services;

import fi.tuni.prog3.weatherapp.models.LocationData;
import fi.tuni.prog3.weatherapp.models.WeatherData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WeatherDataServiceTest {

    @Test
    public void testGetCurrentWeatherData() {
        
        LocationDataService locationDataService = new LocationDataService();
        WeatherDataService weatherDataService = new WeatherDataService();

        LocationData latestSearchLocation = locationDataService.getCurrentLocation();
        float latitude = latestSearchLocation.getLat();
        float longitude = latestSearchLocation.getLon();
        WeatherData weatherData = weatherDataService.getCurrentWeatherData(latitude, longitude);
        
        assertNotNull(weatherData);
        
        // Check if the weatherData object has non-null values
        assertNotNull(weatherData.getTimestamp());
        assertNotNull(weatherData.getTimeOffset());
        assertNotNull(weatherData.getTemp());
        assertNotNull(weatherData.getTempFeelsLike());
        assertNotNull(weatherData.getWindDir());
        assertNotNull(weatherData.getWindSpeed());
        assertNotNull(weatherData.getPrecipitation());
        assertNotNull(weatherData.getLat());
        assertNotNull(weatherData.getLon());
        assertNotNull(weatherData.getWeatherDesc());
        assertNotNull(weatherData.getIcon());
        assertNotNull(weatherData.getSunrise());
        assertNotNull(weatherData.getSunset());
        assertNotNull(weatherData.getId());

        // Check if timestamp is a positive value
        assertTrue(weatherData.getTimestamp() > 0);
        assertTrue(weatherData.getSunset() > 0);
        assertTrue(weatherData.getSunrise() > 0);

        // Check temperature ranges
        assertTrue(weatherData.getTemp() >= -273); // In Celsius, absolute zero is -273°C
        assertTrue(weatherData.getTempFeelsLike() >= -273);
        assertTrue(weatherData.getMinTemp() >= -273);
        assertTrue(weatherData.getMaxTemp() >= -273);

        // Check wind direction is within the range [0, 360]
        assertTrue(weatherData.getWindDir() >= 0 && weatherData.getWindDir() <= 360);

        // Check latitudes and longitudes are within valid ranges
        assertTrue(weatherData.getLat() >= -90 && weatherData.getLat() <= 90);
        assertTrue(weatherData.getLon() >= -180 && weatherData.getLon() <= 180);
        assertTrue((int)weatherData.getLat() == (int)latitude);
        assertTrue((int)weatherData.getLon() == (int)longitude);
        
        // Check wind speed and precipitation are non-negative
        assertTrue(weatherData.getWindSpeed() >= 0);
        assertTrue(weatherData.getPrecipitation() >= 0);
        
    }

    @Test
    public void testGet5day3HourlyForecast() {
        
        LocationDataService locationDataService = new LocationDataService();
        WeatherDataService weatherDataService = new WeatherDataService();

        LocationData latestSearchLocation = locationDataService.getCurrentLocation();
        float latitude = latestSearchLocation.getLat();
        float longitude = latestSearchLocation.getLon();
                
        ArrayList<WeatherData> weatherDataList = weatherDataService.get5day3HourlyForecast(latitude, longitude);
        assertNotNull(weatherDataList);
        
        assertFalse(weatherDataList.isEmpty());
    
        for (WeatherData weatherData : weatherDataList) {
            assertNotNull(weatherData);

            // Check if the weatherData object has non-null values
            assertNotNull(weatherData.getTimestamp());
            assertNotNull(weatherData.getTemp());
            assertNotNull(weatherData.getWindSpeed());
            assertNotNull(weatherData.getPrecipitation());
            assertNotNull(weatherData.getIcon());
            assertNotNull(weatherData.getWindDir());
            assertNotNull(weatherData.getTimeOffset());
            assertNotNull(weatherData.getId());

            
            // Check if timestamp is a positive value
            assertTrue(weatherData.getTimestamp() > 0);

            // Check temperature ranges
            assertTrue(weatherData.getTemp() >= -273); // In Celsius, absolute zero is -273°C
            
            // Check latitudes and longitudes are within valid ranges
            assertTrue(weatherData.getLat() >= -90 && weatherData.getLat() <= 90);
            assertTrue(weatherData.getLon() >= -180 && weatherData.getLon() <= 180);
            
            // Check wind speed and precipitation are non-negative
            assertTrue(weatherData.getWindSpeed() >= 0);
            assertTrue(weatherData.getPrecipitation() >= 0);            
        }
    }

    @Test
    public void testGetWeeklyForecast() {
        
        LocationDataService locationDataService = new LocationDataService();
        WeatherDataService weatherDataService = new WeatherDataService();

        LocationData latestSearchLocation = locationDataService.getCurrentLocation();
        float latitude = latestSearchLocation.getLat();
        float longitude = latestSearchLocation.getLon();
        
        ArrayList<WeatherData> weatherDataList = weatherDataService.getWeeklyForecast(latitude, longitude);
        assertNotNull(weatherDataList);
        
        assertFalse(weatherDataList.isEmpty());
    
        for (WeatherData weatherData : weatherDataList) {
            assertNotNull(weatherData);

            // Check if the weatherData object has non-null values
            assertNotNull(weatherData.getTimestamp());
            assertNotNull(weatherData.getMinTemp());
            assertNotNull(weatherData.getMaxTemp());
            assertNotNull(weatherData.getWindSpeed());
            assertNotNull(weatherData.getPrecipitation());
            assertNotNull(weatherData.getIcon());
            assertNotNull(weatherData.getWindDir());
            assertNotNull(weatherData.getId());

            // Check if timestamp is a positive value
            assertTrue(weatherData.getTimestamp() > 0);

            // Check temperature ranges
            assertTrue(weatherData.getMinTemp() >= -273); // In Celsius, absolute zero is -273°C
            assertTrue(weatherData.getMaxTemp() >= -273);
            
            // Check latitudes and longitudes are within valid ranges
            assertTrue(weatherData.getLat() >= -90 && weatherData.getLat() <= 90);
            assertTrue(weatherData.getLon() >= -180 && weatherData.getLon() <= 180);
            
            // Check wind speed and precipitation are non-negative
            assertTrue(weatherData.getWindSpeed() >= 0);
            assertTrue(weatherData.getPrecipitation() >= 0);            
        }
    }
}

