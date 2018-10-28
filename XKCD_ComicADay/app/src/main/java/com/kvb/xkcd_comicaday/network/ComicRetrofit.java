package com.kvb.xkcd_comicaday.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ComicRetrofit {
    public void fetchComic(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                        .url(url)
                        .build();
           client.newCall(request).enqueue(callback);
    }
}
