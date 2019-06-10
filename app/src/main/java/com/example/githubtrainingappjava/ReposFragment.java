package com.example.githubtrainingappjava;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.RepoAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.githubtrainingappjava.UserFragment.REPOSLIST;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReposFragment extends Fragment {
private ArrayList<GitHubRepo> reposList;
@BindView(R.id.repos_recycler)
RecyclerView repoRecycler;
private Context context;

    public ReposFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if(bundle != null){
            reposList = bundle.getParcelableArrayList(REPOSLIST);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        repoRecycler.setLayoutManager(layoutManager);
        repoRecycler.setAdapter(new RepoAdapter(getContext(), reposList));
        return view;
    }

}
