package api.endpoints;
import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.ResourceBundle;

//The endpoints are taken from routes.properties file
public class UserEndpoints2 {
    //Created for getting URLs from properties file
    static ResourceBundle getURL(){
        ResourceBundle routes = ResourceBundle.getBundle("routes"); //Load properties file from resource folder
        return routes;
    }
    public static Response createUser(User payload){
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post(getURL().getString("post_url"));
        return response;
    }

    public static Response readUser(String userName){
        Response response =
                given()
                        .pathParam("username", userName)
                        .when()
                        .get(getURL().getString("get_url"));
        return response;
    }

    public static Response updateUser(String userName, User payload){
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .pathParam("username", userName)
                        .body(payload)
                        .when()
                        .put(getURL().getString("update_url"));
        return response;
    }

    public static Response deleteUser(String userName){
        Response response =
                given()
                        .pathParam("username", userName)
                        .when()
                        .get(getURL().getString("delete_url"));
        return response;
    }
}
