package com.example.githubtrainingappjava.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    @NonNull
    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.RepoViewHolder repoViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {


        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
