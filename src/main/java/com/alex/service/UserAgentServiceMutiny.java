package com.alex.service;

import com.alex.model.UserAgentMutiny;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.ext.auth.User;


import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class UserAgentServiceMutiny {


    public Uni<Tuple2<Boolean,UserAgentMutiny>> createOrUpdateUserAgent(String userAgentString){
        String userAgentHash = hashUserAgent(userAgentString);
        return UserAgentMutiny.findByUserAgentMutinyHash(userAgentHash)
                .onItem().transformToUni( optEntity -> doUpdateOrCreate(userAgentString, userAgentHash, optEntity));
    }
    /*
    private static Uni<Void> doUpdateOrCreate(String userAgentString, String userAgentHash, Optional<UserAgentMutiny> optEntity) {
        Date date= Date.from(Instant.now());
        if(optEntity.isPresent()){
            optEntity.get().setTimestamp(date);
            return UserAgentMutiny.update(optEntity.get()).map(ignore -> null);
        }else{
            UserAgentMutiny newUserAgent = new UserAgentMutiny(userAgentHash, userAgentString);
            newUserAgent.setTimestamp(date);
            return newUserAgent.persist().map(ignore -> null);
        }
    }

     */
    private static Uni<Tuple2<Boolean,UserAgentMutiny>> doUpdateOrCreate(String userAgentString, String userAgentHash, Optional<UserAgentMutiny> optEntity) {
        Date date= Date.from(Instant.now());
        if(optEntity.isPresent()){
            optEntity.get().setTimestamp(date);
            return UserAgentMutiny.update(optEntity.get()).map(ignore -> Tuple2.of(false,optEntity.get()));
        }else{
            UserAgentMutiny newUserAgent = new UserAgentMutiny(userAgentHash, userAgentString);
            newUserAgent.setTimestamp(date);
            return newUserAgent.persist().map(ignore -> Tuple2.of(true,newUserAgent));
        }
    }


    public Uni<UserAgentMutiny> findWithHash(String hash){
        return UserAgentMutiny.findByUserAgentMutinyHashNoOptional(hash);
    }


    public Uni<List<UserAgentMutiny>> getLastTenUserAgents() {
            return UserAgentMutiny.findTop10ByOrderByTimestampDesc();
        }


    private String hashUserAgent(String userAgentString) {
        // Use SHA-256 hashing algorithm to create a hash of the user agent string
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(userAgentString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to create user agent hash: " + e.getMessage(), e);
        }
    }

    public Uni<List<UserAgentMutiny>> getAllUserAgents() {
        return UserAgentMutiny.returnAllUserAgents();
    }
    public Uni<Void> removeAllUsers(){
       return UserAgentMutiny.deleteAllUserAgentsMutiny();
    }

}
