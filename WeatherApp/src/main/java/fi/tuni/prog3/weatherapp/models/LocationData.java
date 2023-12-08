package fi.tuni.prog3.weatherapp.models;

import java.util.Objects;

/**
 * Represents geographical location data including city, country, state, and coordinates.
 * This class provides methods to access and manage location information.
 * 
 * @author Xuan-An Cao
 */
public class LocationData {
  
  // Attributes for location data
  private String id, city, country, countryCode, state, flag;
  private float lat, lon;

  /**
   * Constructs a LocationData object with specified parameters.
   * 
   * @param id          The unique identifier for the location.
   * @param city        The name of the city.
   * @param country     The name of the country.
   * @param countryCode The country code.
   * @param state       The name of the state or region.
   * @param flag        The flag icon representing the country.
   * @param lat         The latitude coordinate of the location.
   * @param lon         The longitude coordinate of the location.
   */
  public LocationData(String id, String city, String country, String countryCode,
      String state, String flag, float lat, float lon) {
    this.id = id;
    this.city = city;
    this.country = country;
    this.countryCode = countryCode;
    this.state = state;
    this.flag = flag;
    this.lat = lat;
    this.lon = lon;
  }

  /**
   * Retrieves the unique identifier of the location.
   * 
   * @return The identifier of the location.
   */
  public String getId() {
    return id;
  }

  /**
   * Retrieves the name of the city.
   * 
   * @return The name of the city.
   */
  public String getCity() {
    return city;
  }

  /**
   * Retrieves the name of the country.
   * 
   * @return The name of the country.
   */
  public String getCountry() {
    return country;
  }

  /**
   * Retrieves the country code.
   * 
   * @return The country code.
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * Retrieves the name of the state or region.
   * 
   * @return The name of the state or region.
   */
  public String getState() {
    return state;
  }

  /**
   * Retrieves the flag icon representing the country.
   * 
   * @return The flag icon representing the country.
   */
  public String getFlag() {
    return flag;
  }

  /**
   * Retrieves the latitude coordinate of the location.
   * 
   * @return The latitude coordinate of the location.
   */
  public float getLat() {
    return lat;
  }

  /**
   * Retrieves the longitude coordinate of the location.
   * 
   * @return The longitude coordinate of the location.
   */
  public float getLon() {
    return lon;
  }

  /**
   * Checks if this LocationData object is equal to another object.
   * 
   * @param obj The object to compare for equality.
   * @return True if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    LocationData location = (LocationData) obj;

    return Objects.equals(city, location.city) &&
        Objects.equals(country, location.country) &&
        Objects.equals(state, location.state);
  }

  /**
   * Generates a hash code for this LocationData object.
   * 
   * @return The hash code for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(city, country, state);
  }
}
