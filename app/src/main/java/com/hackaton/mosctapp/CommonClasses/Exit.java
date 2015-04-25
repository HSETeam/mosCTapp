package com.hackaton.mosctapp.CommonClasses;

/**
 * Created by diesersamat on 25/04/15.
 */
public class Exit {
    public float lon;
    public float lat;
    public Route way;


    Exit(float lon, float lat, Route way) {
        this.lat = lat;
        this.lon = lon;
        this.way = way;
    }

    Exit() {

    }


}
