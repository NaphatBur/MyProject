package com.app.ordering.orderingsystem;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 5/4/2016.
 */
public class AsyncTaskTable extends AsyncTask<String,String,String> {

    OnGetTableCompleted onGetTableCompleted;

    public AsyncTaskTable(OnGetTableCompleted onGetTableCompleted){
        this.onGetTableCompleted = onGetTableCompleted;
    }

    public interface OnGetTableCompleted{
        void onGetTableCompleted(String sTable);
    }

    @Override
    protected String doInBackground(String... params) {

        Request request = new Request.Builder()
                .url(WebServceUrl.TABLE_URL)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String sTable = response.body().string();

            if(sTable != null){
                onGetTableCompleted.onGetTableCompleted(sTable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
