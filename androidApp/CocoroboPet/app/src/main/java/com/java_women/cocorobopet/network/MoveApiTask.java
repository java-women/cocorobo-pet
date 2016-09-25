package com.java_women.cocorobopet.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * COCOROBOの動作APIの非同期処理.
 */
public class MoveApiTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "MoveAPI";
    private static final String API_URL = "http://javajo-erk5.azurewebsites.net/cocorobo-pet/api/moves/toko";

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        String result = null;

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "Can't get move api.");
        }

        return result;
    }

}
