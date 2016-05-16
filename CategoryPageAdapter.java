package com.app.ordering.orderingsystem;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 5/8/2016.
 */
public class CategoryPageAdapter extends PagerAdapter {

    ArrayList<ViewGroup> views;
    LayoutInflater inflater;
    int btnPosition;
    ArrayList<Integer> layoutFood = new ArrayList<Integer>();

    ArrayList<String> foodName = new ArrayList<String>();
    ArrayList<String> foodLocalName = new ArrayList<String>();
    ArrayList<String> foodPrice = new ArrayList<String>();
    ArrayList<String> foodPic = new ArrayList<String>();

    LinearLayout[] layoutChild = new LinearLayout[5];

    Context context;
    int i=0;

    public CategoryPageAdapter(Context ctx, int btnPosition, ArrayList<Integer> layoutFood,
                               ArrayList<String> foodName,
                               ArrayList<String> foodPrice,
                               ArrayList<String> foodPic){
        inflater=LayoutInflater.from(ctx);

        //เท่ากับจำนวนหน้า food
        views=new ArrayList<ViewGroup>(5);

        this.btnPosition = btnPosition;
        this.layoutFood = layoutFood;
        context = ctx;

        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodPic = foodPic;
    }

    public void release(){
        views.clear();
        views=null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //จำนวนหน้าของอาหารแต่ละประเภท
        LinearLayout[] layout = new LinearLayout[3];
        layout[0] = new LinearLayout(context);
        layout[1] = new LinearLayout(context);
        layout[2] = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layout[0].setLayoutParams(lp);
        layout[0].setBackground(context.getResources().getDrawable(R.color.colorAccent));
        layout[1].setLayoutParams(lp);
        layout[1].setBackground(context.getResources().getDrawable(R.color.lightGreen));
        layout[2].setLayoutParams(lp);
        layout[2].setBackground(context.getResources().getDrawable(R.color.lightYellow));

        Log.d("chek click", "position: "+btnPosition);

        ViewGroup currentView = null;
        if(views.size()>position&&views.get(position)!=null){
            currentView=views.get(position);
        }else{
            int rootLayout=layoutFood.get(btnPosition);
            Log.d("chek view", String.valueOf(rootLayout));
            currentView= (ViewGroup) inflater.inflate(rootLayout,container,false);
            //((TextView)currentView.findViewById(R.id.txtTitle)).setText("My Views "+btnPosition);

            if(rootLayout == R.layout.category_layout01){
                //setPicFood(layout);

                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.food_icon_new));
                layout[0].addView(img);
                ImageView img2 = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.tables));
                layout[1].addView(img2);

                //ลูปเพิ่มหน้าอาหารเข้าไปใน view
                while(position == i){
                    currentView.addView(layout[i]);
                    i++;
                }



                container.addView(currentView);
                Log.d("chek position", "pos:" + position);

            }/*else if(rootLayout == R.layout.category_layout02){



                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.tables));


                layout.addView(img);


            }else if(rootLayout == R.layout.category_layout03){
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.food_icon_new));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout04) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.checkmark));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout05) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.dropbox));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout06) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.github));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout07) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.facebook));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout08) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.gplus));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout09) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.soundcloud));

                layout.addView(img);
            }else if(rootLayout == R.layout.category_layout10) {
                ImageView img = new ImageView(context);
                img.setBackground(context.getResources().getDrawable(R.drawable.user));

                layout.addView(img);
            }*/

        }

        return currentView;
    }

    @Override
    public int getCount() {
        int rootLayout=layoutFood.get(btnPosition);
        int numPage;

        if(rootLayout == R.layout.category_layout01){

        }else if(rootLayout == R.layout.category_layout02){

        }else if(rootLayout == R.layout.category_layout03){

        }else if(rootLayout == R.layout.category_layout04) {

        }else if(rootLayout == R.layout.category_layout05) {

        }else if(rootLayout == R.layout.category_layout06) {

        }else if(rootLayout == R.layout.category_layout07) {

        }else if(rootLayout == R.layout.category_layout08) {

        }else if(rootLayout == R.layout.category_layout09) {

        }else if(rootLayout == R.layout.category_layout10) {

        }

        return foodName.size();
    }

    public void setPicFood(LinearLayout layout){

        LinearLayout.LayoutParams lp02 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i=0; i<foodName.size();i++) {
            layoutChild[i] = new LinearLayout(context);
            layoutChild[i].setLayoutParams(lp02);
        }

        ImageView img = new ImageView(context);
        img.setBackground(context.getResources().getDrawable(R.drawable.add));
        ImageView img2 = new ImageView(context);
        img.setBackground(context.getResources().getDrawable(R.drawable.user));
        ImageView img3 = new ImageView(context);
        img.setBackground(context.getResources().getDrawable(R.drawable.soundcloud));
        layoutChild[0].addView(img);
        layoutChild[1].addView(img2);
        layoutChild[2].addView(img3);
        layout.addView(layoutChild[0]);
        layout.addView(layoutChild[1]);
        layout.addView(layoutChild[2]);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((View)object);
    }
}
