package com.app.ordering.orderingsystem;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 5/6/2016.
 */
public class AsyncTaskLogout extends AsyncTask<String, String, String> {

    OnLogoutCompleted onLogoutCompleted;

    public AsyncTaskLogout(OnLogoutCompleted onLogoutCompleted){
        this.onLogoutCompleted = onLogoutCompleted;
    }

    public interface OnLogoutCompleted{
        void onLogoutCompleted(String sMsg);
    }

    @Override
    protected String doInBackground(String... params) {
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("u", params[0])
                .build();

        Request request = new Request.Builder()
                .url(WebServceUrl.LOGIN_URL)
                .addHeader("Content-Type", "text/json; Charset=UTF-8")
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String sMsg = response.body().string();
            Log.d("Check Logout", sMsg);

            if(sMsg != null){
                onLogoutCompleted.onLogoutCompleted(sMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
