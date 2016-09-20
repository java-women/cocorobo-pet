package com.java_women.cocorobopet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button photoButton = (Button)findViewById(R.id.photo);
        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,PhotoActivity.class);
                startActivity(intent);

            }
        });


        clickFeelButton();
    }

    private void clickFeelButton() {
        Button feelButton = (Button) findViewById(R.id.feel);
        if (feelButton != null) {
            feelButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, FeelActivity.class)));
        }
    }

}
