package com.example.slide06;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
    }

    public void saveCache(View view) {
        File file = new File(getApplicationContext().getFilesDir(), "SAVE_DATA");
    }

    public void loadCache(View view) {

    }

    public void savePDF(View view) {

    }
}
