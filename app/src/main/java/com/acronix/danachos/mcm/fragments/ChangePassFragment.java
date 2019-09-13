package com.acronix.danachos.mcm.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acronix.danachos.mcm.R;
import com.acronix.danachos.mcm.apis.ApiClient;
import com.acronix.danachos.mcm.apis.StoreEndPoint;
import com.acronix.danachos.mcm.models.AddExpenseResponse;
import com.acronix.danachos.mcm.models.ChangePassResponse;
import com.acronix.danachos.mcm.utils.Contant;
import com.acronix.danachos.mcm.utils.PopupUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassFragment extends Fragment {


    private EditText etNewPassChangePass;
    private EditText etConfirmPassChangePass;
    private Button btnSaveChangePass;
    private Button btnCancelChangePass;


    public ChangePassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_change_pass, container, false);

        View view= inflater.inflate(R.layout.fragment_change_pass, container, false);


        //intialize user interface
        etNewPassChangePass = (EditText) view.findViewById(R.id.etNewPassChangePass);
        etConfirmPassChangePass = (EditText)view.findViewById(R.id.etConfirmPassChangePass);
        btnSaveChangePass = (Button) view.findViewById(R.id.btnSaveChangePass);
        btnCancelChangePass = (Button) view.findViewById(R.id.btnCancelChangePass);


        btnSaveChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                dooSaveChangePass();
            }
        });


        btnCancelChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                etNewPassChangePass.setText("");
                etConfirmPassChangePass.setText("");

            }
        });



        return view;
    }


    public void dooSaveChangePass (){

        String strNewPass=etNewPassChangePass.getText().toString();
        String strConfirmPass=etConfirmPassChangePass.getText().toString();
        String strUsername= Contant.USERNAME_SESSION.toString();


        if(strNewPass.isEmpty()){
            etNewPassChangePass.setError("Please fill New Password");
            return;
        }

        if(strConfirmPass.isEmpty()){
            etConfirmPassChangePass.setError("Please fill Confirm Password");
            return;
        }


        boolean matchPass= strNewPass.equals(strConfirmPass);
        if(!matchPass){

            Toast.makeText(getActivity(),"Password does not match, please try again..", Toast.LENGTH_SHORT).show();
            return;

        }

        PopupUtil.showLoading(getActivity(),"Saving Please Wait...");

        StoreEndPoint endPoint= ApiClient.getClient().create(StoreEndPoint.class);
        Call<ChangePassResponse> callChangePass=endPoint.postChangePassword(strUsername,strNewPass);

        callChangePass.enqueue(new Callback<ChangePassResponse>() {
            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                PopupUtil.dismiss();

                ChangePassResponse changePassResponse=response.body();


                if(changePassResponse.getSuccess()){

                    etNewPassChangePass.setText("");
                    etConfirmPassChangePass.setText("");

                    Toast.makeText(getActivity(),"Save Success", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getActivity(),"Save Fail, please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable t) {
                PopupUtil.dismiss();
                Log.e("ChangePassFrag Error",t.getMessage());
            }
        });






    }

}
