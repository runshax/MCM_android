package com.acronix.danachos.mcm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 12/18/2017.
 */

public class BudgetResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("date_budget")
    @Expose
    private String dateBudget;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("sharedData")
    @Expose
    private String sharedData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDateBudget() {
        return dateBudget;
    }

    public void setDateBudget(String dateBudget) {
        this.dateBudget = dateBudget;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSharedData() {
        return sharedData;
    }

    public void setSharedData(String sharedData) {
        this.sharedData = sharedData;
    }


}
