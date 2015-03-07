package com.themind.vishnu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.themind.vishnu.Adapter.VisionBoardAdapter;
import com.themind.vishnu.Bean.VisionBoard;

import java.util.ArrayList;
import java.util.List;


public class VisionBoardActivity extends ActionBarActivity {

    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    private static final String TAG = "StaggeredGridActivity";

    StaggeredGridView mGridView;
    boolean mHasRequestedMore;
    VisionBoardAdapter mAdapter;

    ProgressDialog mProgressDialog;
    CheckNetworkState internetState;
    Boolean isInternetPresent;

    VisionBoard data;
    String parseObjectID;

    ArrayList<String> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO more to be done
        // get action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb107")));

        loadImages();

       /* mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mAdapter = new VisionBoardAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, getImageUrls());

        data = new VisionBoard();

        // do we have saved data?
       /* if (savedInstanceState != null) {
            mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
        }

        if (mData == null) {
            mData = getImageUrls();
        }

        for (String data : mData) {
            mAdapter.add(data);
        }
        mAdapter.notifyDataSetChanged();*\\/

        mGridView.setAdapter(mAdapter);
        /*mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {

                Log.d(TAG, "onScrollStateChanged:" + scrollState);
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
                Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                        " visibleItemCount:" + visibleItemCount +
                        " totalItemCount:" + totalItemCount);
                // our handling
                /*if (!mHasRequestedMore) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (lastInScreen >= totalItemCount) {
                        Log.d(TAG, "onScroll lastInScreen - so load more");
                        mHasRequestedMore = true;
                        onLoadMoreItems();
                    }
                }
            }
        });*\/
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent showFullImage = new Intent(VisionBoardActivity.this, VisionBoardFullImage.class);
                //mAdapter.getItem(position);
                showFullImage.putExtra("clickedImageUrl", mAdapter.getItem(position));
                startActivity(showFullImage);

                //Toast.makeText(getApplicationContext(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    private void loadImages() {
        internetState = new CheckNetworkState(getApplicationContext());
        isInternetPresent = internetState.isConnectingToInternet();

        if (isInternetPresent) {
            setContentView(R.layout.vision_board_fragment);
            data = new VisionBoard();


            mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
            mAdapter = new VisionBoardAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, getImageUrls());
            mGridView.setAdapter(mAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent showFullImage = new Intent(VisionBoardActivity.this, VisionBoardFullImageActivity.class);
                    //mAdapter.getItem(position);
                    showFullImage.putExtra("clickedImageUrl", mAdapter.getItem(position));
                    startActivity(showFullImage);

                    //Toast.makeText(getApplicationContext(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            setContentView(R.layout.no_internet_fragment);
            Button tryAgain = (Button) findViewById(R.id.tryAgainButton);

            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadImages();
                }
            });
        }
    }

    private void showDeleteAlert(int position) {
        final CharSequence[] items = {"Open camera", "Choose from gallery",
                "Cancel"};

        final int mPosition = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(VisionBoardActivity.this);
        builder.setTitle("Delete Image?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Delete")) {
                    /*
                        delete the image

                     */


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

   /* @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }*/


    /*private void onLoadMoreItems() {
        final ArrayList<String> sampleData = getImageUrls();
        for (String data : sampleData) {
            mAdapter.add(data);
        }
        // stash all the data in our backing store
        mData.addAll(sampleData);
        // notify the adapter that we can update now
        mAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;
    }*/

    // ArrayList<String> listData  = new ArrayList<>();

    private ArrayList<String> getImageUrls() {


        ArrayList<String> listOfImageUrls = new ArrayList<>();

        generateImageUrls imageUrls = new generateImageUrls();

        try {
            listOfImageUrls = imageUrls.execute().get();
            if (listOfImageUrls.isEmpty()) {
                //Toast.makeText(getApplicationContext(),"no pics", Toast.LENGTH_LONG).show();
                TextView textView = (TextView) findViewById(R.id.textView11);
                textView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfImageUrls;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vision_board, menu);
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

        return super.onOptionsItemSelected(item);
    }

    private class generateImageUrls extends AsyncTask<Void, Void, ArrayList<String>> {

        ArrayList<String> listData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Create a progress dialog
            mProgressDialog = new ProgressDialog(VisionBoardActivity.this);
            // Set progress dialog title
            mProgressDialog.setTitle("Fetching images from your Vision Board");
            // Set progress dialog message
            mProgressDialog.setMessage("Please wait while the image is loaded. This might take several moments depending on the image size and network speed.");
            mProgressDialog.setIndeterminate(false);
            // Show progress dialog

            mProgressDialog.show();


        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            //ArrayList<String> listData = new ArrayList<>();

            listData = new ArrayList<>();
            List<VisionBoard> visionBoards;

            ParseQuery<VisionBoard> query = new ParseQuery<>("VisionBoard");
            query.whereEqualTo("user", ParseUser.getCurrentUser());
            query.orderByDescending("createdAt");

            try {
                visionBoards = query.find();

                for (int i = 0; i < visionBoards.size(); i++) {
                    ParseFile imageFile = visionBoards.get(i).getImage();

                    String url = imageFile.getUrl();
                    listData.add(url);
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;

                }
            }
            return listData;

        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            // Result is here now, may be 6 different List type.
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            listData = result;

        }

    }
}