package com.example.githubtrainingappjava.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "owner_table")
public class Owner implements Parcelable {
    public Owner() {
        super();
    }

    @SerializedName("login")
        @Expose
        private String login;

       @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("node_id")
        @Expose
        private String nodeId;
        @SerializedName("avatar_url")
        @Expose
        private String avatarUrl;
        @SerializedName("gravatar_id")
        @Expose
        private String gravatarId;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("html_url")
        @Expose
        private String htmlUrl;
        @SerializedName("followers_url")
        @Expose
        private String followersUrl;
        @SerializedName("following_url")
        @Expose
        private String followingUrl;
        @SerializedName("gists_url")
        @Expose
        private String gistsUrl;
        @SerializedName("starred_url")
        @Expose
        private String starredUrl;
        @SerializedName("subscriptions_url")
        @Expose
        private String subscriptionsUrl;
        @SerializedName("organizations_url")
        @Expose
        private String organizationsUrl;
        @SerializedName("repos_url")
        @Expose
        private String reposUrl;
        @SerializedName("events_url")
        @Expose
        private String eventsUrl;
        @SerializedName("received_events_url")
        @Expose
        private String receivedEventsUrl;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("site_admin")
        @Expose
        private Boolean siteAdmin;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("blog")
        @Expose
        private String blog;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("hireable")
        @Expose
        private Boolean hireable;
        @SerializedName("bio")
        @Expose
        private String bio;
        @SerializedName("public_repos")
        @Expose
        private Integer publicRepos;
        @SerializedName("public_gists")
        @Expose
        private Integer publicGists;
        @SerializedName("followers")
        @Expose
        private Integer followers;
        @SerializedName("following")
        @Expose
        private Integer following;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("private_gists")
        @Expose
        private Integer privateGists;
        @SerializedName("total_private_repos")
        @Expose
        private Integer totalPrivateRepos;
        @SerializedName("owned_private_repos")
        @Expose
        private Integer ownedPrivateRepos;
        @SerializedName("disk_usage")
        @Expose
        private Integer diskUsage;
        @SerializedName("collaborators")
        @Expose
        private Integer collaborators;
        @SerializedName("two_factor_authentication")
        @Expose
        private Boolean twoFactorAuthentication;
//        @SerializedName("plan")
//        @Expose
//        private Plan plan;

