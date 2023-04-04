package com.alex.model;


import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;
import java.util.List;





@MongoEntity(collection = "useragent")
public class UserAgent extends PanacheMongoEntityBase {

    @BsonId
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
