package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.DataRandom;
import ru.yandex.steps.Profile;
import static ru.yandex.steps.ConfigConst.*;

public class CourierCreateNegativeTest {
    DataRandom data;
    Courier courier;
    Courier newCourier;
    Profile profile;
    String oldLogin;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        data = new DataRandom();
        courier = new Courier(data.getFirstName(), data.getLogin(), data.getPassword());
        courier.loginCourierFail();

    }

    @After
    public void tearDown() {
        courier.deleteCourier(courier.loginCourier());
    }


    //нельзя создать двух одинаковых курьеров;
    @Test
    @DisplayName("Create two identical couriers")
    @Description ("You can't create two identical couriers")
    public void createCourierDouble() {
        courier.createCourier();
        profile = courier.getProfile();
        courier.createCourierFail(profile);
    }

    //если создать пользователя с логином, который уже есть, возвращается ошибка.
    //запрос возвращает правильный код ответа в случае создания с существующим логином
    @Test
    @DisplayName("Create a courier with a login that already exists")
    @Description("you can't create a courier with a login that already exists,\n" +
            "the request returns the correct response code")
    public void createCourierDoubleLogin() {
        courier.createCourier();
        oldLogin = courier.getLogin();
        DataRandom newData = new DataRandom();
        newCourier = new Courier(newData.getFirstName(), oldLogin, newData.getPassword());
        profile = newCourier.getProfile();
        courier.createCourierFail(profile);

    }

}
