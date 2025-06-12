package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

public class BooksStepDefinitions {
    private Response response;
    private static final String BASE_URL = "http://simple-books-api.glitch.me";

    @Given("I send a GET request to the Books API")
    public void sendGetRequestToBooksAPI() {
        response = RestAssured.given()
                .when()
                .get(BASE_URL + "/Books");
    }

    @Given("the Books API is available")
    public void theBooksAPIIsAvailable() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("I hit an invalid books endpoint")
    public void i_hit_an_invalid_books_endpoint() {
        response = when().get("/books?type=invalid"); // Triggers 400
    }

    @Then("I should receive a 400 status code")
    public void i_should_receive_a_400_status_code() {
        System.out.println("Test 400  response"); 
        assertEquals(400, response.getStatusCode());
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