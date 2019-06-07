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
import android.widget.Toast;

import com.example.githubtrainingappjava.data.ApiClient;
import com.example.githubtrainingappjava.data.ApiInterface;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String OWNER_DATA = "ownerResponse";
    public static final String AUTHHEADER = "authheather" ;
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameEditText.setVisibility(View.GONE);
                passwordEditText.setVisibility(View.GONE);
                githubIcon.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
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
                    Log.d("asdfs","isSuccessful");
                    Log.d("asdfs","isSuccessful " + response.body().getAvatarUrl());


                    Owner ownerResponse = response.body();

                    Intent intent =  new Intent (LoginActivity.this, MainActivity.class);
                    intent.putExtra(OWNER_DATA, ownerResponse);
                    intent.putExtra(AUTHHEADER, authHeader);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Log.d("asdfs","onFailure");
                Log.d("asdfs",t.getMessage());
                Log.d("asdfs",t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this, "it's not a succes", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
