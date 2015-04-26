package com.hackaton.mosctapp.CommonClasses;

import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;
import com.hackaton.mosctapp.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by tema on 26.04.15.
 */
public class Place {
    final public String name;
    final public String id;
     public double x;
     public double y;

    public Place(String name, String id) {
        this.name = name;
        this.id= id;

    }

    public void loadData(final MainActivity ma) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("key", "AIzaSyD3HZiU9pf0R0ggYKrStSChUtUAOGj6dh8");
        params.put("placeid", id);

        client.get("https://maps.googleapis.com/maps/api/place/details/json" , params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //TODO parse answer

                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    obj = obj.getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
                    x = obj.getDouble("lat");
                    y = obj.getDouble("lng");
                    ma.dataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("pizda","answer did not received\n"+(new String(responseBody)));
            }
        });
    }
}
