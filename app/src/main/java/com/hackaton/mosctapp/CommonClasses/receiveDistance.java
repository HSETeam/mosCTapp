package com.hackaton.mosctapp.CommonClasses;

import android.content.Context;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by diesersamat on 26/04/15.
 */
public interface  receiveDistance {
    void distanceReceived(byte[] responseBody, Exit exit) throws JSONException;
    void nearestStationReceived(Station responseBody) throws JSONException;
    void autoCompleteReceived(ArrayList<String> responseBody) throws JSONException;
}
