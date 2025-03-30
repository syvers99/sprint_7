package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.Profile;


public class CourierCreatePositiveTest {
    Profile profile;
    Courier courier;


    @Before
    public void setUp() {
        profile = new Profile();
        courier = new Courier(profile.getFirstName(), profile.getLogin(), profile.getPassword());
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
        courier = new Courier(null, profile.getLogin(), profile.getPassword());
        courier.createCourier();
    }
}


