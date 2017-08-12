package pl.patrykks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoCoordinate {

    @JsonProperty(value = "lat")
    private double latitude;

    @JsonProperty(value = "lon")
    private double longitude;

    public GeoCoordinate() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GeoCoordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
