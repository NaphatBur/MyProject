package com.app.ordering.orderingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AsyncTask.AsyncTaskCategory;
import AsyncTask.AsyncTaskFood;
import MainActivity.Adapter.FabMenu;

public class Order extends AppCompatActivity {
    //Category
    ArrayList<String> cateName = new ArrayList<String>();
    ArrayList<String> catePicUrl = new ArrayList<String>();
    ArrayList<String> cateId = new ArrayList<String>();
    ArrayList<Integer> layoutF = new ArrayList<Integer>();
    //Food
    ArrayList<String> foodName = new ArrayList<String>();
    ArrayList<String> foodLocalName = new ArrayList<String>();
    ArrayList<String> foodPrice = new ArrayList<String>();
    ArrayList<String> foodPic = new ArrayList<String>();

    ArrayList<LinearLayout> listLayoutFood = new ArrayList<LinearLayout>();

    LinearLayout layout_cate;
    LinearLayout layout_food;

    ViewPager pager;
    TabLayout tabLayout;

    Context context;

    LinearLayout[] cateLayout = new LinearLayout[15];
    TextView[] txtCateName = new TextView[15];
    ImageView[] imgCateBtn = new ImageView[15];
    ImageView[] ImageView = new ImageView[15];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //hide statusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide activity name in toolbar
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        context = this;
        setUpToolbar();
        getCateFromService();
        setFabMenu();


        layout_cate = (LinearLayout) findViewById(R.id.layout_cate);
        layout_food= (LinearLayout) findViewById(R.id.layout_food);
        pager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tbTableNo = (TextView) toolbar.findViewById(R.id.tlb_tableNum);

        SharedPreferences settings = getSharedPreferences(Table.TABLE_NO, Context.MODE_PRIVATE );
        final String table = settings.getString("table", Table.TABLE_NO);

        tbTableNo.setText("Table Number: "+table);
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

