package com.example.githubtrainingappjava;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    ImageView githubIcon;
    private String username;
    private String password;
    private String sharedPrefFile;
    private SharedPreferences mPreferences;
    private Owner mOwner;
    private AppRoomDatabase appRoomDatabase;
    private AppExecutors appExecutors;
    private ApiInterface apiInterface;
    private Repository repository;
    private OwnerViewModelFactory ownerViewModelFactory;
    private OwnerViewModel ownerViewModel;

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
        appRoomDatabase = AppRoomDatabase.getsInstance(this);
        appExecutors =AppExecutors.getInstance();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        repository = Repository.getsInstance(appExecutors,appRoomDatabase,
                appRoomDatabase.ownerDao(), apiInterface);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser();

            }
        });

    }

    /**
     * This is a method that will verify if the device has internet connection
     * @return true if exists connection and false otherwise
     */
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean hasConnection = false;
        if (networkInfo != null && networkInfo.isConnected()) hasConnection = true;
        return hasConnection;
    }

    private void loadUser() {

       username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        String base = username + ":" + password;
        final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        ownerViewModelFactory = new OwnerViewModelFactory(repository,authHeader);
        ownerViewModel = ViewModelProviders.of(this, ownerViewModelFactory).get(OwnerViewModel.class);

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
