package com.hackaton.mosctapp.CommonClasses;

/**
 * Created by diesersamat on 25/04/15.
 */
interface  receiveDistance {
    void distanceReceived(float distance);
}
public class Station implements receiveDistance {
    Exit[] exits;
    String name;
    Line line;

    Station(Exit[] es, String n, Line l) {
        exits = es;
        name  = n;
        line = l;
    }


    String getName() {
        return name;
    }

    Exit getNearestExit(float lon, float lat){
        return null;
       //TODO google api request
    }

    public void distanceReceived(float distance) {

    }
}
