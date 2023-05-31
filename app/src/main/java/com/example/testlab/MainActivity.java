package com.example.testlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Part;

public class MainActivity extends AppCompatActivity {
//    public static final String APP_PREFERENCES = "settings";
    public static final String BASE_URL = "http://127.0.0.1:5000";

    class UserCredential {
        String username, password;

        public UserCredential(String login, String password) {
            this.username = login;
            this.password = password;
        }
    }

    class ResponseMessage {
        String statusMessage;
    }

    class UserData {
        String name, surname;
        int age;
    }

    interface UserService {
        @POST("/api/v1/login")
        Call<ResponseMessage> login(@Body UserCredential userCredential);

        @GET("/api/v1/user/{username}")
        Call<UserData> getUserData(
                @Header("Cookie") String cookie,
                @Part("username") String username
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);

        Call<ResponseMessage> call = userService.login(new UserCredential("login", "pass"));

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                String cookie = response.headers().get("Set-Cookie");
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
    }
}