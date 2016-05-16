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
 * Created by Administrator on 5/13/2016.
 */
public class AsyncTaskFood extends AsyncTask<String, String, String> {

    OnGetFoodCompleted onGetFoodCompleted;

    public AsyncTaskFood(OnGetFoodCompleted onGetFoodCompleted){
        this.onGetFoodCompleted = onGetFoodCompleted;
    }

    public interface OnGetFoodCompleted{
        void onGetFoodCompleted(String sFood);
    }

    @Override
    protected String doInBackground(String... params) {

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("categoryid", params[0])
                .build();

        Request request = new Request.Builder()
                .url(WebServceUrl.FOOD_URL)
                .addHeader("Content-Type", "text/json; Charset=UTF-8")
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String sFood = response.body().string();
            Log.d("Check Food", sFood);

            if(sFood.substring(0, 2).equals("null")){
                Log.d("Check 02", "this is null");
            }else{
                onGetFoodCompleted.onGetFoodCompleted(sFood);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
