package com.graph.demo.Service.Impl;

import com.graph.demo.Dto.CreateFollowerDto;
import com.graph.demo.Dto.FollowersDto;
import com.graph.demo.Entity.User;
import com.graph.demo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.summary.ResultSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  Neo4jClient neo4jClient;

    UserServiceImpl(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Override
    public Set<User> getMyFollowers(FollowersDto followersDto) {
        String query = "MATCH (a:User {userName: $userName})<-[:FOLLOWS]-(users) " +
                "WHERE users.status = true AND users.isAdminBlocked = false " +
                "RETURN users";



        Set<User> users = new HashSet<>(neo4jClient.query(query)
                .bind(followersDto.getUserName()).to("userName")
                .fetchAs(User.class)
                .mappedBy((typeSystem, record) -> {
                    User user = new User();
                    user.setUserName(record.get("users").get("userName").asString());
                    return user;
                })
                .all());

        return users;
    }

    @Override
    public Set<User> getWhoIFollow(FollowersDto followersDto) {
        String query = "MATCH (a:User {userName: $userName})-[:FOLLOWS]->(users) " +
                "WHERE users.status = true AND users.isAdminBlocked = false " +
                "RETURN users";

        return new HashSet<>(neo4jClient.query(query)
                .bind(followersDto.getUserName()).to("userName")
                .fetchAs(User.class)
                .mappedBy((typeSystem, record) -> {
                    User follower = new User();
                    follower.setUserName(record.get("users").get("userName").asString());
                    return follower;
                })
                .all());
    }

    @Override
    public Long getMyFollowerCount(FollowersDto followersDto) {
        String query = "MATCH (a:User {userName: $userName})<-[:FOLLOWS]-(users) " +
                "WHERE users.status = true AND users.isAdminBlocked = false " +
                "RETURN count(users) AS count";

        return neo4jClient.query(query)
                .bind(followersDto.getUserName()).to("userName")
                .fetchAs(Long.class)
                .one()
                .orElse(0L);
    }

    @Override
    public void addFollower(String userName, String followerName) {
        String query = "MATCH (a:User), (b:User) " +
                "WHERE a.userName = $fromUserId AND b.userName = $toUserId " +
                "CREATE (a)-[:FOLLOWS]->(b) " +
                "RETURN a, b";


        ResultSummary resultSummary = neo4jClient.query(query)
                .bind(userName).to("fromUserId")
                .bind(followerName).to("toUserId")
                .run();
    }

    @Override
    public void removeFollower(String userName, String followerName) {
        String query = "MATCH (a:User {userName: $fromUserId})-[r:FOLLOWS]->(b:User {userName: $toUserId}) "+
                "DELETE r ";

        ResultSummary resultSummary = neo4jClient.query(query)
                .bind(userName).to("fromUserId")
                .bind(followerName).to("toUserId")
                .run();
    }

    @Override
    public User findByUserName(String userName) {
        String query = "MATCH (a:User {userName: $fromUserName}) " +
                "RETURN a";

        return neo4jClient.query(query)
                .bind(userName).to("fromUserName")
                .fetchAs(User.class)
                .mappedBy((typeSystem, record) -> {
                    User user = new User();
                    user.setUserName(record.get("a").get("userName").asString());
                    user.setStatus(record.get("a").get("status").asBoolean());
                    user.setAdminBlocked(record.get("a").get("isAdminBlocked").asBoolean());
                    user.setCreatedAt(record.get("a").get("createdAt").asLong());
                    // Map other fields as needed
                    return user;
                })
                .one()
                .orElse(null);
    }

    @Override
    public boolean isFollowing(String userName, String followerName) {
        String query = "MATCH (a:User {userName: $fromUserId})-[:FOLLOWS]->(b:User {userName: $toUserId}) " +
                "WHERE b.isAdminBlocked = false " +
                "RETURN count(b) > 0 AS following";


        return neo4jClient.query(query)
                .bind(userName).to("fromUserId")
                .bind(followerName).to("toUserId")
                .fetchAs(Boolean.class)
                .mappedBy((typeSystem, record) -> record.get("following").asBoolean())
                .one()
                .orElse(false);
    }

    @Override
    public void deleteByAuthUserId(String userName) {
        String query = "MATCH (a:User {userName: $authUserId}) " +
                "SET a.status = false " +
                "RETURN a";

        ResultSummary resultSummary = neo4jClient.query(query)
                .bind(userName).to("authUserId")
                .run();

    }

    @Override
    public User saveUser(CreateFollowerDto createFollowerDto) {
        String query = "CREATE (n:User { " +
                "userName: $userName, " +
                "status: true, " +
                "isAdminBlocked: false, " +
                "createdAt: $createdAt, " +
                "updatedAt: 0 " +
                "}) RETURN n";

        long currentEpochTime = Instant.now().getEpochSecond();

        return neo4jClient.query(query)
                .bind(createFollowerDto.getUserName()).to("userName")
                .bind(currentEpochTime).to("createdAt")
                .fetchAs(User.class)
                .mappedBy((typeSystem, record) -> {
                    Map<String, Object> properties = record.get("n").asNode().asMap();
                    User user = new User();
                    user.setUserName((String) properties.get("userName"));
                    user.setStatus((Boolean) properties.get("status"));
                    user.setAdminBlocked((Boolean) properties.get("isAdminBlocked"));
                    user.setCreatedAt((Long) properties.get("createdAt"));
                    user.setUpdatedAt((Long) properties.get("updatedAt"));
                    return user;
                })
                .one()
                .orElseThrow(() -> new IllegalStateException("Failed to create FollowersData node"));
    }
}
