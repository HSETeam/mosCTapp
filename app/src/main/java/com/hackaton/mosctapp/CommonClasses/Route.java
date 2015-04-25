package com.hackaton.mosctapp.CommonClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by diesersamat on 25/04/15.
 */
enum Steps {
    Straight,
    Right,
    Left
}

public class Route {
    boolean direct;
    ArrayList<Steps> route;
    String name;

    public Route(boolean direct, ArrayList<Steps> route) {
        this.route = route;
        this.direct = direct;
    }

    public boolean getDirection(boolean dir) {
        return dir ^ this.direct;
    }

    public ArrayList<Steps> getWay(boolean dir) {
        boolean currentDir = getDirection(dir);
        Steps way;
        if(currentDir) {
            way = Steps.Left;
        } else {
            way = Steps.Right;
        }
        route.add(0, way);

        return route;

    }
}
