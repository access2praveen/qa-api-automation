package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

public class BooksStepDefinitions {
    private Response response;
    private static final String BASE_URL = "http://simple-books-api.glitch.me";

    @Given("I send a GET request to the Books API")
    public void sendGetRequestToBooksAPI() {
        response = RestAssured.given()
                .when()
                .get(BASE_URL + "/Books");
    }

    @Then("the status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("the response time should be less than {int} milliseconds")
    public void verifyResponseTime(int maxResponseTime) {
        Assert.assertTrue("Response time should be less than " + maxResponseTime + "ms",
                response.getTime() < maxResponseTime);
    }

    @Then("the response size should be less than {int} KB")
    public void verifyResponseSize(int maxSizeKB) {
        int responseSizeBytes = response.getBody().asString().getBytes().length;
        int maxSizeBytes = maxSizeKB * 1024;
        Assert.assertTrue("Response size should be less than " + maxSizeKB + "KB",
                responseSizeBytes < maxSizeBytes);
    }

    @Then("the response should contain atleast one book with type {string}")
    public void verifyBookType(String bookType) {
        Assert.assertTrue("Response should contain at least one book with type " + bookType,
                response.getBody().asString().contains("\"type\":\"" + bookType + "\""));
    }
    
} 