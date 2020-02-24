package nl.blackstardlb.instabot;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class Stepdefs {
    static {
        RestAssured.port = 8080;
        RestAssured.basePath = "/api";
        RestAssured.baseURI = "http://localhost";
    }

    private Map<String, Object> headers = new HashMap<>();
    private ValidatableResponse response;

    @Given("User not authenticated")
    public void user_not_authenticated() {
//        headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.remove(HttpHeaders.AUTHORIZATION);
    }

    @When("User asks for managed servers")
    public void user_asks_for_managed_servers() {
        response = given()
                .headers(headers)
                .when()
                .get("/users/me/manageable-servers")
                .then();
    }

    @Then("Unauthenticated Error")
    public void unauthenticated_Error() {
        response.statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Given("blackstardlb authenticated")
    public void blackstardlbAuthenticated() {
        headers.put(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", Constants.TOKEN));
    }

    @Then("{int} servers")
    public void servers(int amountOfServers) {
        response.rootPath("_embedded.manageableServers").body("$", hasSize(amountOfServers));
    }

    @When("User asks for server {}")
    public void userAsksForServer(String serverId) {
        response = given()
                .headers(headers)
                .when()
                .get(String.format("/servers/%s", serverId))
                .then();
    }

    @Then("Server with id {}")
    public void serverWithId(String serverId) {
        response.body("id", equalTo(serverId));
    }
}
