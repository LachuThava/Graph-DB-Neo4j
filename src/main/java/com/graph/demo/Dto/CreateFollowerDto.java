package com.graph.demo.Dto;

import lombok.Getter;
import lombok.Setter;


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
}
