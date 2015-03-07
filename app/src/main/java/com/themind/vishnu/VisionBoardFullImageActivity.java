package com.themind.vishnu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class VisionBoardFullImageActivity extends ActionBarActivity {

    int position;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vision_board_full_image);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imageUrl = extras.getString("clickedImageUrl");

        }


        ImageView fullImage = (ImageView) findViewById(R.id.full_image);
        Picasso.with(getApplicationContext()).load(imageUrl).noFade().into(fullImage);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vision_board_full_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if(id == R.id.action_delete_history)
        {
            showDeleteDialog();

        }*/

        return super.onOptionsItemSelected(item);
    }


}
