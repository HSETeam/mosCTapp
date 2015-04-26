package com.hackaton.mosctapp.CommonClasses;

import android.content.Context;

import com.hackaton.mosctapp.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by diesersamat on 25/04/15.
 */

interface  receiveDistance {
    void distanceReceived(byte[] responseBody, Exit exit) throws JSONException;
}

/**
 * A pair of exit and its distance from a point
 */

class Pair {
    Exit exit;
    Float distance;

    Pair(Exit exit, float distance) {
        this.exit = exit;
        this.distance = distance;
    }
}

public class Station implements receiveDistance {
    Exit[] exits;
    String name;
    Line line;
    ArrayList<Pair> distances = new ArrayList<Pair>();
    int distancesCount = 0;
    MainActivity context;

    public Station(Exit[] es, String n, Line l) {
        exits = es;
        name  = n;
        line = l;
    }


    String getName() {
        return name;
    }

    public void getNearestExit(float lon, float lat, MainActivity applicationContext){
        GoogleAPIRequest request = new GoogleAPIRequest();
        request.listener = this;
        context = ((MainActivity)applicationContext);
        for (Exit exit : exits) {
            request.getRouteDistance(exit, lon, lat);
            //TODO google api request
        }

    }

    public void distanceReceived(byte[] responseBody, Exit exit) throws JSONException {
        String answer = new String(responseBody);
        JSONObject root = new JSONObject(answer);
        JSONArray array = root.getJSONArray("routes");


        if (array.length() != 0) {
            root = array.getJSONObject(0);
            array = root.getJSONArray("legs");
            root = array.getJSONObject(0);
            root = root.getJSONObject("value");
            distances.add(new Pair(exit,Float.parseFloat(String.valueOf(Float.parseFloat(root.toString())))));
        }
        distancesCount++;
        if (distancesCount == exits.length) {
            compareDistances();
        }
    }

    private void compareDistances() {
        class CustomComparator implements Comparator<Pair> {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.distance.compareTo(o2.distance);
            }
        }

        Collections.sort(distances,new CustomComparator());
        if (distances.size() !=0 ) {
            context.receivedNearestExit(distances.get(0).exit);
        }
        else {

        }
    }
}
