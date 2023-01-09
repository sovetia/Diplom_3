package api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UsersApi {

    private String email, password, name;

    public UsersApi() {

    }

    public UsersApi(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Create user. Send POST request to /api/auth/register")
    public ValidatableResponse registerUserResponse(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(body)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/register")
                .then();
    }

    @Step("Login user. Send POST request to /api/auth/login")
    public ValidatableResponse loginUserResponse(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(body)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/login")
                .then();
    }

    @Step("Delete user. Send DELETE request to /api/auth/user")
    public void deleteUser(String accessToken) {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .when()
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then()
                .assertThat().statusCode(202);
    }
}
