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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsyncTask.AsyncTaskCategory;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;


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
                                //setCustomCard();
                            }
                        });

                    }
                });
        asyncTaskCategory.execute();
    }

    private void setCardViewFromCategory(int countCate){
        //CardView[] cardCate = new CardView[countCate];
        CardViewNative[] cardCate = new CardViewNative[countCate];
        Card[] card = new Card[countCate];
        TextView[] txtCateName = new TextView[countCate];
        LinearLayout[] layoutFoodCard = new LinearLayout[countCate];
        ImageView[] imgCate = new ImageView[countCate];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams paramsLayoutCate = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        for (int i=0;i<countCate;i++){
            cardCate[i] = new CardViewNative(context);
            cardCate[i].setLayoutParams(new RelativeLayout.LayoutParams(100, 85));
            cardCate[i].setRadius(4);
            cardCate[i].setContentPadding(0,0,0,0);
            cardCate[i].setCardElevation(9);

            card[i] = new Card(context);

            layoutFoodCard[i] = new LinearLayout(context);
            layoutFoodCard[i].setLayoutParams(paramsLayoutCate);
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
            txtCateName[i].setPadding(0,3,0,0);
            txtCateName[i].setGravity(Gravity.CENTER);

            card[i].setOnClickListener(new setOnCardClicked(i));

            cardCate[i].setCard(card[i]);
            layoutFoodCard[i].addView(imgCate[i]);
            layoutFoodCard[i].addView(txtCateName[i]);

            cardCate[i].addView(layoutFoodCard[i]);

            layout_food.addView(cardCate[i]);

        }
    }


    private class setOnCardClicked implements Card.OnCardClickListener{

        int position;

        public setOnCardClicked(int i) {
            position = i;
        }

        @Override
        public void onClick(Card card, View view) {
            Log.d("position", String.valueOf(position));

        }

    }

    private void setFoodLayout(int position){
        LinearLayout[] lFood = new LinearLayout[cateName.size()];

        LinearLayout.LayoutParams paramsLayoutFood = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        for(int i=0; i<cateName.size(); i++){
            lFood[i] = new LinearLayout(context);
            lFood[i].setLayoutParams(paramsLayoutFood);
            lFood[i].setOrientation(LinearLayout.HORIZONTAL);
        }
    }

    private void setCustomCard(){

        CardViewNative[] cardCate = new CardViewNative[countCate];

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        //params.gravity = Gravity.CENTER;

        cardCate[0] = new CardViewNative(context);
        cardCate[0].setLayoutParams(params);
        cardCate[0].setRadius(4);
        //cardCate[i].setContentPadding(10,10,10,8);
        cardCate[0].setCardElevation(9);



        final ImageView[] imgCate = new ImageView[countCate];
        imgCate[0] = new ImageView(context);
        imgCate[0].setLayoutParams(params);

        MaterialLargeImageCard card = MaterialLargeImageCard.with(context)
                .setTextOverImage(cateName.get(0))
                .setTitle(cateName.get(0))
                .setSubTitle(cateName.get(0))
                .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                    @Override
                    public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                        Picasso.with(getActivity()).setIndicatorsEnabled(true);  //only for debug tests
                        Picasso.with(getActivity())
                                .load("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg")
                                .into((ImageView) viewImage);
                    }
                })
                .build();

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity()," Click on ActionArea ", Toast.LENGTH_SHORT).show();
            }
        });

        cardCate[0].setCard(card);
        layout_food.addView(cardCate[0]);
    }

}
