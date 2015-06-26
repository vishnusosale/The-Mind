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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.model.AdPreferences;
import com.themind.vishnu.Bean.PrivateData;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;


public class HistoryCardDetailsActivity extends ActionBarActivity {

    int position;

    String parseObjectID;

    Card card;

    TextView mainGoalText;
    TextView gratitudeText;
    TextView positiveThoughtText;
    TextView quotesText;

    PrivateData data;

    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startAppAd.loadAd(new AdPreferences().setAge(5));
        //StartAppSDK.init(this, "102166166 ", "202357553", new SDKAdPreferences().setAge(5));
        setContentView(R.layout.activity_history_card_details);


        // get action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb107")));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("clickedCardPosition");
        }

        data = new PrivateData();

        card = new Card(this, R.layout.history_card_details_form);
        CardView cardView = (CardView) findViewById(R.id.historyDetailsCard);
        cardView.setCard(card);

       /* mainGoalText = (TextView) findViewById(R.id.mainGoalText);
        gratitudeText = (TextView) findViewById(R.id.gratitudeText);
        positiveThoughtText = (TextView) findViewById(R.id.positiveThoughtText);
        quotesText = (TextView) findViewById(R.id.quotesText);*/


        ParseQuery<PrivateData> query = new ParseQuery<>("PrivateData");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.orderByDescending("createdAt");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);

        query.findInBackground(new FindCallback<PrivateData>() {

            @Override
            public void done(List<PrivateData> data, ParseException error) {
                if (data != null) {

                    mainGoalText = (TextView) findViewById(R.id.mainGoalText);
                    gratitudeText = (TextView) findViewById(R.id.gratitudeText);
                    positiveThoughtText = (TextView) findViewById(R.id.positiveThoughtText);
                    quotesText = (TextView) findViewById(R.id.quotesText);

                    parseObjectID = data.get(position).getObjectId();
                    mainGoalText.setText("Your Main Goal : " + data.get(position).getMainGoal());
                    gratitudeText.setText("Gratitude: " + data.get(position).getGratitude());
                    positiveThoughtText.setText("Your Positive Thought: " + data.get(position).getPositiveThought());
                    quotesText.setText("Your Quotes: " + data.get(position).getQuotes());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startAppAd.loadAd(new AdPreferences().setAge(5));
        //.setLatitude(31.776719)
        //.setLongitude(35.234508));
        startAppAd.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_card_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_history) {

            showEditDialog();

            return true;
        }

        if (id == R.id.action_delete_history) {
            showDeleteDialog();

        }


        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.history_edit_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        /*final EditText mainGoalEditText = (EditText)promptsView.findViewById(R.id.mainGoalEditText);
        final EditText gratitudeEditText = (EditText)promptsView.findViewById(R.id.gratitudeEditText);
        final EditText positiveThoughtEditText = (EditText)promptsView.findViewById(R.id.positiveThoughtEditText);
        final EditText quotesEditText = (EditText)promptsView.findViewById(R.id.quotesEditText);*/


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                EditText mainGoalEditText = (EditText) findViewById(R.id.mainGoalEditText);
                                EditText gratitudeEditText = (EditText) findViewById(R.id.gratitudeEditText);
                                EditText positiveThoughtEditText = (EditText) findViewById(R.id.positiveThoughtEditText);
                                EditText quotesEditText = (EditText) findViewById(R.id.quotesEditText);

                                mainGoalText.setText("Your Main Goal : " + mainGoalEditText.getText().toString().trim());
                                gratitudeText.setText("Gratitude: " + gratitudeEditText.getText().toString().trim());
                                positiveThoughtText.setText("Your Positive Thought: " + positiveThoughtEditText.getText().toString().trim());
                                quotesText.setText("Your Quotes: " + quotesEditText.getText().toString().trim());

                                saveUpdatedData(mainGoalEditText.getText().toString().trim(),
                                        gratitudeEditText.getText().toString().trim(),
                                        positiveThoughtEditText.getText().toString().trim(),
                                        quotesEditText.getText().toString().trim());
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it

        alertDialog.show();
    }

    private void saveUpdatedData(String mainGoal, String gratitude, String positiveThought, String quotes) {
        data.setACL(new ParseACL(ParseUser.getCurrentUser()));
        data.setUser(ParseUser.getCurrentUser());
        data.setObjectId(parseObjectID);
        data.setMainGoal(mainGoal);
        data.setGratitude(gratitude);
        data.setPositiveThought(positiveThought);
        data.setQuotes(quotes);
        data.saveInBackground();

    }


    private void showDeleteDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.history_delete_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                data.setACL(new ParseACL(ParseUser.getCurrentUser()));
                                data.setUser(ParseUser.getCurrentUser());
                                data.setObjectId(parseObjectID);
                                data.deleteInBackground();
                                finish();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it

        alertDialog.show();
    }
}





