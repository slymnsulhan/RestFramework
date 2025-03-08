package api.stepDefinitions;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.File;


public class tokenSteps {
    private static String token;
    static Response response;
    protected static final Logger logger = LogManager.getLogger(tokenSteps.class);
    @Given("set request for token generation")
    public void setRequestForTokenGeneration() {

            JsonObject bodyJ = new JsonObject();
        bodyJ.add("clientEmail", "tesyfdgookjko@gmail.com");
            bodyJ.add("clientName", "Bawdtytwww");
            String URL = "https://simple-books-api.glitch.me/api-clients/";

            File eeee= new File("src/test/java/api/Payloads/tokenBody.json");
            response = RestAssured.given()
                    .body(eeee)
                    .post(URL);

        logger.info("bodyJ: "+bodyJ);
        logger.info("response: "+response.asPrettyString());

            Assert.assertEquals(201,response.statusCode());
            token = response.jsonPath().getString("accessToken");
        }







}
