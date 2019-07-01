package com.example.githubtrainingappjava;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.githubtrainingappjava.ViewModel.OwnerViewModel;
import com.example.githubtrainingappjava.ViewModel.OwnerViewModelFactory;
import com.example.githubtrainingappjava.data.ApiClient;
import com.example.githubtrainingappjava.data.ApiInterface;
import com.example.githubtrainingappjava.database.AppRoomDatabase;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.githubtrainingappjava.LoginActivity.AUTHHEADER;
import static com.example.githubtrainingappjava.LoginActivity.IS_LOGED;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private String sharedPrefFile;
    private SharedPreferences mPreferences;
    private AppRoomDatabase appRoomDatabase;
    private Repository mRepository;
    private ApiInterface apiInterface;
    private OwnerViewModelFactory ownerViewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPrefFile = "com.example.githubtrainingappjava";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if(drawerLayout != null){
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        }
        if(navigationView != null){
            navigationView.setNavigationItemSelectedListener(this);
        }
        actionBarDrawerToggle.syncState();
        navigationView.setScrollContainer(true);
        Intent intent = getIntent();
        if(intent != null) {
            owner = intent.getParcelableExtra(OWNER_DATA);
            authHeader = intent.getStringExtra(AUTHHEADER);

        }
        appRoomDatabase = AppRoomDatabase.getsInstance(this);
        mAppExcutors = AppExecutors.getInstance();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mRepository = Repository.getsInstance(mAppExcutors, appRoomDatabase,
                appRoomDatabase.ownerDao(),apiInterface);
        ownerViewModelFactory = new OwnerViewModelFactory(mRepository, authHeader);
        mViewModel = ViewModelProviders.of(this, ownerViewModelFactory).get(OwnerViewModel.class);

        setTitle(owner.getName());

        if(savedInstanceState == null) {
            UserFragment userFragment = new UserFragment();
            fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putParcelable(OWNER_DATA, owner);
            bundle.putString(AUTHHEADER, authHeader);
            userFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, userFragment)
                    .commit();
        }

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
            SharedPreferences.Editor preferancesEditor = mPreferences.edit();
            preferancesEditor.putBoolean(IS_LOGED, false);
            preferancesEditor.putString(AUTHHEADER, null);
            preferancesEditor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.created_action:
               loadReposByCreatedDate();
                break;
            case R.id.updated:
                loadReposByUpdatedDate();
                break;
            case R.id.pushed:
                loadReposByPushedDate();
                break;
            case R.id.full_name:
                loadReposByName();
                break;
            case R.id.owner:
                loadOwnedRepos();
                break;
            case R.id.collaborator:
                loadCollaboratingRepos();
                break;
            case R.id.organization:
                break;
            default:
                loadReposByName();
                return true;

        }
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
    private void loadReposByName() {

        mViewModel.getReposByName().observe(this, gitHubRepos -> {
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

    private void loadReposByPushedDate() {

        mViewModel.getReposByPushedDate().observe(this, gitHubRepos -> {
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


    private void loadReposByUpdatedDate() {

        mViewModel.getReposByUpdatedDate().observe(this, gitHubRepos -> {
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

    private void loadOwnedRepos() {
        mViewModel.getOwnedRepos().observe(this, gitHubRepos -> {
            if(gitHubRepos != null) {
                List<GitHubRepo> ownedRepos = new ArrayList<>();
                for(int i= 0; i< gitHubRepos.size(); i++){
                    if(gitHubRepos.get(i).getFullName().contains(owner.getLogin())){
                        ownedRepos.add(gitHubRepos.get(i));
                    }
                }
                Bundle bundle = new Bundle();
                ReposFragment reposFragment = new ReposFragment();
                bundle.putParcelableArrayList(REPOSLIST, new ArrayList<>(ownedRepos));
                reposFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_container, reposFragment)
                        .commit();

            }
        });
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void loadCollaboratingRepos() {
        mViewModel.getOwnedRepos().observe(this, gitHubRepos -> {
            if(gitHubRepos != null) {
                List<GitHubRepo> ownedRepos = new ArrayList<>();
                for(int i= 0; i< gitHubRepos.size(); i++){
                    if(!gitHubRepos.get(i).getFullName().contains(owner.getLogin())){
                        ownedRepos.add(gitHubRepos.get(i));
                    }
                }
                Bundle bundle = new Bundle();
                ReposFragment reposFragment = new ReposFragment();
                bundle.putParcelableArrayList(REPOSLIST, new ArrayList<>(ownedRepos));
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


