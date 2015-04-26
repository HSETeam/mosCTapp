package com.hackaton.mosctapp.CommonClasses;

import android.content.Context;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by diesersamat on 26/04/15.
 */
public interface  receiveDistance {
    void nearestStationReceived(Station responseBody) throws JSONException;
}
