package api.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Responses {
    static Response response;
    public static Response getApiWithPath(String URI, String token){

        response = given().relaxedHTTPSValidation()
                .auth().preemptive().oauth2(token)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .get(URI);

        return response;
    }

    public static Response postApiWithBody(String URI, String token, String path){

        File pathBody = new File(path);
        response = given().relaxedHTTPSValidation().auth().preemptive().oauth2(token)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(path)
                .post(URI);
        return response;
    }

    public static Response deleteApiWithBody(String URI, String token, String path){

        response = given().relaxedHTTPSValidation()
                .auth().preemptive().oauth2(token)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(path)
                .delete(URI);
        return response;
    }

    public static Response postApiWithNoBody(String URI, String pathBody){
        response = given().relaxedHTTPSValidation()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(pathBody)
                .post(URI);
        return response;
    }

}
