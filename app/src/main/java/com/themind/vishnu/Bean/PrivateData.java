package com.themind.vishnu.Bean;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Vishnu on 04-Feb-15.
 */

@ParseClassName("PrivateData")
public class PrivateData extends ParseObject {

    public PrivateData() {

    }

    public void setUser(ParseUser currentUser) {
        put("user", currentUser);
    }

    /*public void setUpdatedAt(Date updatedAt) {
        put("createdAt", updatedAt);
    }
*/
    public String getMainGoal() {
        return getString("mainGoal");
    }

    public void setMainGoal(String mainGoal) {
        put("mainGoal", mainGoal);
    }

    public String getGratitude() {
        return getString("gratitude");
    }

    public void setGratitude(String gratitude) {
        put("gratitude", gratitude);
    }

    public String getPositiveThought() {
        return getString("positiveThought");
    }

    public void setPositiveThought(String positiveThought) {
        put("positiveThought", positiveThought);
    }

    public String getQuotes() {
        return getString("quotes");
    }

    public void setQuotes(String quotes) {
        put("quotes", quotes);
    }

    public String getMyCreatedAt() {
        /*String createdAtDateString = getString("createdAt");

        Date createdAt = getDate("createdAt");*/

        return getDate("myCreatedAt").toString();
    }

    public void setMyCreatedAt(Date createdAt) {
        put("myCreatedAt", createdAt);
    }


}
