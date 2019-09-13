package com.acronix.danachos.mcm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 11/30/2017.
 */

public class AddExpenseResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("lastID")
    @Expose
    private String lastID;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getLastID() {
        return lastID;
    }

    public void setLastID(String lastID) {
        this.lastID = lastID;
    }




}
