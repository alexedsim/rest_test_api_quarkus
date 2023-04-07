package com.alex.service;

import com.alex.model.UserAgentMutiny;
import io.smallrye.mutiny.Uni;


import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;



@ApplicationScoped
public class UserAgentServiceMutiny {


    public Uni<Void> createUserAgent(String userAgentString){
        String userAgentHash = hashUserAgent(userAgentString);
        return UserAgentMutiny.findByUserAgentMutinyHash(userAgentHash)
                .onItem().transformToUni( (optEntity) ->{
                    if(optEntity != null){
                        optEntity.setTimestamp(Date.from(Instant.now()));
                        return optEntity.persist().map(ignore -> null);
                    }else{
                        UserAgentMutiny newUserAgent = new UserAgentMutiny(userAgentHash,userAgentString);
                        newUserAgent.setTimestamp(Date.from(Instant.now()));
                        return newUserAgent.persist().map(ignore -> null);
                    }
                });
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
