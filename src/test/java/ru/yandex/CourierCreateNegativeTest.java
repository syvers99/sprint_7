package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.*;


public class CourierCreateNegativeTest {
    Courier courier;
    Courier newCourier;
    Profile profile;
    String oldLogin;

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


    //нельзя создать двух одинаковых курьеров;
    @Test
    @DisplayName("Create two identical couriers")
    @Description ("You can't create two identical couriers")
    public void createCourierDouble() {
        courier.createCourier();
        courier.createCourierFail(courier.getProfile());
    }

    //Если создать пользователя с логином, который уже есть, возвращается ошибка.
    //Запрос возвращает правильный код ответа в случае создания с существующим логином
    @Test
    @DisplayName("Create a courier with a login that already exists")
    @Description("You can't create a courier with a login that already exists,\n" +
            "the request returns the correct response code")
    public void createCourierDoubleLogin() {
        courier.createCourier();
        oldLogin = courier.getLogin();
        profile = new Profile();
        newCourier = new Courier(profile.getFirstName(), oldLogin, profile.getPassword());
        courier.createCourierFail(newCourier.getProfile());

    }

}
