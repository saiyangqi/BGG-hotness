package com.example.android.bgg_hotness.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Saiyang Qi on 11/9/17.
 * A helper class to fetch data from the given url using OkHttp;
 * in our case, the result is raw Xml Data.
 */

public class NetworkUtils {

    public static String fetchData(String queryUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(queryUrl).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
