package com.app.ordering.orderingsystem;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.gordonwong.materialsheetfab.AnimatedFab;

/**
 * Created by Administrator on 5/16/2016.
 */
public class FabMenu extends FloatingActionButton implements AnimatedFab {
    public FabMenu(Context context) {
        super(context);
    }

    @Override
    public void show() {
        show(0,0);
    }

    @Override
    public void show(float translationX, float translationY) {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(View.INVISIBLE);
    }
}
