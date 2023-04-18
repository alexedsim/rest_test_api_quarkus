package com.alex.service;

import com.alex.exception.UserAgentCreationException;
import com.alex.exception.UserAgentUpdateException;
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
import java.util.logging.Logger;


@ApplicationScoped
public class UserAgentServiceMutiny {

    private static final Logger LOG = Logger.getLogger("UserAgentServiceMutiny");

    public Uni<Tuple2<Boolean,UserAgentMutiny>> createOrUpdateUserAgent(String userAgentString){
        String userAgentHash = hashUserAgent(userAgentString);
        return UserAgentMutiny.findByUserAgentMutinyHash(userAgentHash)
                .onItem().transformToUni( optEntity -> doUpdateOrCreate(userAgentString, userAgentHash, optEntity));
    }

    private static Uni<Tuple2<Boolean,UserAgentMutiny>> doUpdateOrCreate(String userAgentString, String userAgentHash, Optional<UserAgentMutiny> optEntity) {
        Date date= Date.from(Instant.now());
        if(optEntity.isPresent()){
            return doUpdate(userAgentString, userAgentHash, optEntity, date);
        }else{
            return doCreate(userAgentString, userAgentHash, date);
        }
    }

    private static Uni<Tuple2<Boolean, UserAgentMutiny>> doCreate(String userAgentString, String userAgentHash, Date date) {
        LOG.info("The object with user agent string: "+ userAgentString +" and user agent hash: "+ userAgentHash +" is not present.Creating.");
        UserAgentMutiny newUserAgent = new UserAgentMutiny(userAgentHash, userAgentString);
        newUserAgent.setTimestamp(date);
        return newUserAgent.persist().map(ignore -> Tuple2.of(true, newUserAgent))
                .onFailure().recoverWithItem(throwable -> {
                    throw new UserAgentCreationException("Could not create user agent.");
                });
    }

    private static Uni<Tuple2<Boolean, UserAgentMutiny>> doUpdate(String userAgentString, String userAgentHash, Optional<UserAgentMutiny> optEntity, Date date) {
        LOG.info("The object with user agent string: "+ userAgentString +" and user agent hash: "+ userAgentHash +" is already present.Updating.");
        optEntity.get().setTimestamp(date);
        return UserAgentMutiny.update(optEntity.get()).map(ignore -> Tuple2.of(false, optEntity.get()))
                .onFailure().recoverWithItem(throwable -> {
                    throw new UserAgentUpdateException("Could not update user agent.");
                });
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
