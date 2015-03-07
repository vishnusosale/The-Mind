package com.themind.vishnu.Application;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.PushService;
import com.startapp.android.publish.SDKAdPreferences;
import com.startapp.android.publish.StartAppSDK;
import com.themind.vishnu.Bean.PrivateData;
import com.themind.vishnu.Bean.VisionBoard;
import com.themind.vishnu.BusyActivity;
import com.themind.vishnu.R;

import java.util.HashMap;

/**
 * Created by Vishnu on 12-Feb-15.
 */
public class MainApplication extends Application {


    private static final String PROPERTY_ID = "UA-60146259-1";
    //Logging TAG
    private static final String TAG = "MyApp";
    public static int GENERAL_TRACKER = 0;
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public MainApplication() {
        super();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(getApplicationContext(), getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key), getString(R.string.twitter_consumer_secret));
        PushService.setDefaultPushCallback(this, BusyActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject.registerSubclass(VisionBoard.class);
        ParseObject.registerSubclass(PrivateData.class);

        StartAppSDK.init(getApplicationContext(), "102166166 ", "202357553", new SDKAdPreferences().setAge(5));


    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        if (!mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

            Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);

            mTrackers.put(appTracker, t);
        }
        return mTrackers.get(appTracker);
    }

    public enum TrackerName {
        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
    }

}
