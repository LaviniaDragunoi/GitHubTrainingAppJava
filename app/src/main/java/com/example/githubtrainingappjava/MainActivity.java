package com.example.githubtrainingappjava;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.githubtrainingappjava.database.OwnerDao;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubtrainingappjava.LoginActivity.AUTHHEADER;
import static com.example.githubtrainingappjava.LoginActivity.OWNER_DATA;

public class MainActivity extends AppCompatActivity {


    private Owner owner;
    private String authHeader;
    private AppExecutors mAppExcutors;
    private OwnerViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null) {
            owner = intent.getParcelableExtra(OWNER_DATA);
            authHeader = intent.getStringExtra(AUTHHEADER);

        }

        AppRoomDatabase appRoomDatabase = AppRoomDatabase.getsInstance(this);
        mAppExcutors = AppExecutors.getInstance();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Repository mRepository = Repository.getsInstance(mAppExcutors, appRoomDatabase,
                appRoomDatabase.ownerDao(),apiInterface);
        OwnerViewModelFactory ownerViewModelFactory = new OwnerViewModelFactory(mRepository, authHeader);
        mViewModel = ViewModelProviders.of(this, ownerViewModelFactory).get(OwnerViewModel.class);
        setTitle(owner.getName());
        UserFragment userFragment = new UserFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelable(OWNER_DATA,owner);
        bundle.putString(AUTHHEADER, authHeader);
        userFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.main_container, userFragment)
                .commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.id_logout == item.getItemId()){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAppExcutors.diskIO().execute(() -> {
            mViewModel.deleteDatabase();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}


