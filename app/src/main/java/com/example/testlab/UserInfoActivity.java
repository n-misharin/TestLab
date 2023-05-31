package com.example.testlab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testlab.requests.UserData;
import com.example.testlab.requests.UserService;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoActivity extends AppCompatActivity {
    private TextView nameTextView, surnameTextView, ageTextView;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        // TODO: begin
        nameTextView = findViewById(R.id.text_name);
        surnameTextView = findViewById(R.id.text_surname);
        ageTextView = findViewById(R.id.text_age);

        editText = findViewById(R.id.et_find_user);
        Button button = findViewById(R.id.button_find);

        String cookie = getIntent().getStringExtra(MainActivity.COOKIE_KEY);

        button.setOnClickListener(view -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserService userService = retrofit.create(UserService.class);
            Call<UserData> userDataCall = userService.getUserData(cookie, editText.getText().toString());
            userDataCall.enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    if (response.isSuccessful() && response.body() != null){
                        setUserData(response.body());
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                getResources().getString(R.string.user_not_found),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(R.string.incorrect_request),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
        // TODO: end
    }

    // TODO: begin
    private void setUserData(UserData userData){
        nameTextView.setText(userData.name);
        surnameTextView.setText(userData.surname);
        ageTextView.setText(String.valueOf(userData.age));
    }
    // TODO: end
}
