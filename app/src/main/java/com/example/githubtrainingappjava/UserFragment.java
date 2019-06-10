package com.example.githubtrainingappjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubtrainingappjava.data.ApiClient;
import com.example.githubtrainingappjava.data.ApiInterface;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubtrainingappjava.LoginActivity.AUTHHEADER;
import static com.example.githubtrainingappjava.LoginActivity.OWNER_DATA;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    @BindView(R.id.avatar)
    ImageView ownerAvatar;
    @BindView(R.id.bio_text_view)
    TextView bioTV;
    @BindView(R.id.location_text_view)
    TextView locationTv;
    @BindView(R.id.email_text_view)
    TextView emailTV;
    @BindView(R.id.created_date_text_view)
    TextView createdTV;
    @BindView(R.id.update_date_text_view)
    TextView updateTV;
    @BindView(R.id.public_repo_number)
    TextView publicRepoTV;
    @BindView(R.id.private_repo_number)
    TextView privateRepoTV;
    @BindView(R.id.open_repo_button)
    Button openRepoButton;
    @BindView(R.id.email_button)
    Button emailButton;
    private Owner owner;
    private String authHeader;
    public static final String REPOSLIST = "reposList";


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            owner = bundle.getParcelable(OWNER_DATA);
            authHeader = bundle.getString(AUTHHEADER);
        }
        displayUserInfo();
        return view;
    }

    private void displayUserInfo() {
        Picasso.get().load(owner.getAvatarUrl()).into(ownerAvatar);
        bioTV.setText(owner.getBio());
        locationTv.setText(owner.getLocation());
        emailTV.setText(owner.getEmail());
        createdTV.setText(owner.getCreatedAt());
        updateTV.setText(owner.getUpdatedAt());
        publicRepoTV.setText(owner.getPublicRepos().toString());
        privateRepoTV.setText(owner.getTotalPrivateRepos().toString());
        openRepoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRepos();
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent =  new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: " ));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, owner.getEmail());
                if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(emailIntent);
                }

            }
        });

    }

    private void loadRepos() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<GitHubRepo>> call = apiInterface.getRepos(authHeader);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                    List<GitHubRepo> gitHubRepoList = response.body();
                    Bundle bundle = new Bundle();
                ReposFragment reposFragment = new ReposFragment();
               bundle.putParcelableArrayList(REPOSLIST, new ArrayList<>(gitHubRepoList));
               reposFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.main_container, reposFragment)
               .commit();
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(getContext(), "It is not a succes", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
