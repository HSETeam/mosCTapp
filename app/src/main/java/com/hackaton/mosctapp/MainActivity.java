package com.hackaton.mosctapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.hackaton.mosctapp.CommonClasses.*;
import com.parse.*;
import com.parse.Parse;
import com.parse.entity.mime.content.StringBody;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Place from;
    Place to;

    Station stationFrom;
    Station stationTo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "25hdoejCjH0imQAFBGav3Wr4nMgBWYexr44RTCg7", "flhxfIbJBONuJRmcC77ZrWAEXqmpnb6Cf0lmCgAt");
        initializeAutoHint();
    }


    void loadFinalRouteForStationFromParse(String name) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Route");
        query.whereEqualTo("name", name);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    findViewById(R.id.progressInstructions).setVisibility(View.GONE);
                    List<String> list = scoreList.get(0).getList("steps");
                    List<Step> result = new ArrayList<Step>();
                    for (String i : list) {
                        result.add(new Step(i, "Поверните на " + i));
                    }
                    setCardsAdapter(result);
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
    void  loadExits (String stationName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("rows2");
        query.whereEqualTo("NameOfStation", stationName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    findViewById(R.id.progressInstructions).setVisibility(View.GONE);
                    List<Exit> list = new ArrayList<Exit>();
                    for (ParseObject po : scoreList) {
                        if (po.getString("Name").contains("ыход")) {
                            list.add(new Exit(po.getDouble("Lon"), po.getDouble("Lat"), po.getString("Name")));
                        }
                    }

                    GoogleAPIRequest g = new GoogleAPIRequest();
                    Exit nearestExit = g.getNearestExit(to.x, to.y, list);
                    loadFinalRouteForStationFromParse(nearestExit.name);
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void dataSetChanged() throws UnsupportedEncodingException {
        if (from != null && to != null && from.x != 0 && to.x !=0) {
            loadFinalRouteForStationFromParse("Маяковская");
            findViewById(R.id.progressInstructions).setVisibility(View.VISIBLE);
            GoogleAPIRequest request = new GoogleAPIRequest();
            request.getNearestStation(from.x, from.y, new receiveDistance() {
                @Override
                public void nearestStationReceived(Station responseBody) throws JSONException {
                    stationFrom = responseBody;
                }
            });

            request.getNearestStation(to.x, to.y, new receiveDistance() {
                @Override
                public void nearestStationReceived(Station responseBody) throws JSONException {
                    stationTo = responseBody;
                    loadExits(stationTo.getName());
                }
            });
        }
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

        if(id == R.id.action_clear) {
            to = null;
            from = null;
            ((EditText)findViewById(R.id.inputTo)).setText("");
            ((EditText)findViewById(R.id.inputFrom)).setText("");
            setCardsAdapter(new ArrayList<Step>());

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

    /**
     * Инициализация подсказок выбора позиции
     */
    public void initializeAutoHint() {
        final DelayAutoCompleteTextView toTitle = (DelayAutoCompleteTextView) findViewById(R.id.inputTo);
        toTitle.setThreshold(4);
        toTitle.setAdapter(new autoCompleteAdapter(this));
        toTitle.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar2));
        final MainActivity c = this;
        toTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Place str = (Place) adapterView.getItemAtPosition(position);
                toTitle.setText(str.name);
                to = str;
                to.loadData( c);
//
            }
        });

        final DelayAutoCompleteTextView fromTitle = (DelayAutoCompleteTextView) findViewById(R.id.inputFrom);
        fromTitle.setThreshold(4);
        fromTitle.setAdapter(new autoCompleteAdapter(this));
        fromTitle.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar1));
        fromTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Place str = (Place) adapterView.getItemAtPosition(position);
                fromTitle.setText(str.name);
                from = str;
                from.loadData(c);

            }
        });
    }
}
