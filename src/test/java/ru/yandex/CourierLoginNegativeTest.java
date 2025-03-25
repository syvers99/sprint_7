package ru.yandex;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.*;
import org.junit.rules.Timeout;
import ru.yandex.steps.Courier;
import ru.yandex.steps.Creds;
import ru.yandex.steps.DataRandom;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static ru.yandex.steps.ConfigConst.*;


public class CourierLoginNegativeTest {
    DataRandom data;
    Courier courier;
    Creds creds;
    RestAssuredConfig newConfig;


    @Rule
    public Timeout globalTimeout = Timeout.seconds(15);


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        data = new DataRandom();
        courier = new Courier(data.getFirstName(), data.getLogin(), data.getPassword());
    }

    @After
    public void tearDown() {
       String id = courier.loginCourier();
        if (!id.equals("fail")) {
            courier.deleteCourier(id);
           }
    }


    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Log in under a non-existent user")
    @Description("if you log in under a non-existent user, the request returns an error")
    public void loginCourierNegative() {
        courier.loginCourierFail();
    }
    //для авторизации нужно передать все обязательные поля;
    //если поля login нет, запрос возвращает ошибку;

    @Test()
    @DisplayName("There is no login field")
    @Description("for successful login , you must pass all required fields;\n" +
            "if there is no login field, the request returns an error;")
    public void loginCourierWithoutLogin() {
        courier.createCourier();
        creds = new Creds(NULL,courier.getPassword());
        courier.loginCourierBadCreds(creds);


    }
    //если поля password нет, запрос вылетает по таймауту (по причине недоступности сервера)
    @Test
    @DisplayName("There is no password field")
    @Description("if there is no password field, the request will time out (due to server unavailable)")
    public void loginCourierWithoutPassword () {
        newConfig = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout",4000).
                setParam("http.socket.timeout",4000).
                setParam("http.connection-manager.timeout",4000));
        courier.createCourier();
        creds = new Creds(courier.getLogin(),NULL);

            Assert.assertThrows(Exception.class,() -> given()
                    .config(newConfig)
                    .header("Content-type", "application/json")
                    .and()
                    .body(creds)
                    .log().all()
                    .when()
                    .post(LOGIN_PATH)
                    .then().statusCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT));
    }

    //система вернёт ошибку, если неправильно указать неверный логин
    @Test()
    @DisplayName("Wrong login")
    @Description("the service will return an error if you enter an incorrect login")
    public void loginCourierWithWrongLogin() {
        courier.createCourier();
        data = new DataRandom();
        creds = new Creds(data.getLogin(), courier.getPassword());
        courier.loginCourierFail(creds);
    }

    //система вернёт ошибку, если неправильно указать неверный пароль
    @Test()
    @DisplayName("Wrong password")
    @Description("the service will return an error if you enter an incorrect password")
    public void loginCourierWithWrongPassword() {
        courier.createCourier();
        data = new DataRandom();
        creds = new Creds(courier.getLogin(), data.getPassword());
        courier.loginCourierFail(creds);
    }

}


