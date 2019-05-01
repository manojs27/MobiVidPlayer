package com.mobiplayer.mobiplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobiplayer.mobiplayer.R;
import com.mobiplayer.mobiplayer.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();

        }
    }
}