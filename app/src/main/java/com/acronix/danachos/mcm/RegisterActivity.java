package com.acronix.danachos.mcm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acronix.danachos.mcm.apis.ApiClient;
import com.acronix.danachos.mcm.apis.StoreEndPoint;
import com.acronix.danachos.mcm.models.RegisterResponse;
import com.acronix.danachos.mcm.utils.PopupUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {



    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private EditText etToken;
    private Button btnRegister;
    private Button btnLogback;
    private String CheckShared="none";
    private TextView tvTokenShared;
    private LinearLayout regResultLay;
    private LinearLayout regLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //initialize inputs
        etUsername= (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword=(EditText) findViewById(R.id.etConfirmPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etToken = (EditText) findViewById(R.id.etToken);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogback = (Button) findViewById(R.id.btnLogback);
        tvTokenShared = (TextView) findViewById(R.id.tvTokenShared);
        regResultLay = (LinearLayout) findViewById(R.id.regResultLay);
        regLay = (LinearLayout) findViewById(R.id.regLay);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                dooRegister();
            }
        });


        btnLogback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


        //setup first load
        regLay.setVisibility(View.VISIBLE);
        regResultLay.setVisibility(View.GONE);



    }


    public void login (View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void dooRegister(){


        //variables

        String strUsername=etUsername.getText().toString();
        String strPassword=etPassword.getText().toString();
        String strConfirmPassword=etConfirmPassword.getText().toString();
        String strEmail=etEmail.getText().toString();
        String strToken=etToken.getText().toString();


        if(strUsername.isEmpty()){
            etUsername.setError("Please fill username.");
            return;

        }
        if(strPassword.isEmpty()){
            etPassword.setError("Please fill Password");
            return;
        }
        if(strConfirmPassword.isEmpty()){
            etConfirmPassword.setError("Please fill Confirm Password");
            return;
        }

        if(strEmail.isEmpty()){
            etEmail.setError("Please fill Email");
            return;
        }

        boolean matchPass= strPassword.equals(strConfirmPassword);
        if(!matchPass){

            Toast.makeText(this,"Password does not match, please retry..", Toast.LENGTH_SHORT).show();
            return;

        }



        PopupUtil.showLoading(this,"Saving Please Wait...");
//
        StoreEndPoint endPoint= ApiClient.getClient().create(StoreEndPoint.class);
        Call<RegisterResponse> callRegister=endPoint.postRegister(strUsername,strEmail,strPassword,strToken,CheckShared);

        callRegister.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                PopupUtil.dismiss();

                RegisterResponse registerResponse=response.body();

                if(registerResponse.getSuccess()){

                    tvTokenShared.setVisibility(View.GONE);

                    final String strResponToken=registerResponse.getTokenShared().toString();

                    final String strTokenValidation=etToken.getText().toString();

                    if(strResponToken.equals("no")){
                        //do nothing

                        regLay.setVisibility(View.GONE);
                        regResultLay.setVisibility(View.VISIBLE);

                    }
                    else{

                        if(strTokenValidation.isEmpty()){

                            tvTokenShared.setText("Your shared Token use this code if you want share expense\n" +strResponToken);
                            tvTokenShared.setVisibility(View.VISIBLE);

                        }
                        else{
                            //do nothing

                        }



                        regLay.setVisibility(View.GONE);
                        regResultLay.setVisibility(View.VISIBLE);

                    }

                }
                else{

                    final String strTokenValidation=etToken.getText().toString();

                    if(strTokenValidation.isEmpty()){
                        //do nothing something must be wrong with the server

                    }
                    else{

                        Toast.makeText(RegisterActivity.this,"Token was not found, please retry..", Toast.LENGTH_SHORT).show();
                    }

                    // do nothing
                }



            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                PopupUtil.dismiss();
                Log.e("RegisterActivity Error",t.getMessage());


            }
        });






    }

    public void cbClickShared(View v){


//        if (((CheckBox) v).isChecked()) {
//            Toast.makeText(RegisterActivity.this, "Checked", Toast.LENGTH_LONG).show();
//        }

        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            CheckShared="shared";
            Toast.makeText(RegisterActivity.this, "Checked" + CheckShared, Toast.LENGTH_LONG).show();
        }
        else{

            CheckShared="none";
            Toast.makeText(RegisterActivity.this, "notChecked" + CheckShared, Toast.LENGTH_LONG).show();
        }

    }

}
