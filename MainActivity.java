package com.app.ordering.orderingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import MainActivity.Adapter.FabMenu;
import MainActivity.Adapter.TwoWayPagerAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;
    TextView txtTabName;
    TextView tbTableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hide statusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //hide activity name in toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tbTableNo = (TextView) toolbar.findViewById(R.id.tlb_tableNum);
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        txtTabName = (TextView) findViewById(R.id.tlb_tabName);

        setTableNumber();
        setViewPager();
        setTitleViewPager();
        setFabMenu();
    }

    private void setViewPager(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        TwoWayPagerAdapter adapter = new TwoWayPagerAdapter(fragmentManager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void setTitleViewPager(){
        txtTabName.setText("Promotion");
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0){
                    txtTabName.setText("Promotion");
                }else if(position == 1){
                    txtTabName.setText("Set Order");
                }else if(position == 2){
                    txtTabName.setText("Order");
                }else if(position == 3){
                    txtTabName.setText("Status");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTableNumber(){
        SharedPreferences settings = getSharedPreferences(Table.TABLE_NO, Context.MODE_PRIVATE );
        String table = settings.getString("table", Table.TABLE_NO);
        tbTableNo.setText("Table Number: "+table);
    }

    private void setFabMenu(){
        FabMenu fabMenu = (FabMenu) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.white);
        int fabColor = getResources().getColor(R.color.monsoon);

        final MaterialSheetFab materialSheetFab = new MaterialSheetFab<>(fabMenu, sheetView,
                overlay, sheetColor, fabColor);

        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                materialSheetFab.showFab();
            }
        });
    }


}
