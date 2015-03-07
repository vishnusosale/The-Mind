package com.themind.vishnu;

import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by Vishnu on 05-Feb-15.
 */
public class LoginDispatchActivity extends ParseLoginDispatchActivity {
    @Override
    protected Class<?> getTargetClass() {
        return BusyActivity.class;
    }
}
