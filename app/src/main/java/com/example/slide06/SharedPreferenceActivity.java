package com.example.slide06;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferenceActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        editText = findViewById(R.id.shared_preference_edit_text);
    }

    public void sharedPreferenceSaveData(View view) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences
                ("My pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String tmp = editText.getText().toString();
        editor.putString(getString(R.string.Data_Key), tmp);
        editor.commit();
    }

    public void sharedPreferenceLoadData(View view) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences
                ("My pref", Context.MODE_PRIVATE);

        String data = sharedPreferences.getString(getString(R.string.Data_Key),getString(R.string.no_data));
        editText.setHint(data);
        editText.setText(null);
    }

}
