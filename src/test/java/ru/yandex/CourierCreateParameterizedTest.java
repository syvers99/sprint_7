package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.steps.Courier;
import ru.yandex.steps.Profile;
import static ru.yandex.steps.ConfigConst.*;

@RunWith(Parameterized.class)
public class CourierCreateParameterizedTest {
    Courier courier;
    Profile profile;
    public CourierCreateParameterizedTest (Courier courier){
        this.courier = courier;
    }

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;

    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][]{
                {PASSWORD_NULL},
                {LOGIN_NULL},

        };
    }



    //чтобы создать курьера, нужно передать в ручку все обязательные поля;
    //запрос возвращает правильный код ответа в случае отсутствия одного из обязательных полей
    //если одного из полей нет, запрос возвращает ошибку


    @Test
    @DisplayName("Create a courier, you need to pass all the required fields")
    @Description("to create a courier, you need to pass all the required fields;\n" +
            "if one of the fields is missing, the request returns an error;" +
            " the request returns the correct response code")
    public void createCourierWithoutField() {
        profile = courier.getProfile();
        courier.createCourierBadRequest(profile);
    }
}

