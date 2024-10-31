package com.ibayad.ccs.applications;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = "com.ibayad.ccs.applications.steps", tags = "not @skip")
public class CucumberTest {

}