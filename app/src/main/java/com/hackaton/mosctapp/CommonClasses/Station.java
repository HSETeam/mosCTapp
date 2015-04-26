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

public class Station {
    Exit[] exits;
    String name;
    Line line;
    double lon;
    double lat;
    ArrayList<Pair> distances = new ArrayList<Pair>();
    int distancesCount = 0;
    MainActivity context;

    public Station() {

    }

    public Station(Exit[] es, String n, Line l) {
        exits = es;
        name  = n;
        line = l;
    }


    public String getName() {
        return name;
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
