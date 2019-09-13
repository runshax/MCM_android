package com.acronix.danachos.mcm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acronix.danachos.mcm.R;
import com.acronix.danachos.mcm.models.DATAArray;

import java.util.List;

/**
 * Created by User on 12/15/2017.
 */

public class ReportExpenseAdapter  extends RecyclerView.Adapter<ReportExpenseAdapter.MyViewHolder>{


    private List<DATAArray> mExpenseList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ReportExpenseAdapter(Context context, List<DATAArray> expenseList){

        mContext=context;
        mExpenseList=expenseList;
        mLayoutInflater=LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.item_report,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final DATAArray dataArray = mExpenseList.get(position);

        holder.tvDate.setText(dataArray.getDateExpense());
        holder.tvTime.setText(dataArray.getTimeExpense());
        holder.tvExpName.setText(dataArray.getExpenseName());
        holder.tvAmount.setText(dataArray.getAmount());


    }

    @Override
    public int getItemCount() {
        return mExpenseList.size();
    }

    public interface OnItemClickListner {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTime;
        TextView tvExpName;
        TextView tvAmount;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.row_item_date);
            tvTime = itemView.findViewById(R.id.row_item_time);
            tvExpName = itemView.findViewById(R.id.row_item_expense);
            tvAmount = itemView.findViewById(R.id.row_item_amount);


        }
    }
}
