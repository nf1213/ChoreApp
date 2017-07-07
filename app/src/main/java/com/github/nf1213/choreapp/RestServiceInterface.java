package com.github.nf1213.choreapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nicole Felch on 6/28/17.
 */
public interface RestServiceInterface {
    @GET("appliances")
    Call<List<ApplianceSearchResult>> searchAppliances(@Query("search") String query);
}
