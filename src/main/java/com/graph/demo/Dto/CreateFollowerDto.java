package com.graph.demo.Dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateFollowerDto {

    private String userName;
    private String followerName;
    private Boolean status;
    private Boolean isAdminBlocked;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }
}
