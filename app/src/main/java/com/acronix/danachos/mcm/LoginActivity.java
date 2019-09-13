package com.acronix.danachos.mcm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acronix.danachos.mcm.apis.ApiClient;
import com.acronix.danachos.mcm.apis.StoreEndPoint;
import com.acronix.danachos.mcm.models.LoginResponse;
import com.acronix.danachos.mcm.utils.Contant;
import com.acronix.danachos.mcm.utils.PopupUtil;
import com.acronix.danachos.mcm.utils.PrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private EditText etUsernameLoginArea;
    private EditText etPasswordLoginArea;
    private Button btnLogin;
    private PrefUtils prefUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize userinterface
        etUsernameLoginArea = (EditText) findViewById(R.id.etUsername);
        etPasswordLoginArea = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        //intialize prefutils
        prefUtils = new PrefUtils(this);


       final String sessionUsername = prefUtils.getUsername();


        if(sessionUsername != null && (prefUtils.isLoggedIn())){
            etUsernameLoginArea.setText(sessionUsername);
        }



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dooLogin();
            }
        });


    }

    public void register (View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void dooLogin (){





        String strUsername=etUsernameLoginArea.getText().toString();
        String strPassword=etPasswordLoginArea.getText().toString();



        if(strUsername.isEmpty()){
            etUsernameLoginArea.setError("Please fill username.");
            return;

        }
        if(strPassword.isEmpty()){
            etPasswordLoginArea.setError("Please fill Password");
            return;
        }

        PopupUtil.showLoading(this,"Loading Please Wait...");

        StoreEndPoint endPoint= ApiClient.getClient().create(StoreEndPoint.class);
        Call<LoginResponse> callLogin=endPoint.getLogin(strUsername,strPassword);

        callLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                PopupUtil.dismiss();

                LoginResponse loginResponse=response.body();

                if(loginResponse.getSuccess()){

                    Contant.USERNAME_SESSION=loginResponse.getUsername().toString();
                    Contant.EMAIL_SESSION=loginResponse.getEmail().toString();
                    Contant.TOKEN_SESSION=loginResponse.getShareData().toString();

                    //Constant.PUSH_NOTIFICATION=

                 //   Toast.makeText(LoginActivity.this,"Sukses..", Toast.LENGTH_SHORT).show();

                    prefUtils.saveBoolean(PrefUtils.IS_LOGGEDIN,true);
                    prefUtils.saveString(PrefUtils.username,loginResponse.getUsername().toString());


                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{

                    Toast.makeText(LoginActivity.this,"Invalid Username & Password..", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                PopupUtil.dismiss();
                Log.e("LoginActivity Error",t.getMessage());
               // Log.e("RegisterActivity Error",t.getMessage());
            }
        });


//        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//        startActivity(intent);
//        finish();
    }
}
