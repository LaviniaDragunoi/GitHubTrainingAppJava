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
        holder.repoPrivacy.setText(context.getString(R.string.private_title));
    }else {
        holder.repoPrivacy.setText(context.getString(R.string.public_title));
    }
    holder.repoCreatedAt.setText(UserFragment.formatDate(repo.getCreatedAt()));
    holder.repoUpdatedAt.setText(UserFragment.formatDate(repo.getUpdatedAt()));
    holder.pushedDate.setText(UserFragment.formatDate(repo.getPushedAt()));
    holder.repoFullName.setText(repo.getFullName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(holder.pushedDate.getVisibility() == View.GONE) {
                holder.fullNameTitle.setVisibility(View.VISIBLE);
                holder.repoFullName.setVisibility(View.VISIBLE);
                holder.pushedTitle.setVisibility(View.VISIBLE);
                holder.pushedDate.setVisibility(View.VISIBLE);
            }else {
                holder.fullNameTitle.setVisibility(View.GONE);
                holder.repoFullName.setVisibility(View.GONE);
                holder.pushedTitle.setVisibility(View.GONE);
                holder.pushedDate.setVisibility(View.GONE);
            }

        }
    });
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
    @BindView(R.id.pushed_title)
    TextView pushedTitle;
    @BindView(R.id.repo_pushed)
    TextView pushedDate;
    @BindView(R.id.repo_full_name)
    TextView repoFullName;
    @BindView(R.id.repo_full_name_title)
    TextView fullNameTitle;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
