package com.alex.service;

import com.alex.model.UserAgent;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserAgentService {

    @Transactional
    public void createUserAgent(String userAgentString) {
        String userAgentHash = hashUserAgent(userAgentString);
        Optional<UserAgent> existingUserAgent = UserAgent.find("userAgentHash", userAgentHash).firstResultOptional();
        if (existingUserAgent.isPresent()) {
            UserAgent userAgentToUpdate = existingUserAgent.get();
            userAgentToUpdate.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            userAgentToUpdate.persist();
        } else {
            UserAgent newUserAgent = new UserAgent(userAgentHash, userAgentString);
            newUserAgent.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            newUserAgent.persist();
        }
    }

    public List<UserAgent> getLastTenUserAgents() {
        return UserAgent.findTop10ByOrderByTimestampDesc();
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
}
