package com.alex.model;


import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.panache.common.Sort;

import java.util.Date;
import java.util.List;





@MongoEntity(collection = "useragent")
public class UserAgent extends PanacheMongoEntity{


    public String userAgentHash;

    public String userAgentString;


    public Date timestamp;


    public UserAgent() {
    }

    public UserAgent(String userAgentHash, String userAgentString) {
        this.userAgentHash = userAgentHash;
        this.userAgentString = userAgentString;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserAgentHash() {
        return userAgentHash;
    }

    public void setUserAgentHash(String userAgentHash) {
        this.userAgentHash = userAgentHash;
    }

    public static UserAgent findByUserAgentHash(String userAgentHash){
        return find("userAgentHash", userAgentHash).firstResult();
    }
    /*
    public static List<UserAgent> findTop10ByOrderByTimestampDesc(){
        return find("ORDER BY timestamp DESC").page(Page.ofSize(10)).list();
    }

    public static List<UserAgent> findTop10ByOrderByTimestampDesc() {
        return UserAgent.<UserAgent>find("ORDER BY timestamp DESC").page(Page.ofSize(10)).list();
    }

    public static List<UserAgent> findTop10ByOrderByTimestampDesc() {
        String query = "ORDER BY timestamp DESC";
        List<UserAgent> results = find(query).page(Page.ofSize(10)).list();

        return results;
    }
    */
    public static List<UserAgent> findTop10ByOrderByTimestampDesc() {
        String query = "{ }";
        String orderBy = "timestamp";
        List<UserAgent> results = find(query, Sort.descending(orderBy)).page(Page.ofSize(10)).list();

        return results;
    }

    public static List<UserAgent> returnAllUserAgents() {
        return listAll();
    }
    public static void deleteAllUserAgents(){
        deleteAll();
    }

}
