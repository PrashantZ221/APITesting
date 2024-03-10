package api.test;

import api.endpoints.UserEndpoints2;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class UserTest2 {

    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeClass
    public void setupData(){
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.number().randomDigit());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setUsername(faker.phoneNumber().cellPhone());

        //Logs
        logger= LogManager.getLogger();
    }
    @Test(priority=1)
    public void testPostUser(){
        logger.info("Creating a user");
        Response response = UserEndpoints2.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("User is created");
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("Reading user info");
        Response response = UserEndpoints2.readUser(userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("User info is displayed");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {
        logger.info("Updating a user");
        //Update data using payload
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpoints2.updateUser(userPayload.getUsername(), userPayload);
        response.then().log().body().statusCode(200); //This is also assertion from rest assured
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 200); // This validation is from TestNG

        Response responseAfterUpdate = UserEndpoints2.readUser(userPayload.getUsername());
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
        logger.info("User is updated");
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        logger.info("Deleting a user");
        Response response = UserEndpoints2.deleteUser(userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("User is deleted");
    }


}
