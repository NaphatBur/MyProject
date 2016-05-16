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
 * Created by Administrator on 5/3/2016.
 */
public class AsyncTaskLogin extends AsyncTask<String,String,String>{

    public OnSetUserCompleted onSetUserCompleted = null;

    public AsyncTaskLogin(OnSetUserCompleted onSetUserCompleted){
        this.onSetUserCompleted = onSetUserCompleted;
    }

    public interface OnSetUserCompleted{
        void onTaskDone(String serviceResponse);
    }


    @Override
    protected String doInBackground(String... params) {

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("u", params[0])
                .addFormDataPart("p", params[1])
                .build();

        Request request = new Request.Builder()
                .url(WebServceUrl.LOGIN_URL)
                .addHeader("Content-Type", "text/json; Charset=UTF-8")
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String serviceResponse = response.body().string();
            Log.d("Check Service", serviceResponse);

            if(serviceResponse != null){
                onSetUserCompleted.onTaskDone(serviceResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
