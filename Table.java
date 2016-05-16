package com.app.ordering.orderingsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import AsyncTask.AsyncTaskTable;
import AsyncTask.AsyncTaskLogout;
import Table.Spinner.CustomSpinnerAdapter;

public class Table extends AppCompatActivity {

    ArrayList<String> tableNumber = new ArrayList<String>();
    String tableSelected;
    Context context;

    public static final String TABLE_NO = "MyTable";

    String username;

    CustomSpinnerAdapter customSpinnerAdapter;

    Spinner spnTable;
    mehdi.sakout.fancybuttons.FancyButton btnFinishTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        context = this;
        //hide statusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("USERNAME");

        TextView txtUser = (TextView) findViewById(R.id.txtUser);
        spnTable = (Spinner) findViewById(R.id.spn_TableNumber);
        btnFinishTable = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btn_finishTable);

        txtUser.setText("Welcome : "+username);

        final AsyncTaskTable asyncTaskTable = new AsyncTaskTable(
                new AsyncTaskTable.OnGetTableCompleted(){
            @Override
            public void onGetTableCompleted(String sTable) {
                Log.d("check tableIn", sTable);
                try {
                    JSONObject jsonObject = new JSONObject(sTable);
                    JSONArray jsonArray = jsonObject.getJSONArray("tableNumber");
                    int countTable = jsonArray.length();
                    Log.d("check count Table", String.valueOf(countTable));

                    for(int i=0; i< countTable; i++){
                        tableNumber.add(i, jsonArray.getString(i));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setSpinner();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        asyncTaskTable.execute();


        spnTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tableSelected = tableNumber.get(position);
                Log.d("check click table", tableNumber.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFinishTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Finish");
                dialog.setIcon(R.drawable.checkmark);
                dialog.setCancelable(true);
                dialog.setMessage("Are you sure this Table No."+ tableSelected +" is right?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences setting = getSharedPreferences(TABLE_NO, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = setting.edit();
                        editor.putString("table", tableSelected);
                        editor.commit();

                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

    }

    public void setSpinner(){
        customSpinnerAdapter = new CustomSpinnerAdapter(context, tableNumber);
        spnTable.setAdapter(customSpinnerAdapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.drawable.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AsyncTaskLogout asyncTaskLogout = new AsyncTaskLogout(new AsyncTaskLogout.OnLogoutCompleted() {
                    @Override
                    public void onLogoutCompleted(String sMsg) {
                        Log.d("logout", sMsg);
                    }
                });
                asyncTaskLogout.execute(username);
                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
