package com.alex.model;



import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import io.smallrye.mutiny.Uni;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@MongoEntity(collection = "useragentmutiny")
public class UserAgentMutiny extends ReactivePanacheMongoEntityBase {


    @BsonId
    public String userAgentMutinyHash;

    public String userAgentMutinyString;


    public Date timestamp;


    public UserAgentMutiny() {
    }

    public UserAgentMutiny(String userAgentMutinyHash, String userAgentMutinyString) {
        this.userAgentMutinyHash = userAgentMutinyHash;
        this.userAgentMutinyString = userAgentMutinyString;
    }

    public String getUserAgentMutinyString() {
        return userAgentMutinyString;
    }

    public void setUserAgentMutinyString(String userAgentMutinyString) {
        this.userAgentMutinyString = userAgentMutinyString;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserAgentMutinyHash() {
        return userAgentMutinyHash;
    }

    public void setUserAgentMutinyHash(String userAgentMutinyHash) {
        this.userAgentMutinyHash = userAgentMutinyHash;
    }
    public static Uni<Optional<UserAgentMutiny>> findByUserAgentMutinyHash(String userAgentMutinyHash) {
        return find("_id", userAgentMutinyHash).firstResult()
                .onItem().transform(userAgentMutiny ->Optional.ofNullable((UserAgentMutiny) userAgentMutiny));


    }

    public static Uni<UserAgentMutiny> findByUserAgentMutinyHashNoOptional(String userAgentMutinyHash) {
        return find("_id", userAgentMutinyHash).firstResult();
    }

    public static Uni<List<UserAgentMutiny>> findTop10ByOrderByTimestampDesc() {
        String query = "{ }";
        String orderBy = "timestamp";
        return find(query, Sort.descending(orderBy)).page(Page.ofSize(10)).list();
    }

    public static Uni<List<UserAgentMutiny>> returnAllUserAgents() {

        return listAll();
    }
    public static Uni<Void> deleteAllUserAgentsMutiny(){
        return deleteAll().map(ignore -> null);
    }


}
