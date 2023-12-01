/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp.models;

import java.util.Objects;

/**
 *
 * @author Xuan-An Cao
 */
public class LocationData {
  private String id, city, country, countryCode, state, flag;
  private float lat, lon;

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

  public String getId() {
    return id;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getState() {
    return state;
  }

  public String getFlag() {
    return flag;
  }

  public float getLat() {
    return lat;
  }

  public float getLon() {
    return lon;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(city, country, state);
  }
}
