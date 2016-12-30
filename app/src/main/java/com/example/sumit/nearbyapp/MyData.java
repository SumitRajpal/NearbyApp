package com.example.sumit.nearbyapp;

/**
 * Created by sumit on 12/30/2016.
 */

public class MyData {
    String name;
    double latitude,longitude,distance;

    public MyData(String name, double latitude,double longitude,double distance){
      this.name = name;
      this.distance = distance;
      this.latitude = latitude;
      this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
