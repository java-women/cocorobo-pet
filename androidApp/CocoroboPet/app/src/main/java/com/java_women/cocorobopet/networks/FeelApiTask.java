package com.java_women.cocorobopet.networks;

import android.os.AsyncTask;
import android.util.Log;

import com.java_women.cocorobopet.enums.Feel.FeelEnum;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * FeelAPIの非同期処理.
 */
public class FeelApiTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(FeelEnum.API_URL.getValue())
                .build();

        String result = null;

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(FeelEnum.TAG.getValue(), "Can't get feel api.");
        }

        return result;
    }

}
