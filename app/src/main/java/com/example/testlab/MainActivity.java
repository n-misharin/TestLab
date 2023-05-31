package com.example.testlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testlab.requests.StatusMessage;
import com.example.testlab.requests.UserCredential;
import com.example.testlab.requests.UserService;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://192.168.0.79";
    // TODO: begin
    public static final String COOKIE_KEY = "COOKIE";
    // TODO: end

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        Button enterButton = findViewById(R.id.button_login);

        enterButton.setOnClickListener(view -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserService userService = retrofit.create(UserService.class);

            Call<StatusMessage> call = userService.login(new UserCredential(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()
            ));

            call.enqueue(new Callback<StatusMessage>() {
                @Override
                public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                    if (response.isSuccessful()){
                        // TODO: begin
                        String cookie = response.headers().get("Set-Cookie");
                        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                        intent.putExtra(COOKIE_KEY, cookie);
                        startActivity(intent);
                        finish();
                        // TODO: end
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                getResources().getString(R.string.incorrect_credential),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<StatusMessage> call, Throwable t) {
                    Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(R.string.incorrect_request),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
    }
}