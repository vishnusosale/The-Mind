package com.themind.vishnu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        /*
        TODO: change package com.vishnu.themind
         */

       /*// get action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        TextView textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView9 = (TextView) findViewById(R.id.textView9);
        textView9.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView10 = (TextView) findViewById(R.id.textView10);
        textView10.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
}
