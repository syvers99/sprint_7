package ru.yandex;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.Creds;
import ru.yandex.steps.DataRandom;



import static org.junit.Assert.assertNotNull;
import static ru.yandex.steps.ConfigConst.BASE_URI;


public class CourierLoginPositiveTest {
    DataRandom data;
    Courier courier;
    Creds creds;


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        data = new DataRandom();
        courier = new Courier(data.getFirstName(), data.getLogin(), data.getPassword());
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
