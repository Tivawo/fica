package com.domain.fica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class UserListActivity extends AppCompatActivity {
    //Attributes
    private final String TAG = "UserListActivity";

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
    }
}
