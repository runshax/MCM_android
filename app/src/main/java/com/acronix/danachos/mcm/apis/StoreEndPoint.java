package com.acronix.danachos.mcm.apis;

import com.acronix.danachos.mcm.models.AddExpenseResponse;
import com.acronix.danachos.mcm.models.BudgetResponse;
import com.acronix.danachos.mcm.models.ChangePassResponse;
import com.acronix.danachos.mcm.models.LoginResponse;
import com.acronix.danachos.mcm.models.RegisterResponse;
import com.acronix.danachos.mcm.models.ReportExpenseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 11/27/2017.
 */

public interface StoreEndPoint {


    @FormUrlEncoded
    @POST("api/doRegister")
    public Call<RegisterResponse> postRegister(@Field("username") String username,
                                               @Field("email") String email,
                                               @Field("password") String password,
                                               @Field("token") String token,
                                               @Field("newTokenReq") String newTokenReq);


    @GET("/restapi/public/index.php/api/doLogin/{username}/{password}")
    public Call<LoginResponse> getLogin(@Path("username") String username,
                                        @Path("password") String password);



    @FormUrlEncoded
    @POST("api/doSaveExpense")
    public Call<AddExpenseResponse> postSaveExpense(@Field("expense_name") String expense_name,
                                                 @Field("datex") String datex,
                                                 @Field("amount") String amount,
                                                 @Field("username") String username);


    @FormUrlEncoded
    @POST("api/doChangePass")
    public Call<ChangePassResponse> postChangePassword(@Field("username") String username,
                                                       @Field("password") String password);


    @GET("/restapi/public/index.php/api/doViewExpense/{username}/{key_search}")
    public Call<ReportExpenseResponse> getExpenseReport(@Path("username") String username,
                                                        @Path("key_search") String key_search);


    @FormUrlEncoded
    @POST("api/doSaveBudget")
    public Call<BudgetResponse> postSaveBudget(@Field("date_budget") String date_budget,
                                                @Field("amount") String amount,
                                                @Field("username") String username,
                                                @Field("sharedData") String sharedData);






}
