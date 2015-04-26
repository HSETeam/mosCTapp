package com.hackaton.mosctapp.CommonClasses;
/**
 * Created by denis on 26.04.15.
 */
import android.content.Context;
import android.util.Log;

import com.hackaton.mosctapp.JsonLoad;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GoogleAPIRequest {



    void getRouteDistance (final Exit exit, float lon2, float lat2, final receiveDistance listener){
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

    public void getNearestStation (double lon, double lat, final receiveDistance listener) throws UnsupportedEncodingException {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("key", "AIzaSyD3HZiU9pf0R0ggYKrStSChUtUAOGj6dh8");
        params.add("location", lon+","+lat);
        params.add("sensor", "true");
        params.add("types", "subway_station"); //TODO change radius to correct value
        params.add("rankby", "distance");
        params.add("language", "ru");

        client.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json" , params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //TODO
                try {
                    Station result = new Station();
                    JSONObject obj = new JSONObject(new String(responseBody));
                    result.name = obj.getJSONArray("results").getJSONObject(0).getString("name");
                    result.lon = obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location").getDouble("lng");
                    result.lat = obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location").getDouble("lat");
                    listener.nearestStationReceived(result);
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

    public List<Place> getAutoComplete (String keyWord) {

        JsonLoad json = new JsonLoad();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", "AIzaSyD3HZiU9pf0R0ggYKrStSChUtUAOGj6dh8"));
        params.add(new BasicNameValuePair("input", keyWord));
        params.add(new BasicNameValuePair("sensor", "true"));
        params.add(new BasicNameValuePair("radius", "35000"));
        params.add(new BasicNameValuePair("components", "country:ru"));
        String result = json.makeHttpRequestString("https://maps.googleapis.com/maps/api/place/autocomplete/json", "GET", params );

        try {
            JSONObject obj = new JSONObject(new String(result));
            JSONArray predictions = obj.getJSONArray("predictions");
            ArrayList<Place> results = new ArrayList<Place>();
            for (int i = 0; i < predictions.length(); i++) {
                results.add(new Place(predictions.getJSONObject(i).getString("description"),
                        predictions.getJSONObject(i).getString("place_id")));
            }

            return results;
        } catch (JSONException e) {

        }

        return null;

//        SyncHttpClient client = new SyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("key", "AIzaSyD3HZiU9pf0R0ggYKrStSChUtUAOGj6dh8");
//        params.add("input", keyWord);
//        params.add("sensor", "true");

//        client.get("https://maps.googleapis.com/maps/api/place/autocomplete/json" , params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                System.out.println("answer received");
//                Log.d("pizda", "answer received\n" + (new String(responseBody)));
//
//                try {
//                    JSONObject obj = new JSONObject(new String(responseBody));
//                    JSONArray predictions = obj.getJSONArray("predictions");
//                    ArrayList<String> results = new ArrayList<String>();
//                    for (int i = 0; i < predictions.length(); i++) {
//                        results.add(predictions.getJSONObject(i).getString("description"));
//                    }
//
//                    listener.autoCompleteReceived(results);
//                } catch (JSONException e) {
//                    onFailure(statusCode, headers, responseBody, new IllegalArgumentException());
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("pizda","answer did not received\n"+(new String(responseBody)));
//                //TODO parse answer
//            }
//        });
    }



    public Exit getNearestExit (float lon2, float lat2, Exit[] s ) {
       return s[0];
    }
    /*
    "Id":624,
"CategoryId":13,
"DepartmentId":14,
"Caption":"Входы и выходы вестибюлей станций Московского метрополитена",
"ContainsGeodata":true
     */
}
