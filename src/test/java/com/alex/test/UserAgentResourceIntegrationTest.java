package com.alex.test;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.hamcrest.core.AnyOf;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

import javax.ws.rs.core.MediaType;


import static io.restassured.RestAssured.given;


@QuarkusTest
public class UserAgentResourceIntegrationTest {


    @Test
    void testGetAllUserAgents() {
        given()
                .when().get("/mutiny/useragents/all")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void testCreateOrUpdateUserAgent(){
        String userAgent= "Test";

        given()
                .header("User-Agent",userAgent)
                .when().put("/mutiny/useragents")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON);

        given()
                .header("User-Agent",userAgent)
                .when().put("/mutiny/useragents")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);

    }

    @Test
    void testCreateOrUpdateUserAgent2() {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";

        // First, create a new user agent
        Response response = given()
                .header("User-Agent", userAgent)
                .when().put("/mutiny/useragents");

        response.then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON);

        String userAgentMutinyHash = response.getBody().jsonPath().getString("userAgentMutinyHash");

        // Now, update the existing user agent with the same userAgent string
        given()
                .header("User-Agent", userAgent)
                .when().put("/mutiny/useragents")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("userAgentMutinyHash", equalTo(userAgentMutinyHash));
    }


}
