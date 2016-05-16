package com.app.ordering.orderingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private mehdi.sakout.fancybuttons.FancyButton btnSubmit;
    String username, password;
    Context context;
    String uName;
    String uActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        context = this;
        //hide statusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSubmit = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = edtUsername.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

                login();

                AsyncTaskLogin asyncTaskLogin = new AsyncTaskLogin(
                        new AsyncTaskLogin.OnSetUserCompleted() {

                    @Override
                    public void onTaskDone(String serviceResponse) {
                        Log.d("Check interface", serviceResponse);
                        try {
                            JSONObject jsonObject = new JSONObject(serviceResponse);
                            uName = jsonObject.getString("username");
                            uActive = jsonObject.getString("active");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                asyncTaskLogin.execute(username, password);
            }
        });

    }

    public void login(){
        if(!validate()){
            onLoginFailed();
            return;
        }

        btnSubmit.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onLogInSuccess();
                        progressDialog.dismiss();
                    }
                },3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    public void onLogInSuccess(){
        btnSubmit.setEnabled(true);
        if(uName.equals("null")) {
            edtPassword.setError("Password Incorrect");
            edtUsername.setError("Username Incorrect");

        /*}else if(uActive.equals("1")){
            edtUsername.setError("Username still login");
            edtPassword.setError("Username still login");*/
        }else{
            Intent i = new Intent(context, Table.class);
            i.putExtra("USERNAME", uName);
            startActivity(i);
            finish();
        }
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnSubmit.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;

        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if(username.isEmpty()){
            edtUsername.setError("Please Enter Username");
        }else{
            edtUsername.setError(null);
        }

        if(password.isEmpty() || password.length() < 8 || password.length() > 15){
            edtPassword.setError("between 8 and 15 characters");
            valid = false;
        }else{
            edtPassword.setError(null);
        }

        return valid;
    }



}
