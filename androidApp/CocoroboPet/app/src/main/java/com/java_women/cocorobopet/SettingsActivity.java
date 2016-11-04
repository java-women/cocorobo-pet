package com.java_women.cocorobopet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.java_women.cocorobopet.constants.FeelConst;

/**
 * 設定画面.
 * Created by aya on 2016/11/04.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setApiKey();
        clickSubmitButton();
    }

    private void setApiKey() {
        String apiKey = getSharedPreferences(FeelConst.API_PREF_KEY, Context.MODE_PRIVATE)
                .getString(FeelConst.API_PREF_KEY, "");
        EditText editText = (EditText) findViewById(R.id.api_key);
        if (editText != null) {
            editText.setText(apiKey);
        }
    }

    private void clickSubmitButton() {
        Button submitButton = (Button) findViewById(R.id.submit);
        if (submitButton != null) {
            submitButton.setOnClickListener(view -> saveApiKey());
        }
    }

    private void saveApiKey() {
        getSharedPreferences(FeelConst.API_PREF_KEY, Context.MODE_PRIVATE)
                .edit()
                .putString(FeelConst.API_PREF_KEY, getApiKeyText())
                .apply();

        Toast.makeText(this, R.string.submit_toast_text, Toast.LENGTH_LONG).show();
    }

    private String getApiKeyText() {
        EditText editText = (EditText) findViewById(R.id.api_key);
        return editText != null ? editText.getText().toString() : "";
    }
}
