package ru.yandex;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.Creds;
import ru.yandex.steps.Profile;


import static org.junit.Assert.assertNotNull;


public class CourierLoginPositiveTest {
    Profile profile;
    Courier courier;
    Creds creds;


    @Before
    public void setUp() {
        profile = new Profile();
        courier = new Courier(profile.getFirstName(), profile.getLogin(), profile.getPassword());
        courier.loginCourierFail();
        creds = courier.getCreds();

    }

    @After
    public void tearDown() {
        courier.deleteCourier(courier.loginCourier());
    }



    //курьер может авторизоваться;
    //успешный запрос возвращает id.
    @Test
    @DisplayName("Courier can log in")
    @Description("a courier can log in;\n" +
            "a successful request returns an id.")
    public void loginCourierThereIsId() {
        courier.createCourier();
        assertNotNull(courier.loginCourier());

    }
}
