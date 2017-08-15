package pl.patrykks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    @JsonProperty(value = "countryName")
    private String countryName;

    @JsonProperty(value = "cityName")
    private String cityName;

    @JsonProperty(value = "stateName")
    private String stateName;

    @JsonProperty(value = "coords")
    private GeoCoordinate geoCoordinate;

    public City() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public GeoCoordinate getGeoCoordinate() {
        return geoCoordinate;
    }

    public void setGeoCoordinate(GeoCoordinate geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }

    @Override
    public String toString() {
        return "City{" +
                "countryName='" + countryName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", geoCoordinate=" + geoCoordinate +
                '}';
    }
}
