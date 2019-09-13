package com.acronix.danachos.mcm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 12/15/2017.
 */

public class DATAArray {

    @SerializedName("expense_name")
    @Expose
    private String expenseName;
    @SerializedName("date_expense")
    @Expose
    private String dateExpense;
    @SerializedName("time_expense")
    @Expose
    private String timeExpense;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getDateExpense() {
        return dateExpense;
    }

    public void setDateExpense(String dateExpense) {
        this.dateExpense = dateExpense;
    }

    public String getTimeExpense() {
        return timeExpense;
    }

    public void setTimeExpense(String timeExpense) {
        this.timeExpense = timeExpense;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
