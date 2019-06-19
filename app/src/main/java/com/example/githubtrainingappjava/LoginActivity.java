package com.example.githubtrainingappjava;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.ContentLoadingProgressBar;
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

import com.example.githubtrainingappjava.ViewModel.OwnerViewModel;
import com.example.githubtrainingappjava.ViewModel.OwnerViewModelFactory;
import com.example.githubtrainingappjava.data.ApiClient;
import com.example.githubtrainingappjava.data.ApiInterface;
import com.example.githubtrainingappjava.database.AppRoomDatabase;
import com.example.githubtrainingappjava.models.Owner;


import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    public static final String OWNER_DATA = "owner";
    public static final String AUTHHEADER = "authheather" ;
    private static final String USERNAME = "username";
    private static final String USERS_PASSWORD = "password";
    private static final String IS_LOGED = "isLoged";
    @BindView(R.id.editTextUsername)
    EditText usernameEditText;
    @BindView(R.id.editTextpassword)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.githubOctocatIcon)
    ImageView githubIcon;;
    private String username;
    private String password;
    private String sharedPrefFile;
    private SharedPreferences mPreferences;
    private Owner mOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

         sharedPrefFile = "com.example.githubtrainingappjava";
         mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

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
        AppRoomDatabase appRoomDatabase = AppRoomDatabase.getsInstance(this);
       AppExecutors appExecutors =AppExecutors.getInstance();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        // username = usernameEditText.getText().toString();
        username = "lavinia.dragunoi@yahoo.ro";
        //  password = passwordEditText.getText().toString();
        password = "!Laurentiu35";

        String base = username + ":" + password;
        final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        Repository repository = Repository.getsInstance(appExecutors,appRoomDatabase,
                appRoomDatabase.ownerDao(), apiInterface);
        OwnerViewModelFactory ownerViewModelFactory = new OwnerViewModelFactory(repository,authHeader);
        OwnerViewModel ownerViewModel = ViewModelProviders.of(this, ownerViewModelFactory).get(OwnerViewModel.class);

        ownerViewModel.getOwnerLiveData().observe(this, owner -> {
            if( owner != null){
                SharedPreferences.Editor preferancesEditor = mPreferences.edit();
                preferancesEditor.putBoolean(IS_LOGED, true);
                preferancesEditor.putString(AUTHHEADER, authHeader);
                preferancesEditor.apply();
                Intent intent =  new Intent (LoginActivity.this, MainActivity.class);
                  intent.putExtra(OWNER_DATA, owner);
                   intent.putExtra(AUTHHEADER, authHeader);
                   startActivity(intent);
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
