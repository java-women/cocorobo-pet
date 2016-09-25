package com.java_women.cocorobopet;

import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.java_women.cocorobopet.model.Feel;
import com.java_women.cocorobopet.model.Move;
import com.java_women.cocorobopet.network.FeelApiTask;
import com.java_women.cocorobopet.network.MoveApiTask;

import java.util.Timer;
import java.util.TimerTask;

import jp.co.sharp.openapi.cocorobo.CocoroboApi;

/**
 * 目玉表示画面.
 */
public class FeelActivity extends AppCompatActivity {

    private static final String FEEL_TAG = "FeelAPI";
    private static final String MOVE_TAG = "MoveAPI";

    private static final String NORMAL = "normal";
    private static final String WAAI = "waai";
    private static final String MATTEE = "mattee";
    private static final String SHOBOON = "shoboon";
    private static final String KIRAAN = "kiraan";
    private static final String SUKII = "sukii";
    private static final String MUKII = "mukii";

    private static final String STOP = "stop";

    private static final String TOAST_TEXT = "端末の戻るボタンで前の画面に戻れます";

    private static final int FEEL_START_MS = 0;
    private static final int FEEL_INTERVAL_MS = 10000;
    private static final int MOVE_START_MS = 2000;
    private static final int MOVE_INTERVAL_MS = 5000;

    private static final String API_KEY = "api key";

    private Handler feelHandler = new Handler();
    private Handler moveHandler = new Handler();
    private Timer feelTimer;
    private Timer moveTimer;

    CocoroboApi cocoroboApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feel);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        cocoroboApi = new CocoroboApi(getApplicationContext());

        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();

        callFeelApiTimer();
        callMoveApiTimer();
    }

    private void callFeelApiTimer() {
        feelTimer = new Timer();
        feelTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                feelHandler.post(() -> doFeelApiTask());
            }
        }, FEEL_START_MS, FEEL_INTERVAL_MS);
    }

    private void callMoveApiTimer() {
        moveTimer = new Timer();
        moveTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                moveHandler.post(() -> doMoveApiTask());
            }
        }, MOVE_START_MS, MOVE_INTERVAL_MS);
    }

    private void doFeelApiTask() {
        FeelApiTask feelApiTask = new FeelApiTask() {
            @Override
            protected void onPostExecute(String result) {
                Feel feel = result != null ? convertFeelFromJson(result) : new Feel(true, NORMAL);

                if (feel.isResult()) {
                    setFeelImage(feel.getFeel());
                    Log.d(FEEL_TAG, feel.toString());
                }
            }
        };
        callFeelApi(feelApiTask);
    }

    private void doMoveApiTask() {
        MoveApiTask moveApiTask = new MoveApiTask() {
            @Override
            protected void onPostExecute(String result) {
                Move move = result != null ? convertMoveFromJson(result) : new Move(true, STOP);

                if (move.isResult()) {
                    try {
                        cocoroboApi.control(API_KEY, move.getMoveCommand());
                    } catch (RemoteException e) {
                        Log.e(MOVE_TAG, "RemoteException!!!");
                    }
                }
                Log.e(MOVE_TAG, move.toString());
            }
        };
        callMoveApi(moveApiTask);
    }

    private Feel convertFeelFromJson(String json) {
        return new Gson().fromJson(json, Feel.class);
    }

    private Move convertMoveFromJson(String json) {
        return new Gson().fromJson(json, Move.class);
    }

    public void setFeelImage(String feel) {
        int imageName = R.drawable.normal;

        switch (feel) {
            case NORMAL:
                imageName = R.drawable.normal;
                break;
            case WAAI:
                imageName = R.drawable.waai;
                break;
            case MATTEE:
                imageName = R.drawable.mattee;
                break;
            case SHOBOON:
                imageName = R.drawable.shoboon;
                break;
            case KIRAAN:
                imageName = R.drawable.kiraan;
                break;
            case SUKII:
                imageName = R.drawable.sukii;
                break;
            case MUKII:
                imageName = R.drawable.mukii;
                break;
        }

        ImageView imageView = (ImageView)findViewById(R.id.feel_image);
        if (imageView != null) {
            imageView.setImageResource(imageName);
        }
    }

    private void callMoveApi(MoveApiTask moveApiTask) {
        moveApiTask.execute();
    }

    private void callFeelApi(FeelApiTask feelApiTask) {
        feelApiTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelFeelTimer();
        cancelMoveTimer();
    }

    private void cancelFeelTimer() {
        if (feelTimer != null) {
            feelTimer.cancel();
        }
    }

    private void cancelMoveTimer() {
        if (moveTimer != null) {
            moveTimer.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        callFeelApiTimer();
        callMoveApiTimer();
    }

}
