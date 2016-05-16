package com.app.ordering.orderingsystem;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsyncTask.AsyncTaskCategory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ordered extends Fragment {

    ArrayList<String> cateName = new ArrayList<String>();
    ArrayList<String> catePicUrl = new ArrayList<String>();
    ArrayList<String> cateId = new ArrayList<String>();

    LinearLayout layout_cate;
    LinearLayout layout_food;

    Context context;
    int countCate;

    public Ordered() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordered, container, false);
        context = getContext();
        layout_cate = (LinearLayout) view.findViewById(R.id.layout_cate);
        layout_food = (LinearLayout) view.findViewById(R.id.layout_food);
        getCateFromService();
        return view;
    }

    private void getCateFromService(){
        AsyncTaskCategory asyncTaskCategory = new AsyncTaskCategory
                (new AsyncTaskCategory.OnGetCategoryCompleted() {
                    @Override
                    public void onGetCategoryCompleted(final String sCategory) {
                        Log.d("c_category In", sCategory);

                        try {
                            JSONObject jsonObject = new JSONObject(sCategory);
                            JSONObject jObject = jsonObject.getJSONObject("category");
                            JSONArray jArrayCateName = jObject.getJSONArray("categoryName");
                            JSONArray jArrayCatePic = jObject.getJSONArray("catePicturePath");
                            JSONArray jArrayCateId = jObject.getJSONArray("categoryId");

                            countCate = jArrayCateName.length();
                            int countCatePic = jArrayCatePic.length();
                            Log.d("check count cate", String.valueOf(countCate +"/" +countCatePic));

                            for(int i=0; i<countCate; i++){
                                cateName.add(i, jArrayCateName.getString(i));
                                catePicUrl.add(i, jArrayCatePic.getString(i));
                                cateId.add(i, jArrayCateId.getString(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCardViewFromCategory(countCate);
                            }
                        });

                    }
                });
        asyncTaskCategory.execute();
    }

    private void setCardViewFromCategory(int countCate){
        CardView[] cardCate = new CardView[countCate];
        TextView[] txtCateName = new TextView[countCate];
        LinearLayout[] layoutFoodCard = new LinearLayout[countCate];
        ImageView[] imgCate = new ImageView[countCate];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams paramsLayoutFood = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        for (int i=0;i<countCate;i++){
            cardCate[i] = new CardView(context);
            cardCate[i].setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
            cardCate[i].setRadius(4);
            cardCate[i].setContentPadding(10,10,10,8);
            cardCate[i].setCardElevation(9);


            layoutFoodCard[i] = new LinearLayout(context);
            layoutFoodCard[i].setLayoutParams(paramsLayoutFood);
            layoutFoodCard[i].setOrientation(LinearLayout.VERTICAL);

            imgCate[i] = new ImageView(context);
            imgCate[i].setLayoutParams(params);
            Picasso.with(context)
                    .load(catePicUrl.get(i))
                    .resize(45, 45)
                    .transform(new CircleTransform())
                    .into(imgCate[i]);

            txtCateName[i] = new TextView(context);
            txtCateName[i].setLayoutParams(params);
            txtCateName[i].setText(cateName.get(i));
            txtCateName[i].setTextSize(11);

            layoutFoodCard[i].addView(imgCate[i]);
            layoutFoodCard[i].addView(txtCateName[i]);
            cardCate[i].addView(layoutFoodCard[i]);


            layout_food.addView(cardCate[i]);

            cardCate[i].setOnClickListener(new setOnCardClicked(i));
        }
    }

    private class setOnCardClicked implements View.OnClickListener{

        int position;

        public setOnCardClicked(int i) {
            position = i;
        }

        @Override
        public void onClick(View v) {
            Log.d("position", String.valueOf(position));
        }
    }

}
