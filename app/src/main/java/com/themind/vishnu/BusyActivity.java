/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Vishnu Sosale
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.themind.vishnu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.model.AdPreferences;
import com.themind.vishnu.Application.MainApplication;
import com.themind.vishnu.Bean.PrivateData;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;


public class BusyActivity extends ActionBarActivity {

    PrivateData data;
    CheckNetworkState internetState;
    Boolean isInternetPresent;
    private EditText mainGoalEditText, gratitudeEditText, positiveThoughtEditText, quotesEditText;
    private ImageButton tagButton;

    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StartAppSDK.init(getApplicationContext(), "102166166 ", "202357553", new SDKAdPreferences().setAge(5));
        setContentView(R.layout.busy_activity_fragment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb107")));

        //Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_LONG).show();
        // TODO: ActionBarSherlock.
        // TODO: Save instances....
        Card card = new Card(this, R.layout.thoughts_form);

        reportGoogleAnalytics("/homeScreen");

        CardView cardView = (CardView) findViewById(R.id.thoughtsCard);
        cardView.setCard(card);
        ParseAnalytics.trackAppOpened(getIntent());


        /*ParseACL defaultACL = new ParseACL(ParseUser.getCurrentUser());
        ParseACL.setDefaultACL(defaultACL, true);*/

        /*ParseObject.registerSubclass(VisionBoard.class);
        ParseObject.registerSubclass(PrivateData.class);*/

        if (ParseUser.getCurrentUser() != null) {
            tagButton = (ImageButton) findViewById(com.themind.vishnu.R.id.galleryButton);
            mainGoalEditText = (EditText) findViewById(R.id.mainGoalEditText);
            gratitudeEditText = (EditText) findViewById(R.id.gratitudeEditText);
            positiveThoughtEditText = (EditText) findViewById(R.id.positiveThoughtEditText);
            quotesEditText = (EditText) findViewById(R.id.quotesEditText);


            data = new PrivateData();


            tagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent imageControllerIntent = new Intent(getApplicationContext(), ImageControllerActivity.class);
                    startActivity(imageControllerIntent);

                }
            });
        } else {
            Intent intent = new Intent(BusyActivity.this, LoginDispatchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void reportGoogleAnalytics(String screenName) {
        Tracker t = ((MainApplication) getApplication()).getTracker(MainApplication.TrackerName.APP_TRACKER);
        t.setScreenName(screenName);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public void onResume() {
        super.onResume();
        startAppAd.loadAd(new AdPreferences().setAge(5));
        //.setLatitude(31.776719)
        //.setLongitude(35.234508));
        // startAppAd.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    public void onBackPressed() {
        startAppAd.onBackPressed();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }


     /*@Override
    protected void onPause() {
        super.onPause();
        // Store values between instances here
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI


        mainGoalEditText = (EditText) findViewById(R.id.mainGoalEditText);
        gratitudeEditText = (EditText) findViewById(R.id.gratitudeEditText);
        positiveThoughtEditText = (EditText) findViewById(R.id.positiveThoughtEditText);
        quotesEditText = (EditText) findViewById(R.id.quotesEditText);

        String mainGoalEditTextString = mainGoalEditText.getText().toString().trim();
        String gratitudeEditTextString = gratitudeEditText.getText().toString().trim();
        String positiveThoughtEditTextString = positiveThoughtEditText.getText().toString().trim();
        String quotesEditTextString = quotesEditText.getText().toString().trim();

        editor.putString("mainGoalEditTextString", mainGoalEditTextString); // value to store
        editor.putString("gratitudeEditTextString", gratitudeEditTextString); // value to store
        editor.putString("positiveThoughtEditTextString", positiveThoughtEditTextString);
        editor.putString("quotesEditTextString", quotesEditTextString);// value to store
        // Commit to storage
        editor.commit();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busy_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_history) {
            Intent addNewIntent = new Intent(this, HistoryActivity.class);
            startActivity(addNewIntent);
            return true;
        }

        if (id == R.id.action_vision_board) {
            Intent addNewIntent = new Intent(this, VisionBoardActivity.class);
            startActivity(addNewIntent);
            return true;
        }

        if (id == R.id.action_about) {
            // TODO: code about!
            Intent addNewIntent = new Intent(this, AboutActivity.class);
            startActivity(addNewIntent);
            return true;
        }

        if (id == R.id.action_logout) {
            internetState = new CheckNetworkState(getApplicationContext());
            isInternetPresent = internetState.isConnectingToInternet();
            if (isInternetPresent) {
                ParseUser.logOut();
                Intent intent = new Intent(BusyActivity.this, LoginDispatchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                // TODO: make a dialog box instead.
                Toast.makeText(getApplicationContext(), "uh-oh! Cannot log out... Seems you are offline. Please check your internet settings.", Toast.LENGTH_LONG).show();
            }

        }

        if (id == R.id.action_save) {
            internetState = new CheckNetworkState(getApplicationContext());
            isInternetPresent = internetState.isConnectingToInternet();
            if (isInternetPresent) {
                addThoughts();
            } else {
                // TODO: make a dialog box instead.
                Toast.makeText(getApplicationContext(), "uh-oh! Cannot save... Seems you are offline. Please check your internet settings.", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addThoughts() {
        String mainGoalString, gratitudeString, positiveThoughtString, quotesString;

        mainGoalString = mainGoalEditText.getText().toString().trim();
        gratitudeString = gratitudeEditText.getText().toString().trim();
        positiveThoughtString = positiveThoughtEditText.getText().toString().trim();
        quotesString = quotesEditText.getText().toString().trim();

        if (mainGoalString.length() > 0 || gratitudeString.length() > 0 || positiveThoughtString.length() > 0 || quotesString.length() > 0) {
            data.setACL(new ParseACL(ParseUser.getCurrentUser()));
            data.setUser(ParseUser.getCurrentUser());

            data.setMainGoal(mainGoalString);
            data.setGratitude(gratitudeString);
            data.setPositiveThought(positiveThoughtString);
            data.setQuotes(quotesString);
            // data.setMyCreatedAt(new Date());

            data.saveInBackground();

            mainGoalEditText.setText("");
            gratitudeEditText.setText("");
            positiveThoughtEditText.setText("");
            quotesEditText.setText("");

            Toast.makeText(getApplicationContext(), "Your thoughts are saved!", Toast.LENGTH_SHORT).show();
        }


    }
}
