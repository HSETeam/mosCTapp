package com.hackaton.mosctapp.CommonClasses;
/**
 * Created by denis on 26.04.15.
 */
import android.util.Log;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONException;

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
                try {
                    listener.distanceReceived(responseBody, exit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("pizda","answer did not received\n"+(new String(responseBody)));
            }
        });

    }

    public void getNearestStation (float lon, float lat) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("key", "AIzaSyD3HZiU9pf0R0ggYKrStSChUtUAOGj6dh8");
        params.add("location", lat+","+lon);
        params.add("sensor", "true");
        params.add("keyword","метро");
        params.add("language", "RUru");

        client.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json" , params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //TODO parse answer
                System.out.println("answer received");
                Log.d("pizda", "answer received\n" + (new String(responseBody)));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("pizda","answer did not received\n"+(new String(responseBody)));
            }
        });
    }

    public void getAutoComplete (String keyWord) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("key", "AIzaSyD3HZiU9pf0R0ggYKrStSChUtUAOGj6dh8");
        params.add("input", keyWord);
        params.add("sensor", "true");
        params.add("language", "RUru");

        client.get("https://maps.googleapis.com/maps/api/place/autocomplete/json" , params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //TODO parse answer
                System.out.println("answer received");
                Log.d("pizda", "answer received\n" + (new String(responseBody)));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("pizda","answer did not received\n"+(new String(responseBody)));
            }
        });
    }
    /*
    "Id":624,
"CategoryId":13,
"DepartmentId":14,
"Caption":"Входы и выходы вестибюлей станций Московского метрополитена",
"ContainsGeodata":true
     */
}
