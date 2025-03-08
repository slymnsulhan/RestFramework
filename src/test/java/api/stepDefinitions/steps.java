package api.stepDefinitions;

import api.enumClass.HttpMethods;
import api.runners.APIBase;
import api.utils.ConfigurationReader;
import api.utils.JsonUtil;
import api.utils.Responses;
import api.utils.tokenManager;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;  // This is the correct class for handling responses
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;


public class steps extends APIBase {

    protected static final Logger logger = LogManager.getLogger(steps.class);

    static String method, bookWithId, postEndpoint, orderId;
    static String requestBody;
    static Response response;
    static JsonObject jsonObjectBody;
    static File req;

    @Before
    public void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("base.url");
    }


    @When("Set a Method Type as {string}")
    public void iSetAMethodTypeAs(String arg0) {
        method = arg0;
    }

    @Then("Sent to {string} endpoint with {string} number")
    public void iSentToEndpointWithNumber(String arg0, String arg1) {
        String specificBook = ConfigurationReader.getProperty("base.url") + arg0;
        bookWithId = specificBook + arg1;
        System.out.println(bookWithId);
    }

    @And("Call the API")
    public void iCallTheAPI() {
        response = given().relaxedHTTPSValidation()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .get(bookWithId);

        String response2 = response.jsonPath().prettify();
        logger.info(response2);
    }


    @When("the user has a valid order request body from {string}")
    public void theUserHasAValidOrderRequestBodyFrom(String arg0) {
        requestBody = arg0;

    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @And("the response should contain an {string}")
    public void theResponseShouldContainAn(String key) {
        Assert.assertTrue(response.getBody().asString().contains(key));
    }


    @Given("the user has an existing order")
    public void theUserHasAnExistingOrderWithAn() {
        this.orderId = response.jsonPath().getString("orderId");
    }


    @Then("the response should return an error with message {string}")
    public void theResponseShouldReturnAnErrorWithMessage(String expectedMessage) {

        String actualMessage = response.jsonPath().getString("error");
        logger.info(actualMessage);
        String expectedErrorMessage = expectedMessage.replace("{orderId}", this.orderId);
        Assert.assertEquals(actualMessage, expectedErrorMessage + ".");
    }

    @When("the user sends a {string} request to {string}")
    public void theUserSendsARequestTo(String httpMethod, String pathParam) {
        String endpoint = ConfigurationReader.getProperty("base.url") + pathParam;
        String token = ConfigurationReader.getProperty("token");
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET:
                response = Responses.getApiWithPath(endpoint + this.orderId, token);
                break;
            case POST:
                response = Responses.postApiWithBody(endpoint, token, String.valueOf(jsonObjectBody));
                break;
            case DELETE:
                response = Responses.deleteApiWithBody(endpoint + this.orderId, token, String.valueOf(jsonObjectBody));


        }
    }

    @When("the user has a valid order request body")
    public void theUserHasAValidOrderRequestBody() {
        jsonObjectBody = new JsonObject();
        jsonObjectBody.add("bookId", "1");
        jsonObjectBody.add("customerName", "Test123123123");

    }
}