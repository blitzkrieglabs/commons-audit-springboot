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

import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = com.ibayad.ccs.audit.Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class ApplicationInquiryByEmail {

    @LocalServerPort
    private int port;

    private String endpoint;
    private Response response;

    @Given("the Email API")
    public void theAPI() {
        this.endpoint = "http://localhost:" + port + "/applications/v1/leads";
    }

    @When("I enter {string} address")
    public void iEnterEmailAddress(String email) {
        Map<String, String> emailDetails = new HashMap<String, String>();
        emailDetails.put("email", email);

        response = RestAssured.given()
            .queryParams(emailDetails)
            .get(endpoint);
        System.out.println(response.getBody().asPrettyString());
    }

    @Then("I should get {int} as application inquiry result")
    public void iShouldGetStatusAsAnExistingApplication(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Unexpected status code", expectedStatusCode, actualStatusCode);
    }
    
    @Then("I should get {int} as valid email")
    public void iShouldGet200AsValidEmail(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Unexpected status code", expectedStatusCode, actualStatusCode);
    }
    
    @Then("I should get {int} as invalid email")
    public void iShouldGet400AsInvalidEmail(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Unexpected status code", expectedStatusCode, actualStatusCode);
    }
}