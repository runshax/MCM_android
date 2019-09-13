package com.acronix.danachos.mcm.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acronix.danachos.mcm.R;
import com.acronix.danachos.mcm.utils.Contant;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSettingFragment extends Fragment {

    private TextView tvUsernameSetting;
    private TextView tvEmailSetting;
    private TextView tvSharedSetting;
    private Button btnChangePassSetting;

    private FragmentActivity myContext;

    public UserSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_setting, container, false);
        // View view=  inflater.inflate(R.layout.fragment_product_list, container, false);


        //intialize user interface
        tvUsernameSetting = (TextView) view.findViewById(R.id.tvUsernameSetting);
        tvEmailSetting = (TextView) view.findViewById(R.id.tvEmaiSetting);
        tvSharedSetting = (TextView) view.findViewById(R.id.tvTokenSharedSetting);
        btnChangePassSetting = (Button) view.findViewById(R.id.btnChangePassSetting);



        //set information
        tvUsernameSetting.setText(Contant.USERNAME_SESSION);
        tvEmailSetting.setText(Contant.EMAIL_SESSION);
        tvSharedSetting.setText(Contant.TOKEN_SESSION);


        btnChangePassSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                dooChangePass();
            }
        });


        return view;
    }




    public void dooChangePass (){


       // Toast.makeText(getActivity(),"Halo Gays..", Toast.LENGTH_SHORT).show();

        Fragment fragment = null;
        fragment = new ChangePassFragment();

        FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();




    }

}
