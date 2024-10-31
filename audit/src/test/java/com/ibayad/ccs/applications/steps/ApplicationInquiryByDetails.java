package com.ibayad.ccs.applications.steps;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;

//@ContextConfiguration(classes = com.ibayad.ccs.applications.Application.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@CucumberContextConfiguration
public class ApplicationInquiryByDetails {

    @LocalServerPort
    private int port;

    private String endpoint;
    private Response response;

    @Given("the Details API")
    public void theAPI() {
    	this.endpoint = "http://localhost:" + port + "/applications/v1/leads";
    }

    @When("I enter details {string}, {string}, {string}, {string}, and {string}")
    public void iEnterDetails(String lastname, String firstname, String middlename, String gender, String birthdate) {
        Map<String, String> userDetails = new HashMap<String, String>();
        userDetails.put("last_name", lastname);
        userDetails.put("first_name", firstname);
        userDetails.put("middle_name", middlename);
        userDetails.put("date_of_birth", birthdate);

        response = RestAssured.given()
            .contentType("application/json")
            .queryParams(userDetails)
            .get(endpoint);
    }

    @Then("I should get {int} as Details API result")
    public void iShouldGetStatusAsAnExistingApplication(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Unexpected status code", expectedStatusCode, actualStatusCode);
    }
}
