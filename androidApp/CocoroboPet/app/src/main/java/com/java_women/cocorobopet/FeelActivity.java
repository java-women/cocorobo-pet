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
import com.java_women.cocorobopet.constants.FeelConst;
import com.java_women.cocorobopet.constants.MoveConst;
import com.java_women.cocorobopet.enums.FeelApiEnum;
import com.java_women.cocorobopet.models.Feel;
import com.java_women.cocorobopet.models.Move;
import com.java_women.cocorobopet.networks.FeelApiTask;
import com.java_women.cocorobopet.networks.MoveApiTask;

import java.util.Timer;
import java.util.TimerTask;

import jp.co.sharp.openapi.cocorobo.CocoroboApi;

/**
 * 目玉表示画面.
 */
public class FeelActivity extends AppCompatActivity {

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

        Toast.makeText(this, FeelConst.TOAST_TEXT, Toast.LENGTH_LONG).show();

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
        }, FeelConst.FEEL_START_MS, FeelConst.FEEL_INTERVAL_MS);
    }

    private void callMoveApiTimer() {
        moveTimer = new Timer();
        moveTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                moveHandler.post(() -> doMoveApiTask());
            }
        }, MoveConst.MOVE_START_MS, MoveConst.MOVE_INTERVAL_MS);
    }

    private void doFeelApiTask() {
        FeelApiTask feelApiTask = new FeelApiTask() {
            @Override
            protected void onPostExecute(String result) {
                Feel feel = result != null
                        ? new Gson().fromJson(result, Feel.class)
                        : new Feel(true, FeelApiEnum.NORMAL.getValue());

                if (feel.isResult()) {
                    setFeelImage(feel.getFeel());
                    Log.d(FeelConst.FEEL_TAG, feel.toString());
                }
            }
        };
        callFeelApi(feelApiTask);
    }

    private void doMoveApiTask() {
        MoveApiTask moveApiTask = new MoveApiTask() {
            @Override
            protected void onPostExecute(String result) {
                Move move = result != null
                        ? new Gson().fromJson(result, Move.class)
                        : new Move(true, MoveConst.STOP);

                if (move.isResult()) {
                    try {
                        cocoroboApi.control(MoveConst.API_KEY, move.getMoveCommand());
                    } catch (RemoteException e) {
                        Log.e(MoveConst.MOVE_TAG, "RemoteException!!!");
                    }
                }
                Log.d(MoveConst.MOVE_TAG, move.toString());
            }
        };
        callMoveApi(moveApiTask);
    }

    public void setFeelImage(String feel) {
        int imageName = FeelApiEnum.getImageFromValue(feel);
        ImageView imageView = (ImageView) findViewById(R.id.feel_image);
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
