package com.graph.demo.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.graph.demo.Entity.User;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowersDto {

    private Long id;
    private String userName;
    private Boolean status;
    private Boolean isAdminBlocked;
    private Set<User> following;
    private Set<User> followedBy;
    private long createdAt;
    private long updatedAt;


    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAdminBlocked() {
        return isAdminBlocked;
    }

    public void setAdminBlocked(Boolean adminBlocked) {
        isAdminBlocked = adminBlocked;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(Set<User> followedBy) {
        this.followedBy = followedBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
