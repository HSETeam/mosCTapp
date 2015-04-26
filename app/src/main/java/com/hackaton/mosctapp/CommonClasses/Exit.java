package com.hackaton.mosctapp.CommonClasses;

/**
 * Created by diesersamat on 25/04/15.
 */
public class Exit {
    public double lon;
    public double lat;
    public Route way;
    public String name;


    public Exit(double lon, double lat, String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public Exit() {

    }


}
