package com.example.githubtrainingappjava;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubtrainingappjava.LoginActivity.AUTHHEADER;
import static com.example.githubtrainingappjava.LoginActivity.OWNER_DATA;
import static com.example.githubtrainingappjava.UserFragment.REPOSLIST;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Owner owner;
    private String authHeader;
    private AppExecutors mAppExcutors;
    private OwnerViewModel mViewModel;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;


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
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelable(OWNER_DATA,owner);
        bundle.putString(AUTHHEADER, authHeader);
        userFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, userFragment)
                .commit();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.setScrollContainer(true);
        actionBarDrawerToggle.syncState();
        navigationView.setScrollContainer(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

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
        }else if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.created_action){
            Toast.makeText(MainActivity.this, "merge", Toast.LENGTH_SHORT).show();
        }
//        switch (id) {
//            case R.id.created:
//                Toast.makeText(this, "merge", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.organization:
//                break;
//            case R.id.updated:
//                break;
//            case R.id.pushed:
//                break;
//            case R.id.full_name:
//                break;
//            case R.id.owner:
//                break;
//            case R.id.collaborator:
//                break;
//            default:
//                return true;
//
//        }
        return true;
    }

    private void loadReposByCreatedDate() {

        mViewModel.getReposByCreatedDate().observe(this, gitHubRepos -> {
                if(gitHubRepos != null) {
                    Bundle bundle = new Bundle();
                    ReposFragment reposFragment = new ReposFragment();
                    bundle.putParcelableArrayList(REPOSLIST, new ArrayList<>(gitHubRepos));
                    reposFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.main_container, reposFragment)
                            .commit();

                }
            });
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


