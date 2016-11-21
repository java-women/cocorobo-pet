package com.java_women.cocorobopet.networks;

import android.os.AsyncTask;
import android.util.Log;

import com.java_women.cocorobopet.enums.Move.MoveEnum;

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
                .url(MoveEnum.API_URL.getValue())
                .post(requestBody)
                .build();

        String result = null;

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(MoveEnum.TAG.getValue(), "Can't get move api.");
        }

        return result;
    }

}
