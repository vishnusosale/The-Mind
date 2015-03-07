package com.themind.vishnu.Bean;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Vishnu on 09-Feb-15.
 */

@ParseClassName("VisionBoard")
public class VisionBoard extends ParseObject {


    public void setUser(ParseUser currentUser) {
        put("user", currentUser);

    }

    public ParseFile getImage() {
        return getParseFile("pictures");
    }

}