package com.example.githubtrainingappjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubtrainingappjava.data.ApiClient;
import com.example.githubtrainingappjava.data.ApiInterface;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public static final String OWNER_DATA = "ownerResponse";
    public static final String AUTHHEADER = "authheather" ;
    private static final String USERNAME = "username";
    private static final String USERS_PASSWORD = "password";
    @BindView(R.id.editTextUsername)
    EditText usernameEditText;
    @BindView(R.id.editTextpassword)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.githubOctocatIcon)
    ImageView githubIcon;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
               if(savedInstanceState != null){
            username = savedInstanceState.getString(USERNAME);
            password = savedInstanceState.getString(USERS_PASSWORD);
            usernameEditText.setText(username);
            passwordEditText.setText(password);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser();

            }
        });

    }
    private void loadUser() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
       username = usernameEditText.getText().toString();
       password = passwordEditText.getText().toString();
        String base = username + ":" + password;
        final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

        Call<Owner> call = apiInterface.getOwner(authHeader);
        call.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.isSuccessful()) {
                    Owner ownerResponse = response.body();
                    Intent intent =  new Intent (LoginActivity.this, MainActivity.class);
                    intent.putExtra(OWNER_DATA, ownerResponse);
                    intent.putExtra(AUTHHEADER, authHeader);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, R.string.wrong_credential, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
             if(t instanceof IOException){
                 Toast.makeText(LoginActivity.this, R.string.no_connection_message, Toast.LENGTH_SHORT).show();
             }

            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USERNAME, username);
        outState.putString(USERS_PASSWORD, password);
    }


}
