import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class reqresTests extends TestBase {

    @DisplayName("Запрос на регистрацию пользователя. POST - SUCCESSFUL REGISTRATION")
    @Test
    void successfulRegistrationTest() {

        String regData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

            .when()
                .post("/register")

            .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @DisplayName("Запрос на попытку регистрации без пароля. POST - UNSUCCESSFUL REGISTRATION")
    @Test
    void unsuccessfulRegistrationWithoutPasswordTest() {

        String regData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

            .when()
                .post("/register")

            .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @DisplayName("Запрос на изменение данных пользователя. POST - CREATE")
    @Test
    void successfulCreateTest() {

        String createData = "{\"name\": \"marpheus\", \"job\": \"leader\"}";

        given()
                .body(createData)
                .contentType(JSON)
                .log().uri()

            .when()
                .post("/users")

            .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("job", is("leader"));
    }

    @DisplayName("Запрос на поиск пользователя по id. GET - SINGLE USER")
    @Test
    void successfulFoundUserTest() {

        given()
                .log().uri()

            .when()
                .get("/users/7")

            .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(7))
                .body("data.first_name", is("Michael"))
                .body("data.last_name", is("Lawson"));
    }

    @DisplayName("Запрос на поиск несуществующего id пользователя. GET - SINGLE USER NOT FOUND")
    @Test
    void unsuccessfulFoundUserTest() {

        given()
                .log().uri()

                .when()
                .get("/users/666")

                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
