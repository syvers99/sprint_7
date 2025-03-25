package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.DataRandom;
import static ru.yandex.steps.ConfigConst.BASE_URI;

public class CourierCreatePositiveTest {
    DataRandom data;
    Courier courier;


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

    //курьера можно создать;
    //успешный запрос возвращает ok: true;
    //запрос возвращает правильный код ответа в случае успеха
    @Test
    @DisplayName("Courier can be created")
    @Description("a courier can be created;\n" +
            "successful request returns ok: true;\n" +
            "request returns correct response code")
    public void createCourierPositive() {
        courier.createCourier();
    }
    //курьера можно создать без обязательного поля firstName
    @Test
    @DisplayName("Courier without the optional firstName field")
    @Description("a courier can be created without the optional firstName field")
    public void createCourierWithoutFirstName() {
        courier = new Courier(null, data.getLogin(), data.getPassword());
        courier.createCourier();
    }
}


