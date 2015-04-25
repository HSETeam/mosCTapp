package com.hackaton.mosctapp.CommonClasses;
/**
 * Created by denis on 26.04.15.
 */
import android.util.Log;

import com.loopj.android.http.*;

import org.apache.http.Header;

import java.util.ArrayList;

public class GoogleAPIRequest {

    receiveDistance listener;

    void getRouteDistance (final Exit exit, float lon2, float lat2){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("origin", exit.lon+","+exit.lat);
        params.put("destination", lon2+","+lat2);
        params.put("sensor", "true");
        params.put("mode","walking");

        client.get("http://maps.googleapis.com/maps/api/directions/json" , params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //TODO parse answer
                System.out.println("answer received");
                Log.d("pizda", "answer received\n" + (new String(responseBody)));
                listener.distanceReceived(responseBody, exit);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("pizda","answer did not received\n"+(new String(responseBody)));
            }
        });

    }
}
