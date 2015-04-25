package com.hackaton.mosctapp.CommonClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by diesersamat on 25/04/15.
 */

public class Route {
    boolean direct;
    ArrayList<Step> route;
    String name;

    public Route(boolean direct, ArrayList<Step> route) {
        this.route = route;
        this.direct = direct;
    }

    public boolean getDirection(boolean dir) {
        return dir ^ this.direct;
    }

    public ArrayList<Step> getWay(boolean dir) {
        boolean currentDir = getDirection(dir);
        Step way;
        if(currentDir) {
            way = new Step("Left", "lalal");
        } else {
            way = new Step("Right", ";a;a");
        }
        route.add(0, way);

        return route;

    }
}
