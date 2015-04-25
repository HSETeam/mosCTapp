package com.hackaton.mosctapp.CommonClasses;
/**
 * Created by denis on 26.04.15.
 */
import com.loopj.android.http.*;

import org.apache.http.Header;

public class GoogleAPIRequest {

    receiveDistance delegate;
    void getRouteDistance (float lon1, float lat1, float lon2, float lat2){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("http://maps.googleapis.com/maps/api/directions/output?parameters\n",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //TODO parse answer
                System.out.println("answer received");
                delegate.distanceReceived(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("answer did not received");
            }
        });
    }
}
