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
