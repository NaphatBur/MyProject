package com.app.ordering.orderingsystem;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 5/6/2016.
 */
public class AsyncTaskCategory extends AsyncTask<String, String, String> {

    OnGetCategoryCompleted onGetCategoryCompleted;

    public AsyncTaskCategory(OnGetCategoryCompleted onGetCategoryCompleted){
        this.onGetCategoryCompleted = onGetCategoryCompleted;
    }

    public interface OnGetCategoryCompleted{
        void onGetCategoryCompleted(String sCategory);
    }

    @Override
    protected String doInBackground(String... params) {

        Request request = new Request.Builder()
                .url(WebServceUrl.CATEGORY_URL)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String sCategory = response.body().string();

            if(sCategory != null){
                onGetCategoryCompleted.onGetCategoryCompleted(sCategory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
