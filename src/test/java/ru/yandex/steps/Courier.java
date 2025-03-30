package ru.yandex.steps;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import lombok.Setter;

import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.*;
import static ru.yandex.steps.ConfigConst.*;

@Getter
@Setter
public class Courier extends Client {
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


    @Step("create a courier")
    public void createCourier() {
        create(profile)
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("ok", is(true));

    }

    @Step("login a courier")
    public String loginCourier() {
        try {
            return
                    login(creds)
                            .extract()
                            .path("id").toString();
        } catch (NullPointerException e) {
            return FAILED;
        }
    }

    @Step("delete a courier")
    public void deleteCourier(String courierId) {

        spec()
                .when()
                .log().all()
                .delete(COURIER_PATH + "/" + courierId)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok", notNullValue());

    }

    @Step("login a courier (failed) ")
    public void loginCourierFail() {

        login(creds)
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .assertThat().body("message", is(MESSAGE_LOGIN));


    }

    @Step("login a courier with bad creds ")
    public void loginCourierBadCreds(Creds badCreds) {

        login(badCreds)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", is("Недостаточно данных для входа"));

    }

    @Step("create a courier (failed) ")
    public void createCourierFail(Profile profile) {
            create(profile)
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .assertThat().body("message", is(MESSAGE_CREATE));
    }

    @Step("create a courier with bad profile)")
    public void createCourierBadRequest(Profile badProfile) {
                create(badProfile)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", notNullValue());
    }

    @Step("login a courier  with wrong creds")
    public void loginCourierWrong(Creds wrongCreds) {
        login(wrongCreds)
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .assertThat().body("message", is(MESSAGE_LOGIN));
    }

    public ValidatableResponse login(Creds creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }

    public ValidatableResponse create(Profile profile) {
        return spec()
                .body(profile)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }
}


