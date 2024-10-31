package com.ibayad.ccs.applications.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationInquiryByHash {

    @LocalServerPort
    private int port;

    private String endpoint;
    private Response response;

    @Given("the hash API")
    public void theAPI() {
    	this.endpoint = "http://localhost:" + port + "/applications/v1/leads";
    }

    @When("I enter {string}")
    public void iEnterHash(String hash) {
        response = RestAssured.given()
            .get(endpoint+"/"+hash);
    }

    @Then("I should get {int} as Hash API result")
    public void iShouldGetResult(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Unexpected status code", expectedStatusCode, actualStatusCode);
    }
}