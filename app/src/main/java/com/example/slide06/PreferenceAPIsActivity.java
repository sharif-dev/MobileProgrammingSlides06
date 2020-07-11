package com.example.slide06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PreferenceAPIsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_apis);

        Button but2 = (Button) findViewById(R.id.button1);
        but2.setOnClickListener(new View.OnClickListener() {

            Intent in = new Intent("com.example.slide06.Pref");

            @Override
            public void onClick(View v) {
                startActivity(in);
            }
        });
    }

}
