package com.graph.demo.Service;

import com.graph.demo.Dto.CreateFollowerDto;
import com.graph.demo.Dto.FollowersDto;
import com.graph.demo.Entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserService {

    Set<User> getMyFollowers(FollowersDto followersDto);

    Set<User> getWhoIFollow(FollowersDto followersDto);

    Long getMyFollowerCount(FollowersDto followersDto);

    void addFollower(String userName, String followerName);

    void removeFollower(String userName, String followerName);

    User findByUserName(String userName);

    boolean isFollowing(String userName, String followerName);

    public void deleteByAuthUserId(String userName);

    User saveUser(CreateFollowerDto createFollowerDto);

}
