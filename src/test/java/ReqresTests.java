import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @Test
    void singleUserTest(){
        Integer expectedId = 2;
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(expectedId));
    }

    @Test
    void singleUserNotFound(){
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void successfulRegisterTest(){
        String expectedToken = "QpwL5tke4Pnpja7X4";
        Integer expectedId = 4;
        given()
                .contentType(ContentType.JSON)
                .body("{" +
                        "\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is(expectedToken))
                .body("id", is(expectedId));
    }

    @Test
    void unsuccessfulRegistrationTest(){
        String expectedMgs = "Missing password";
        given()
                .contentType(ContentType.JSON)
                .body("{" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", is(expectedMgs));
    }

    @Test
    void updateTest(){
        String expectedName = "morpheus",
                expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON)
                .body("{" +
                        "    \"name\": \"morpheus\"," +
                        "    \"job\": \"zion resident\"" +
                        "}")
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is(expectedName))
                .body("job", is(expectedJob));
    }
}
