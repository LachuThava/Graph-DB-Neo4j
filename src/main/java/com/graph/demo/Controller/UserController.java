package com.graph.demo.Controller;

import com.graph.demo.Dto.CreateFollowerDto;
import com.graph.demo.Dto.FollowersDto;
import com.graph.demo.Entity.User;
import com.graph.demo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private  UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }



//              @NOTE SAVE THE DATA AS NODE IN Neo4j
    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody CreateFollowerDto createFollowerDto) {
        return ResponseEntity.ok(userService.saveUser(createFollowerDto));
    }




//         @NOTE GET ALL THE NODES WHICH ARE I FOLLOWED
//                              NODE4
//                                ^
//                                |
//                                |
//                NODE2 <---- MY NODE ----> NODE1
//                                |
//                                |
//                                v
//                              NODE3

// It will return all the 4 nodes - {NODE1, NODE2, NODE3, NODE4}
@PostMapping("/get/who/follow/current/user")
public ResponseEntity<Set<User>> getWhoIFollow(@RequestBody FollowersDto followersDto) {
    return ResponseEntity.ok(userService.getWhoIFollow(followersDto));
}







//         @NOTE GET ALL THE NODES WHICH ARE  FOLLOWED CURRENT USERNAME
//                              NODE4
//                                |
//                                |
//                                v
//                NODE2 ---->  MY NODE  <---- NODE1
//                                ^
//                                |
//                                |
//                              NODE3

// It will return all the 4 nodes - {NODE1, NODE2, NODE3, NODE4}
    @PostMapping("/get/my/followers")
    public ResponseEntity<Set<User>> getMyFollowers(@RequestBody FollowersDto followersDto) {
        return ResponseEntity.ok(userService.getMyFollowers(followersDto));
    }


    @PostMapping("/update/followers")
    public ResponseEntity<?> updateFollowers(@RequestBody CreateFollowerDto createFollowerDto) {
        userService.addFollower(createFollowerDto.getUserName(),createFollowerDto.getFollowerName());
        return ResponseEntity.ok("Followers updated successfully");
    }


}
