package ru.yandex.steps;


import io.qameta.allure.Step;
import java.net.HttpURLConnection;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static ru.yandex.steps.ConfigConst.*;

public class Courier {
    private String firstName;
    private String login;
    private String password;
    private Profile profile;
    private Creds creds;

    public Courier(String firstName, String login, String password) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        profile = new Profile(firstName, login, password);
        creds = new Creds(login, password);

    }

    public Profile getProfile() {
        return profile;
    }

    public String getLogin() {
        return login;
    }

    public Creds getCreds() {
        return creds;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Step("create a courier")
    public void createCourier() {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(profile)
                .when()
                .post(COURIER_PATH)
                .then().statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("ok", is(TRUE));

    }

    @Step("login a courier")
    public String loginCourier() {
        String courierId = "fail";
        try {
            courierId = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(creds)
                    .log().all()
                    .when()
                    .post(LOGIN_PATH)
                    .then().log().all()//.statusCode(200).
                    //.assertThat().body("id", notNullValue())
                    .extract()
                    .path("id").toString();
            return courierId;
        } catch (NullPointerException e) {
            return courierId;
        }
    }

    @Step("delete a courier")
    public void deleteCourier(String courierId) {


        given()
                .when()
                .log().all()
                .delete(COURIER_PATH + "/" + courierId)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok", notNullValue());

    }

    @Step("login a courier (failed) ")
    public void loginCourierFail() {

        given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then().statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .assertThat().body("message", is(MESSAGE_LOGIN));


    }

    @Step("login a courier with bad creds ")
    public void loginCourierBadCreds(Creds creds) {

        given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then().statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", is("Недостаточно данных для входа"));

    }

    @Step("create a courier (failed) ")
    public void createCourierFail(Profile profile) {
        given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(profile)
                .when()
                .post(COURIER_PATH)
                .then().statusCode(HttpURLConnection.HTTP_CONFLICT)
                .assertThat().body("message", is(MESSAGE_CREATE));
    }

    @Step("create a courier with bad profile)")
    public void createCourierBadRequest(Profile profile) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(profile)
                .log().all()
                .when()
                .post(COURIER_PATH)
                .then().statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", notNullValue());
    }

    @Step("login a courier  with wrong creds")
    public void loginCourierFail(Creds creds) {

        given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then().statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .assertThat().body("message", is(MESSAGE_LOGIN));
    }
}


