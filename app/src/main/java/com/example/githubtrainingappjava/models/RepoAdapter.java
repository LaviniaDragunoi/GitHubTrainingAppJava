package com.example.githubtrainingappjava.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.githubtrainingappjava.R;
import com.example.githubtrainingappjava.UserFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private Context context;
    private List<GitHubRepo> repoList;

public RepoAdapter(Context context, List<GitHubRepo> gitHubRepos){
    this.context = context;
    this.repoList = gitHubRepos;
}

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.repo_item, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.RepoViewHolder holder, int position) {

    GitHubRepo repo = repoList.get(position);

    holder.repoName.setText(repo.getName());
    if(repo.getPrivate()) {
        holder.repoPrivacy.setText("Private");
    }else {
        holder.repoPrivacy.setText("Public");
    }
    holder.repoCreatedAt.setText(UserFragment.formatDate(repo.getCreatedAt()));
    holder.repoUpdatedAt.setText(UserFragment.formatDate(repo.getUpdatedAt()));

    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.repo_name)
        TextView repoName;
    @BindView(R.id.repo_privacy)
    TextView repoPrivacy;
    @BindView(R.id.repo_created)
    TextView repoCreatedAt;
    @BindView(R.id.repo_updated)
    TextView repoUpdatedAt;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
