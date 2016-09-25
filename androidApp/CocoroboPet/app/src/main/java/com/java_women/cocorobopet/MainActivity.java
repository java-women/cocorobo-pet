package com.java_women.cocorobopet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * menu画面.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //さつえいモードへ
        Button photoButton = (Button) findViewById(R.id.photo);
        if (photoButton != null){
            photoButton.setOnClickListener(view ->
                    startActivity(new Intent(MainActivity.this, PhotoActivity.class))
            );
        }
        clickFeelButton();
    }

    private void clickFeelButton() {
        Button feelButton = (Button) findViewById(R.id.feel);
        if (feelButton != null) {
            feelButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, FeelActivity.class)));
        }

    }
}
