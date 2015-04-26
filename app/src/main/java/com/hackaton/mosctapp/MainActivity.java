package com.hackaton.mosctapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hackaton.mosctapp.CommonClasses.*;
import com.parse.*;
import com.parse.Parse;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    GoogleAPIRequest request;
    handler Handler;
    DelayAutoCompleteTextView delayAutoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = new GoogleAPIRequest();
        Handler = new handler();
        Handler.ct = this;

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "25hdoejCjH0imQAFBGav3Wr4nMgBWYexr44RTCg7", "flhxfIbJBONuJRmcC77ZrWAEXqmpnb6Cf0lmCgAt");

        hernya();//TODO


        delayAutoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.inputTo);
        delayAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 4) {
                    request.getAutoComplete(s.toString(), Handler);

                }
            }
        });


        try {
            request.getNearestStation(55.741009f, 37.609883f, Handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Exit[] exArray = new Exit[2];
        exArray[0] = new Exit( 37.716765f, 55.751979f, new Route(true, new ArrayList<Step>()));
        exArray[1] = new Exit(37.717404f, 55.751916f, new Route(true, new ArrayList<Step>()));

        //**Testing Card Adapter (by Tema)
        List<Step> listOfSteps = new ArrayList<Step>();
        listOfSteps.add(new Step("Left", "Поверните налево после входа" ));
        listOfSteps.add(new Step("Right", "Поверните направо после входа" ));
        listOfSteps.add(new Step("Left", "Поверните налево после входа" ));
        listOfSteps.add(new Step("Left", "Выход на улицу Кропоткинская" ));
        setCardsAdapter(listOfSteps);


    }

    class handler implements receiveDistance {

        Context ct;
        @Override
        public void distanceReceived(byte[] responseBody, Exit exit) throws JSONException {

        }

        @Override
        public void nearestStationReceived(Station responseBody) throws JSONException {

        }

        @Override
        public void autoCompleteReceived(ArrayList<String> responseBody) throws JSONException {
            delayAutoCompleteTextView.setAdapter(new autoCompleteAdapter(ct, responseBody));
        }
    }

    void hernya() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Route");
        query.whereEqualTo("name", "Mayakovskaya");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCardsAdapter(List<Step> steps)
    {
        ListView lv = (ListView)findViewById(R.id.card_listview);
        ListAdapter adapter = new cardAdapter(this, steps);
        lv.setAdapter(adapter);
    }

    public void receivedNearestExit(Exit exit) {

    }
}
