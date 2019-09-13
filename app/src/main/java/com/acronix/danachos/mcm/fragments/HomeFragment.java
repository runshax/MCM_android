package com.acronix.danachos.mcm.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.acronix.danachos.mcm.R;
import com.acronix.danachos.mcm.utils.Contant;

import java.text.DecimalFormat;

import static com.acronix.danachos.mcm.HomeActivity.dbmcm;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView tvUsernameHome;
    private TextView tvExpenseMonthPlanHome;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        tvUsernameHome

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.mcm_home, container, false);
       // View view=  inflater.inflate(R.layout.fragment_product_list, container, false);


        //intialize user interface
        tvUsernameHome = (TextView) view.findViewById(R.id.tvUsernameHome);
        tvExpenseMonthPlanHome = (TextView) view.findViewById(R.id.tvExpenseMonthPlanHome);

        //set welcome user message
        tvUsernameHome.setText(Contant.USERNAME_SESSION);

        InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        tvExpenseMonthPlanHome.setText("0");

        String query2="";
        query2 = "select * from t_budget where the_date like '2017-12%' and (username ='"+Contant.USERNAME_SESSION.toString()+"'  or shareData='"+Contant.TOKEN_SESSION.toString()+"')";
        Cursor t_hapus = dbmcm.rawQuery(query2, null);
        if (t_hapus.getCount() > 0){
            t_hapus.moveToFirst();
            while (t_hapus.isAfterLast() == false) {

                String theamount= t_hapus.getString(t_hapus.getColumnIndex("amount"));


                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String theFormattedString = formatter.format(Integer.parseInt(theamount));

                tvExpenseMonthPlanHome.setText(theFormattedString);
               // tvBudgetExist.setVisibility(view.VISIBLE);


                t_hapus.moveToNext();
            }


        }
        t_hapus.close();


        return view;

    }

}