                            int countCate = jArrayCateName.length();
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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setBtnCate(cateName, catePicUrl, cateId);

                            }
                        });

                    }
                });
        asyncTaskCategory.execute();
    }

    private void setFabMenu(){
        FabMenu fabMenu = (FabMenu) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.white);
        int fabColor = getResources().getColor(R.color.monsoon);

        final MaterialSheetFab materialSheetFab = new MaterialSheetFab<>(
                fabMenu,
                sheetView,
                overlay,
                sheetColor,
                fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                materialSheetFab.showFab();
            }
        });
    }

    //ทำปุ่มให้มัน รองรับตอนลบจากหลังร้าน
    private void setBtnCate(ArrayList<String> cateName, ArrayList<String> catePicUrl,
                           ArrayList<String> cateId){
        ArrayList<String> cName = new ArrayList<String>();
        ArrayList<String> cPic = new ArrayList<String>();
        cName = cateName;
        cPic = catePicUrl;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;

        for (int i=0; i<cName.size(); i++) {
            cateLayout[i] = new LinearLayout(context);
            cateLayout[i].setOrientation(LinearLayout.VERTICAL);
            cateLayout[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            cateLayout[i].getLayoutParams().width = 160;
            cateLayout[i].getLayoutParams().height = 170;


            txtCateName[i] = new TextView(context);
            txtCateName[i].setLayoutParams(lp);
            txtCateName[i].setGravity(Gravity.CENTER);
            txtCateName[i].setText(cName.get(i));

            imgCateBtn[i] = new ImageView(context);
            imgCateBtn[i].setLayoutParams(lp);
            Picasso.with(context)
                    .load(cPic.get(i))
                    .resize(100, 100)
                    .transform(new CircleTransform())
                    .into(imgCateBtn[i]);

            cateLayout[i].addView(imgCateBtn[i]);
            cateLayout[i].addView(txtCateName[i]);
            layout_cate.addView(cateLayout[i]);

            imgCateBtn[i].setOnTouchListener(new CateBtnClicked02(i, cateId.get(i)));
        }
    }


    class CateBtnClicked02 implements View.OnTouchListener{
        int btnId;
        String categoryId;

        CateBtnClicked02(int id, String cateId){
            btnId = id;
            categoryId = cateId;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                imgCateBtn[btnId].setColorFilter(getResources().getColor(R.color.iron));
                //txtCateName[btnId].setTextColor(getResources().getColor(R.color.blueTwitter));
                Log.d("cate position", String.valueOf(btnId)+ "/" +categoryId);

                getFoodFromService(btnId, categoryId);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                imgCateBtn[btnId].clearColorFilter();
            }

            return true;
        }
    }

    private void getFoodFromService(final int btnId, String categoryId){
        AsyncTaskFood asyncTaskFood = new AsyncTaskFood(
                new AsyncTaskFood.OnGetFoodCompleted() {
                    @Override
                    public void onGetFoodCompleted(String sFood) {

                        try {
                            JSONObject jFood = new JSONObject(sFood);
                            JSONObject jFObject = jFood.getJSONObject("product");
                            JSONArray jFoodName = jFObject.getJSONArray("productName");
                            JSONArray jFoodLocalName = jFObject.getJSONArray("productLocalName");
                            JSONArray jFoodPrice = jFObject.getJSONArray("productPrice");
                            JSONArray jFoodPic = jFObject.getJSONArray("productPicturePath");

                            int countFood = jFoodName.length();

                            if(foodName.size() == 0){
                                for(int i=0; i<countFood; i++){
                                    foodName.add(i, jFoodName.getString(i));
                                    foodLocalName.add(i, jFoodLocalName.getString(i));
                                    foodPrice.add(i, jFoodPrice.getString(i));
                                    foodPic.add(i, jFoodPic.getString(i));
                                }
                            }else {
                                foodName.clear();
                                foodLocalName.clear();
                                foodPic.clear();
                                foodPrice.clear();

                                for(int i=0; i<countFood; i++){
                                    foodName.add(i, jFoodName.getString(i));
                                    foodLocalName.add(i, jFoodLocalName.getString(i));
                                    foodPrice.add(i, jFoodPrice.getString(i));
                                    foodPic.add(i, jFoodPic.getString(i));
                                }
                            }

                            Log.d("Check food count", String.valueOf(foodName.size()));



                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //addLayoutFood(btnId, foodName, foodPrice, foodPic);

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        asyncTaskFood.execute(categoryId);
    }

    private void setLayoutFood(ImageView imgFood){
        LinearLayout layoutFood = new LinearLayout(context);
        LinearLayout.LayoutParams lpFood = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        layoutFood.setLayoutParams(lpFood);
        layoutFood.setOrientation(LinearLayout.HORIZONTAL);
        layoutFood.addView(imgFood);
        layout_food.addView(layoutFood);
    }

    private void setFoodImage(ArrayList<String> foodPic){
        ImageView[] imgFoodBtn = new ImageView[foodPic.size()];

        for (int i=0; i<foodPic.size(); i++) {
            Picasso.with(context)
                    .load(foodPic.get(i))
                    .resize(200, 100)
                    .into(imgFoodBtn[i]);
        }


    }

    private void addLayoutFood(int btnId, ArrayList<String> foodName, ArrayList<String> foodPrice,
                              ArrayList<String> foodPic){

        /*pagerAdapter = new CategoryPageAdapter(context, btnId, layoutF, foodName, foodPrice, foodPic);
        pager.setAdapter(pagerAdapter);
        pager.setClipToPadding(false);
        pager.setPageMargin(12);
        //หาร หาจำนวนหน้า จาก food แล้วเอามาใส่ตรงนี้
        pager.setOffscreenPageLimit(3);*/
    }


}


/*public void setTabCate(){
        List<Fragment> fragments = getFragments();
        CategoryPageAdapter categoryPageAdapter = new CategoryPageAdapter(getSupportFragmentManager(), cateName, fragments);
        pager.setAdapter(categoryPageAdapter);

        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(categoryPageAdapter);
    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(fragCategory01.newInstance());
        fList.add(fragCategory02.newInstance());
        fList.add(fragCategory03.newInstance());
        fList.add(fragCategory04.newInstance());
        fList.add(fragCategory05.newInstance());
        fList.add(fragCategory06.newInstance());
        fList.add(fragCategory07.newInstance());
        fList.add(fragCategory08.newInstance());
        fList.add(fragCategory09.newInstance());
        fList.add(fragCategory10.newInstance());

        Log.d("c list", String.valueOf(fList.size()));

        return fList;
    }*/
