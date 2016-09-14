package com.java_women.cocorobopet;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.java_women.cocorobopet.model.Feel;
import com.java_women.cocorobopet.network.FeelApiTask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 目玉表示画面.
 */
public class FeelActivity extends AppCompatActivity {

    private static final String TAG = "FeelAPI";

    private static final String NORMAL = "normal";
    private static final String WAAI = "waai";
    private static final String MATTEE = "mattee";
    private static final String SHOBOON = "shoboon";
    private static final String KIRAAN = "kiraan";
    private static final String SUKII = "sukii";
    private static final String MUKII = "mukii";

    private static final String TOAST_TEXT = "端末の戻るボタンで前の画面に戻れます";

    private static final int START_MS = 0;
    private static final int INTERVAL_MS = 10000;

    private Handler handler = new Handler();
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feel);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
        callApiTimer();
    }

    private void callApiTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                handler.post(() -> doFeelApiTask());
            }
        }, START_MS, INTERVAL_MS);
    }

    private void doFeelApiTask() {
        FeelApiTask feelApiTask = new FeelApiTask() {
            @Override
            protected void onPostExecute(String result) {
                Feel feel = result != null ? convertFeelFromJson(result) : new Feel(true, NORMAL);

                if (feel.isResult()) {
                    setFeelImage(feel.getFeel());
                    Log.d(TAG, feel.toString());
                }
            }
        };
        callFeelApi(feelApiTask);
    }

    private Feel convertFeelFromJson(String json) {
        return new Gson().fromJson(json, Feel.class);
    }

    public void setFeelImage(String feel) {
        int imageName = R.drawable.eye_sample1;

        switch (feel) {
            case NORMAL:
                imageName = R.drawable.eye_sample1;
                break;
            case WAAI:
                imageName = R.drawable.eye_sample2;
                break;
            case MATTEE:
                imageName = R.drawable.eye_sample3;
                break;
            case SHOBOON:
                imageName = R.drawable.eye_sample4;
                break;
            case KIRAAN:
                imageName = R.drawable.eye_sample5;
                break;
            case SUKII:
                imageName = R.drawable.eye_sample2;
                break;
            case MUKII:
                imageName = R.drawable.eye_sample3;
                break;
        }

        ImageView imageView = (ImageView)findViewById(R.id.feel_image);
        if (imageView != null) {
            imageView.setImageResource(imageName);
        }
    }

    private void callFeelApi(FeelApiTask feelApiTask) {
        feelApiTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelTimer();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        callApiTimer();
    }

}
