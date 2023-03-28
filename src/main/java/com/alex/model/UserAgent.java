package com.alex.model;


import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import org.bson.codecs.pojo.annotations.BsonId;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.sql.Timestamp;
import java.util.List;

@MongoEntity(collection = "useragent")
public class UserAgent extends PanacheMongoEntity {

    @BsonId
    public String userAgentHash;

    public String userAgentString;


    public Timestamp timestamp;



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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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

    public static List<UserAgent> findTop10ByOrderByTimestampDesc(){
        return find("ORDER BY timestamp DESC").page(Page.ofSize(10)).list();
    }


}
