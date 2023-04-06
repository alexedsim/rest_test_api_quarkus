package com.alex.model;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;
import java.util.List;

@MongoEntity(collection = "useragentmutiny")
public class UserAgentMutiny extends PanacheMongoEntityBase {


    @BsonId
    public String userAgentHash;

    public String userAgentString;


    public Date timestamp;


    public UserAgentMutiny() {
    }

    public UserAgentMutiny(String userAgentHash, String userAgentString) {
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

    public static Uni<UserAgent> findByUserAgentHash(String userAgentHash){
        return Uni.createFrom().item(find("userAgentHash", userAgentHash).firstResult());
    }

    public static Uni<List<UserAgent>> findTop10ByOrderByTimestampDesc() {
        String query = "{ }";
        String orderBy = "timestamp";
        List<UserAgent> results = find(query, Sort.descending(orderBy)).page(Page.ofSize(10)).list();

        return Uni.createFrom().item(results);
    }

    public static Uni<List<UserAgent>> returnAllUserAgents() {

        return Uni.createFrom().item(listAll());
    }
    public static void deleteAllUserAgents(){
        deleteAll();
    }
}
