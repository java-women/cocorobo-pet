package com.java_women.cocorobopet.networks;

import android.os.AsyncTask;
import android.util.Log;

import com.java_women.cocorobopet.constants.FeelConst;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * FeelAPIの非同期処理.
 */
public class FeelApiTask extends AsyncTask<Void, Void, String> {

    private static final String API_URL = "http://javajo-api.azurewebsites.net/cocorobo-pet/api/feels/toko";

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
            Log.e(FeelConst.FEEL_TAG, "Can't get feel api.");
        }

        return result;
    }

}
