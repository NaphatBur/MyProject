package com.app.ordering.orderingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderSelected extends Fragment {

    TextView orderSe;
    mehdi.sakout.fancybuttons.FancyButton btnGotoSetFood;
    mehdi.sakout.fancybuttons.FancyButton btnGotoOrder;


    public OrderSelected() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_order_selected, container, false);
        btnGotoSetFood = (FancyButton) view.findViewById(R.id.btn_gotoSetFood);
        btnGotoOrder = (FancyButton) view.findViewById(R.id.btn_gotoOrder);

        btnGotoSetFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGotoOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Order.class);
                startActivity(i);
            }
        });

        return view;

    }

}
