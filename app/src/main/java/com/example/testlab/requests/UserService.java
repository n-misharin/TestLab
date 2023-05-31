package com.example.testlab.requests;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    // TODO: begin
    @POST("/api/v1/login")
    Call<StatusMessage> login(@Body UserCredential userCredential);
    
    @GET("/api/v1/user/{username}")
    Call<UserData> getUserData(
            @Header("Cookie") String cookie,
            @Path("username") String username
    );
    // TODO: end
}
