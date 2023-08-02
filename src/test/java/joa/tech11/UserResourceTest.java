package joa.tech11;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @Test
    @Order(2)
    void getUsers() {
        given()
                .when()
                .get("/v1/user")
                .then()
                .body("size()", equalTo(1))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(3)
    void getUser() {
        given()
                .when()
                .get("/v1/user/1")
                .then()
                //.body("size()", equalTo(1))
                .body("firstName", equalTo("Jeffry"))
                .body("lastName", equalTo("Adams"))
                .body("email", equalTo("frexjeff@gmail.com"))
                .body("birthday", equalTo("1990-10-05"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void addUSer() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("firstName", "Jeffry")
                .add("lastName", "Adams")
                .add("email", "frexjeff@gmail.com")
                .add("birthday", "1990-10-05")
                .add("password", BcryptUtil.bcryptHash("12345"))
                .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/v1/user")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(4)
    void editUser() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("firstName", "Jeffrey")
                .add("lastName", "Adams")
                .add("email", "frexjeff@gmail.com")
                .add("birthday", "1990-10-05")
                .add("password", BcryptUtil.bcryptHash("12345"))
                .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/v1/user/1")
                .then()
                .body("firstName", equalTo("Jeffrey"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(5)
    void deleteUser() {
        given()
                .when()
                .delete("/v1/user/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }
}