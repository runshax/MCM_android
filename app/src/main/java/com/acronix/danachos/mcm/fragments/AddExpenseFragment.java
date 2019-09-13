package com.acronix.danachos.mcm.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.acronix.danachos.mcm.models.RegisterResponse;
import com.acronix.danachos.mcm.utils.Contant;
import com.acronix.danachos.mcm.utils.PopupUtil;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {

    static final int DATE_DIALOG_calldate = 0;
    private EditText etDateExpense;
    private EditText etNameExpense;
    private EditText etAmountExpense;
    private Button btnCancelExpense;
    private Button btnSaveExpense;
    public int mYearcalldate, mMonthcalldate, mDaycalldate;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_add_expense, container, false);

        View view= inflater.inflate(R.layout.fragment_add_expense, container, false);


        //intialize user interface
        etDateExpense = (EditText) view.findViewById(R.id.etDateExpense);
        etDateExpense.setFocusable(false);
        etNameExpense = (EditText) view.findViewById(R.id.etNameExpense);
        etAmountExpense = (EditText) view.findViewById(R.id.etAmountExpense);
        btnCancelExpense = (Button) view.findViewById(R.id.btnCancelExpense);
        btnSaveExpense = (Button) view.findViewById(R.id.btnSaveExpense);


        //for date
        Date skg = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(skg);
        mYearcalldate = c.get(Calendar.YEAR);
        mMonthcalldate = c.get(Calendar.MONTH);
        mDaycalldate = c.get(Calendar.DAY_OF_MONTH);



        etDateExpense.setText(new StringBuilder().append(mYearcalldate).append("-").append(pad(mMonthcalldate + 1)).append("-").append(pad(mDaycalldate)));



        btnSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                dooSaveExpense();
            }
        });


        btnCancelExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                etDateExpense.setText(new StringBuilder().append(mYearcalldate).append("-").append(pad(mMonthcalldate + 1)).append("-").append(pad(mDaycalldate)));
                etNameExpense.setText("");
                etAmountExpense.setText("");

            }
        });




        return view;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDateExpense.setOnClickListener(new View.OnClickListener() {
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

            etDateExpense.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };

    public void dooSaveExpense (){



        // Toast.makeText(getActivity(),"Halo Gays..", Toast.LENGTH_SHORT).show();

        String strDateExpense=etDateExpense.getText().toString();
        String strNameExpense=etNameExpense.getText().toString();
        String strAmountExpense=etAmountExpense.getText().toString();
        String strUsernameExpense= Contant.USERNAME_SESSION.toString();


        if(strDateExpense.isEmpty()){
            etDateExpense.setError("Please fill date transaction.");
            return;

        }
        if(strNameExpense.isEmpty()){
            etNameExpense.setError("Please fill name expense");
            return;
        }
        if(strAmountExpense.isEmpty()){
            etAmountExpense.setError("Please fill amount");
            return;
        }


        PopupUtil.showLoading(getActivity(),"Saving Please Wait...");

        StoreEndPoint endPoint= ApiClient.getClient().create(StoreEndPoint.class);
        Call<AddExpenseResponse> callSaveExpense=endPoint.postSaveExpense(strNameExpense,strDateExpense,strAmountExpense,strUsernameExpense);

        callSaveExpense.enqueue(new Callback<AddExpenseResponse>() {
            @Override
            public void onResponse(Call<AddExpenseResponse> call, Response<AddExpenseResponse> response) {
                PopupUtil.dismiss();

                AddExpenseResponse addExpenseResponse=response.body();

                if(addExpenseResponse.getSuccess()){

                    Toast.makeText(getActivity(),"Save Success", Toast.LENGTH_SHORT).show();

                    etDateExpense.setText("");
                    etNameExpense.setText("");
                    etAmountExpense.setText("");


                }
                else{
                    Toast.makeText(getActivity(),"Save Fail, please retry", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AddExpenseResponse> call, Throwable t) {

                PopupUtil.dismiss();

                Log.e("AddExpenseFrament Error",t.getMessage());


            }
        });








    }





    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
