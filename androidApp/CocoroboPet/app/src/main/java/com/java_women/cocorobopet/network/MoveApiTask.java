package com.java_women.cocorobopet.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * COCOROBOの動作APIの非同期処理.
 */
public class MoveApiTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "MoveAPI";
    private static final String API_URL = "http://javajo-api.azurewebsites.net/cocorobo-pet/api/moves/toko";

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }
            @Override
            public void writeTo(BufferedSink sink) throws IOException {
            }
        };

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
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
