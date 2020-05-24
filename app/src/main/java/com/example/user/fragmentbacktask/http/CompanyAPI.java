package com.example.user.fragmentbacktask.http;

import com.example.user.fragmentbacktask.http.base.ApiResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CompanyAPI {

    @POST(ApiConstants.API_MOBI_INVESTOR + "favoritePackage/{id}/itemModify")
    Observable<ApiResponse<Object>> favProjectModify(@Path("id") int id,
                                                     @Body RequestBody requestBody);

}
