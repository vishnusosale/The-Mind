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

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.themind.vishnu.Bean.PrivateData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;


public class HistoryActivity extends ActionBarActivity implements Card.OnSwipeListener {


    CardArrayAdapter mCardArrayAdapter;
    CheckNetworkState internetState;
    Boolean isInternetPresent;


    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//  TODO more to be done
        // get action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb107")));


        loadHistory();


    }


    private void loadHistory() {

        internetState = new CheckNetworkState(getApplicationContext());
        isInternetPresent = internetState.isConnectingToInternet();

        if (isInternetPresent) {

            setContentView(R.layout.history_fragment);
            final ParseQuery<PrivateData> query = new ParseQuery<>("PrivateData");
            query.whereEqualTo("user", ParseUser.getCurrentUser());
            query.orderByDescending("createdAt");
            //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);

            query.findInBackground(new FindCallback<PrivateData>() {

                @Override
                public void done(List<PrivateData> data, ParseException error) {
                    if (data.isEmpty()) {
                        TextView textView = (TextView) findViewById(R.id.textView12);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        CardListView cardListView;
                        Card card;
                        ArrayList<Card> cardArrayList = new ArrayList<>();

                        for (int i = 0; i < data.size(); i++) {
                            card = new Card(getApplicationContext());
                            // Create a CardHeader
                            CardHeader header = new CardHeader(getApplicationContext());

                            /*DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                            String dateString = dateFormat.format(data.get(i).getCreatedAt());*/

                            Date createdAtDate = data.get(i).getCreatedAt();
                            long timeInMilliseconds = createdAtDate.getTime();

                            CharSequence dateFormatter = DateUtils.getRelativeDateTimeString(getApplicationContext(), timeInMilliseconds,
                                    DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME);


                            header.setTitle("Thought Created : " + dateFormatter);
                            card.addCardHeader(header);
                            card.setTitle("Tap to view details");
                           /* CardExpand expand = new CardExpand(getApplicationContext());
                           // expand.setInnerLayout(R.layout.history_card_details);
                            expand.setTitle(data.get(i).getMainGoal());*/

                            //data.get(mCardArrayAdapter.getPosition(card));

                            card.setOnClickListener(new Card.OnCardClickListener() {
                                @Override
                                public void onClick(Card card, View view) {

                                    Intent historyCardIntent = new Intent(HistoryActivity.this, HistoryCardDetailsActivity.class);
                                    historyCardIntent.putExtra("clickedCardPosition", mCardArrayAdapter.getPosition(card));
                                    //historyCardIntent.putExtra("parseObjectID", )
                                    startActivity(historyCardIntent);
                                }
                            });


                            //card.setTitle("Created At : " + data.get(i).getCreatedAt().toString());
                            //mCardArrayAdapter.add(card);
                            cardArrayList.add(card);
                        }


                        mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cardArrayList);
                        cardListView = (CardListView) findViewById(R.id.history_card_view);

                        //if (cardListView != null) {
                        cardListView.setAdapter(mCardArrayAdapter);
                        //}

                    }
                }
            });

        } else {
            setContentView(R.layout.no_internet_fragment);
            Button tryAgain = (Button) findViewById(R.id.tryAgainButton);

            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadHistory();
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

        if (id == R.id.action_refresh) {
            internetState = new CheckNetworkState(getApplicationContext());
            isInternetPresent = internetState.isConnectingToInternet();
            if (isInternetPresent) {
                setContentView(R.layout.history_fragment);
                loadHistory();
            } else {
                setContentView(R.layout.no_internet_fragment);
                Button tryAgain = (Button) findViewById(R.id.tryAgainButton);
                tryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadHistory();
                    }
                });
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwipe(Card card) {

        /*
                    Delete code here
         */

    }



    /*

            Add expanded list view
     */
}
