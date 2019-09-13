package com.acronix.danachos.mcm.fragments;


import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acronix.danachos.mcm.R;
import com.acronix.danachos.mcm.adapters.ReportExpenseAdapter;
import com.acronix.danachos.mcm.apis.ApiClient;
import com.acronix.danachos.mcm.apis.StoreEndPoint;
import com.acronix.danachos.mcm.models.DATAArray;
import com.acronix.danachos.mcm.models.ReportExpenseResponse;
import com.acronix.danachos.mcm.utils.Contant;
import com.acronix.danachos.mcm.utils.PopupUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.acronix.danachos.mcm.HomeActivity.dbmcm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportExpenseFragment extends Fragment implements ReportExpenseAdapter.OnItemClickListner {


    RecyclerView recyclerView;
    private List<DATAArray> mExpenseList;
    private ReportExpenseAdapter mAdapter;

    private Button btnSearchKeyWord;
    public EditText etKeyword;

    public ReportExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //ArrayList<ItemEventCalendar> itemList = new ArrayList<ItemEventCalendar>();

        View view= inflater.inflate(R.layout.fragment_report_expense, container, false);



        mExpenseList=new ArrayList<>();

        btnSearchKeyWord = (Button) view.findViewById(R.id.btnSearchReport);
        etKeyword = (EditText) view.findViewById(R.id.etKeyword);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ReportExpenseAdapter(getActivity(),mExpenseList);
        //mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);


        loadExpense();

        btnSearchKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

              //  dooSearchReport();
            }
        });


        return view;

       // return inflater.inflate(R.layout.fragment_report_expense, container, false);
    }

    private void loadExpense(){

        PopupUtil.showLoading(getActivity(),"Loading Expense...");

        StoreEndPoint storeEndPoint = ApiClient.getClient().create(StoreEndPoint.class);

        final String strx= Contant.USERNAME_SESSION;
        final String token= Contant.TOKEN_SESSION;
        Call<ReportExpenseResponse> callReportExpense = storeEndPoint.getExpenseReport(strx,strx);

        callReportExpense.enqueue(new Callback<ReportExpenseResponse>() {
            @Override
            public void onResponse(Call<ReportExpenseResponse> call, Response<ReportExpenseResponse> response) {
                PopupUtil.dismiss();

                mExpenseList.clear();

                ReportExpenseResponse reportExpenseResponse = response.body();

                if(reportExpenseResponse.getSuccess()){


                    for (int i = 0; i < reportExpenseResponse.getDATAArray().size(); i++) {


                       // Toast.makeText(getActivity(),reportExpenseResponse.getDATAArray().get(i).getAmount().toString(), Toast.LENGTH_SHORT).show();


                        //dbmcm.execSQL("CREATE TABLE IF NOT EXISTS t_expense_report(expense_name VARCHAR, datex VARCHAR, timex VARCHAR, amount VARCHAR, username VARCHAR, store_name VARCHAR, PRIMARY KEY (expense_name,datex,timex));");


                      //  token
                        String query = "";
                        query = "insert or replace into t_expense_report " +
                                "(expense_name, datex, timex, amount, username, sharedData" +
                                ") values " +
                                "('"+reportExpenseResponse.getDATAArray().get(i).getExpenseName().toString()+"','"+
                                reportExpenseResponse.getDATAArray().get(i).getDateExpense().toString()+"','"+
                                reportExpenseResponse.getDATAArray().get(i).getTimeExpense().toString()+"','"+
                                reportExpenseResponse.getDATAArray().get(i).getAmount().toString()+"','"+
                                strx+"','"+
                                token+"')";

                        dbmcm.execSQL(query);


                    }


                    mExpenseList.addAll(reportExpenseResponse.getDATAArray());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter.notifyDataSetChanged();

                        }
                    });


                }
                else{
                    Toast.makeText(getActivity(),"Fail loading Report..", Toast.LENGTH_SHORT).show();

                }




               // mExpenseList.addAll(reportExpenseResponse.get);



            }

            @Override
            public void onFailure(Call<ReportExpenseResponse> call, Throwable t) {

                PopupUtil.dismiss();


                Log.e("ReportExpense Error",t.getMessage());


            }
        });




    }



}