    protected Owner(Parcel in) {
        login = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        nodeId = in.readString();
        avatarUrl = in.readString();
        gravatarId = in.readString();
        url = in.readString();
        htmlUrl = in.readString();
        followersUrl = in.readString();
        followingUrl = in.readString();
        gistsUrl = in.readString();
        starredUrl = in.readString();
        subscriptionsUrl = in.readString();
        organizationsUrl = in.readString();
        reposUrl = in.readString();
        eventsUrl = in.readString();
        receivedEventsUrl = in.readString();
        type = in.readString();
        byte tmpSiteAdmin = in.readByte();
        siteAdmin = tmpSiteAdmin == 0 ? null : tmpSiteAdmin == 1;
        name = in.readString();
        company = in.readString();
        blog = in.readString();
        location = in.readString();
        email = in.readString();
        byte tmpHireable = in.readByte();
        hireable = tmpHireable == 0 ? null : tmpHireable == 1;
        bio = in.readString();
        if (in.readByte() == 0) {
            publicRepos = null;
        } else {
            publicRepos = in.readInt();
        }
        if (in.readByte() == 0) {
            publicGists = null;
        } else {
            publicGists = in.readInt();
        }
        if (in.readByte() == 0) {
            followers = null;
        } else {
            followers = in.readInt();
        }
        if (in.readByte() == 0) {
            following = null;
        } else {
            following = in.readInt();
        }
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            privateGists = null;
        } else {
            privateGists = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPrivateRepos = null;
        } else {
            totalPrivateRepos = in.readInt();
        }
        if (in.readByte() == 0) {
            ownedPrivateRepos = null;
        } else {
            ownedPrivateRepos = in.readInt();
        }
        if (in.readByte() == 0) {
            diskUsage = null;
        } else {
            diskUsage = in.readInt();
        }
        if (in.readByte() == 0) {
            collaborators = null;
        } else {
            collaborators = in.readInt();
        }
        byte tmpTwoFactorAuthentication = in.readByte();
        twoFactorAuthentication = tmpTwoFactorAuthentication == 0 ? null : tmpTwoFactorAuthentication == 1;
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public void setGravatarId(String gravatarId) {
            this.gravatarId = gravatarId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public String getFollowersUrl() {
            return followersUrl;
        }

        public void setFollowersUrl(String followersUrl) {
            this.followersUrl = followersUrl;
        }

        public String getFollowingUrl() {
            return followingUrl;
        }

        public void setFollowingUrl(String followingUrl) {
            this.followingUrl = followingUrl;
        }

        public String getGistsUrl() {
            return gistsUrl;
        }

        public void setGistsUrl(String gistsUrl) {
            this.gistsUrl = gistsUrl;
        }

        public String getStarredUrl() {
            return starredUrl;
        }

        public void setStarredUrl(String starredUrl) {
            this.starredUrl = starredUrl;
        }

        public String getSubscriptionsUrl() {
            return subscriptionsUrl;
        }

        public void setSubscriptionsUrl(String subscriptionsUrl) {
            this.subscriptionsUrl = subscriptionsUrl;
        }

        public String getOrganizationsUrl() {
            return organizationsUrl;
        }

        public void setOrganizationsUrl(String organizationsUrl) {
            this.organizationsUrl = organizationsUrl;
        }

        public String getReposUrl() {
            return reposUrl;
        }

        public void setReposUrl(String reposUrl) {
            this.reposUrl = reposUrl;
        }

        public String getEventsUrl() {
            return eventsUrl;
        }

        public void setEventsUrl(String eventsUrl) {
            this.eventsUrl = eventsUrl;
        }

        public String getReceivedEventsUrl() {
            return receivedEventsUrl;
        }

        public void setReceivedEventsUrl(String receivedEventsUrl) {
            this.receivedEventsUrl = receivedEventsUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getSiteAdmin() {
            return siteAdmin;
        }

        public void setSiteAdmin(Boolean siteAdmin) {
            this.siteAdmin = siteAdmin;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getBlog() {
            return blog;
        }

        public void setBlog(String blog) {
            this.blog = blog;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getHireable() {
            return hireable;
        }

        public void setHireable(Boolean hireable) {
            this.hireable = hireable;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public Integer getPublicRepos() {
            return publicRepos;
        }

        public void setPublicRepos(Integer publicRepos) {
            this.publicRepos = publicRepos;
        }

        public Integer getPublicGists() {
            return publicGists;
        }

        public void setPublicGists(Integer publicGists) {
            this.publicGists = publicGists;
        }

        public Integer getFollowers() {
            return followers;
        }

        public void setFollowers(Integer followers) {
            this.followers = followers;
        }

        public Integer getFollowing() {
            return following;
        }

        public void setFollowing(Integer following) {
            this.following = following;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getPrivateGists() {
            return privateGists;
        }

        public void setPrivateGists(Integer privateGists) {
            this.privateGists = privateGists;
        }

        public Integer getTotalPrivateRepos() {
            return totalPrivateRepos;
        }

        public void setTotalPrivateRepos(Integer totalPrivateRepos) {
            this.totalPrivateRepos = totalPrivateRepos;
        }

        public Integer getOwnedPrivateRepos() {
            return ownedPrivateRepos;
        }

        public void setOwnedPrivateRepos(Integer ownedPrivateRepos) {
            this.ownedPrivateRepos = ownedPrivateRepos;
        }

        public Integer getDiskUsage() {
            return diskUsage;
        }

        public void setDiskUsage(Integer diskUsage) {
            this.diskUsage = diskUsage;
        }

        public Integer getCollaborators() {
            return collaborators;
        }

        public void setCollaborators(Integer collaborators) {
            this.collaborators = collaborators;
        }

        public Boolean getTwoFactorAuthentication() {
            return twoFactorAuthentication;
        }

        public void setTwoFactorAuthentication(Boolean twoFactorAuthentication) {
            this.twoFactorAuthentication = twoFactorAuthentication;
        }

//        public Plan getPlan() {
//            return plan;
//        }
//
//        public void setPlan(Plan plan) {
//            this.plan = plan;
//        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(nodeId);
        dest.writeString(avatarUrl);
        dest.writeString(gravatarId);
        dest.writeString(url);
        dest.writeString(htmlUrl);
        dest.writeString(followersUrl);
        dest.writeString(followingUrl);
        dest.writeString(gistsUrl);
        dest.writeString(starredUrl);
        dest.writeString(subscriptionsUrl);
        dest.writeString(organizationsUrl);
        dest.writeString(reposUrl);
        dest.writeString(eventsUrl);
        dest.writeString(receivedEventsUrl);
        dest.writeString(type);
        dest.writeByte((byte) (siteAdmin == null ? 0 : siteAdmin ? 1 : 2));
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(blog);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeByte((byte) (hireable == null ? 0 : hireable ? 1 : 2));
        dest.writeString(bio);
        if (publicRepos == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(publicRepos);
        }
        if (publicGists == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(publicGists);
        }
        if (followers == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(followers);
        }
        if (following == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(following);
        }
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        if (privateGists == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(privateGists);
        }
        if (totalPrivateRepos == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalPrivateRepos);
        }
        if (ownedPrivateRepos == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ownedPrivateRepos);
        }
        if (diskUsage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(diskUsage);
        }
        if (collaborators == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(collaborators);
        }
        dest.writeByte((byte) (twoFactorAuthentication == null ? 0 : twoFactorAuthentication ? 1 : 2));
    }
}