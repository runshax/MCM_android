package com.acronix.danachos.mcm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 12/15/2017.
 */

public class ReportExpenseResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("DATAArray")
    @Expose
    private List<DATAArray> dATAArray = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DATAArray> getDATAArray() {
        return dATAArray;
    }

    public void setDATAArray(List<DATAArray> dATAArray) {
        this.dATAArray = dATAArray;
    }


}
