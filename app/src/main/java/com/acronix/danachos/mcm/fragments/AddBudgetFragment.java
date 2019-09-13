package com.acronix.danachos.mcm.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acronix.danachos.mcm.R;
import com.acronix.danachos.mcm.apis.ApiClient;
import com.acronix.danachos.mcm.apis.StoreEndPoint;
import com.acronix.danachos.mcm.models.AddExpenseResponse;
import com.acronix.danachos.mcm.models.BudgetResponse;
import com.acronix.danachos.mcm.utils.Contant;
import com.acronix.danachos.mcm.utils.PopupUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.acronix.danachos.mcm.HomeActivity.dbmcm;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBudgetFragment extends Fragment {

    private TextView tvBudgetExist;
    private EditText etMonthBudget;
    private EditText etAmountBudget;

    private Button btnCancelBudget;
    private Button btnSaveBudget;

    public int mYearcalldate, mMonthcalldate, mDaycalldate;

    public AddBudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_add_budget, container, false);


        //inisialize interface
        etMonthBudget = (EditText) view.findViewById(R.id.etMonthBudget);
        etMonthBudget.setFocusable(false);
        etAmountBudget = (EditText) view.findViewById(R.id.etAmountBudget);
        btnSaveBudget = (Button) view.findViewById(R.id.btnSaveBudget);
        btnCancelBudget = (Button) view.findViewById(R.id.btnCancelBudget);
        tvBudgetExist = (TextView) view.findViewById(R.id.tvBudgetExist);



        //for date
        Date skg = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(skg);
        mYearcalldate = c.get(Calendar.YEAR);
        mMonthcalldate = c.get(Calendar.MONTH);
        mDaycalldate = c.get(Calendar.DAY_OF_MONTH);



        etMonthBudget.setText(new StringBuilder().append(mYearcalldate).append("-").append(pad(mMonthcalldate + 1)).append("-").append(pad(mDaycalldate)));


        btnSaveBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                dooSaveBudget();
            }
        });


        btnCancelBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                etMonthBudget.setText(new StringBuilder().append(mYearcalldate).append("-").append(pad(mMonthcalldate + 1)).append("-").append(pad(mDaycalldate)));
                etAmountBudget.setText("");
//                etAmountExpense.setText("");

            }
        });

        tvBudgetExist.setVisibility(view.INVISIBLE);

        String query2="";
        query2 = "select * from t_budget where the_date like '2017-12%' and (username ='"+Contant.USERNAME_SESSION.toString()+"'  or shareData='"+Contant.TOKEN_SESSION.toString()+"')";
        Cursor t_hapus = dbmcm.rawQuery(query2, null);
        if (t_hapus.getCount() > 0){
            t_hapus.moveToFirst();
            while (t_hapus.isAfterLast() == false) {


                tvBudgetExist.setVisibility(view.VISIBLE);


                t_hapus.moveToNext();
            }


        }
        t_hapus.close();



        return view;


    }

    public void dooSaveBudget (){
            //not yet

       final String strMonthBudget=etMonthBudget.getText().toString();
       final String strAmountBudget=etAmountBudget.getText().toString();
       final String strUsernameBudget= Contant.USERNAME_SESSION.toString();
       final String strSharedData= Contant.TOKEN_SESSION.toString();


        if(strAmountBudget.isEmpty()){
            etAmountBudget.setError("Please fill Amount.");
            return;
        }


        PopupUtil.showLoading(getActivity(),"Saving Please Wait...");

        StoreEndPoint endPoint= ApiClient.getClient().create(StoreEndPoint.class);
        Call<BudgetResponse> callSaveBudget=endPoint.postSaveBudget(strMonthBudget,strAmountBudget,strUsernameBudget,strSharedData);


        callSaveBudget.enqueue(new Callback<BudgetResponse>() {
            @Override
            public void onResponse(Call<BudgetResponse> call, Response<BudgetResponse> response) {
                PopupUtil.dismiss();


                BudgetResponse budgetResponse=response.body();

                if(budgetResponse.getSuccess()){

                    Toast.makeText(getActivity(),"Save Success", Toast.LENGTH_SHORT).show();


                //    dbmcm.execSQL("CREATE TABLE IF NOT EXISTS t_budget(the_date VARCHAR, amount VARCHAR, username VARCHAR, shareData VARCHAR);");

                    String query="";
                    query = "insert or replace into t_budget " +
                            "(the_date, amount, username, shareData" +
                            ") values " +
                            "('"+strMonthBudget+"',"+
                            "'"+strAmountBudget+"',"+
                            "'"+strUsernameBudget+"','"+
                            strSharedData+"')";
                    dbmcm.execSQL(query);


                    etAmountBudget.setText("");
                    etMonthBudget.setText(new StringBuilder().append(mYearcalldate).append("-").append(pad(mMonthcalldate + 1)).append("-").append(pad(mDaycalldate)));
                    //etAmountExpense.setText("");


                }
                else{
                    Toast.makeText(getActivity(),"Save Fail, please retry", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BudgetResponse> call, Throwable t) {
                PopupUtil.dismiss();
            }
        });








    }



    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etMonthBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker();
            }
        });
    }


    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            etMonthBudget.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };


    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
