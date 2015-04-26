package com.hackaton.mosctapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.hackaton.mosctapp.CommonClasses.*;
import com.parse.*;
import com.parse.Parse;
import com.parse.entity.mime.content.StringBody;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "25hdoejCjH0imQAFBGav3Wr4nMgBWYexr44RTCg7", "flhxfIbJBONuJRmcC77ZrWAEXqmpnb6Cf0lmCgAt");

        hernya();

        Exit[] exArray = new Exit[2];
        exArray[0] = new Exit(10, 3, new Route(true, new ArrayList<Step>()));
        exArray[1] = new Exit(2, 6, new Route(true, new ArrayList<Step>()));
        Station station = new Station(exArray, "asfas", new Line("ad"));
        //station.getNearestExit(0, 0, this);

        //**Testing Card Adapter (by Tema)
        List<Step> listOfSteps = new ArrayList<Step>();
        listOfSteps.add(new Step("Left", "Поверните налево после входа" ));
        listOfSteps.add(new Step("Right", "Поверните направо после входа" ));
        listOfSteps.add(new Step("Left", "Поверните налево после входа" ));
        listOfSteps.add(new Step("Left", "Выход на улицу Кропоткинская" ));
        setCardsAdapter(listOfSteps);
        initializeAutoHint();
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

    /**
     * Инициализация подсказок выбора позиции
     */
    public void initializeAutoHint() {
        final DelayAutoCompleteTextView toTitle = (DelayAutoCompleteTextView) findViewById(R.id.inputTo);
        toTitle.setThreshold(4);
        toTitle.setAdapter(new autoCompleteAdapter(this));
        toTitle.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar2));
        toTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String str = (String) adapterView.getItemAtPosition(position);
                toTitle.setText(str);
            }
        });

        final DelayAutoCompleteTextView fromTitle = (DelayAutoCompleteTextView) findViewById(R.id.inputFrom);
        fromTitle.setThreshold(4);
        fromTitle.setAdapter(new autoCompleteAdapter(this));
        fromTitle.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar1));
        fromTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String str = (String) adapterView.getItemAtPosition(position);
                fromTitle.setText(str);
            }
        });
    }
}
