package com.acronix.danachos.mcm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 11/27/2017.
 */

public class RegisterResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("tokenShared")
    @Expose
    private String tokenShared;
    @SerializedName("resultRegister")
    @Expose
    private Boolean resultRegister;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTokenShared() {
        return tokenShared;
    }

    public void setTokenShared(String tokenShared) {
        this.tokenShared = tokenShared;
    }

    public Boolean getResultRegister() {
        return resultRegister;
    }

    public void setResultRegister(Boolean resultRegister) {
        this.resultRegister = resultRegister;
    }


}
