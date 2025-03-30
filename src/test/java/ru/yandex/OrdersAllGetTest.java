package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.steps.Courier;
import ru.yandex.steps.Order;
import ru.yandex.steps.Profile;

import static ru.yandex.steps.ConfigConst.*;
public class OrdersAllGetTest {
    Order order;
    Courier courier;
    int orderId;
    String track;
    String courierId;
    Profile profile;


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        order = new Order(ORDER);
        profile = new Profile();
        courier = new Courier(profile.getFirstName(), profile.getLogin(), profile.getPassword());

    }
    @After
    public void tearDown() {
        courier.deleteCourier(courierId);
        order.finishOrder(orderId);
    }
    // получить все заказы
    @Test
    @DisplayName("Get all orders")
    @Description("you can receive all orders")
    public void getOrdersAllTest() {

        courier.createCourier();
        courierId = courier.loginCourier();
        // сoздать и принять  заказ
        track = order.createOrder();
        orderId = order.getOrder(track);
        order.acceptOrder(orderId, courierId);
        order.getAllOrders();
    }
    // получить заказы конкретного курьера возле определенного метро
    @Test
    @DisplayName("Get  a courier's orders")
    @Description("you can receive all orders from a specific courier")
    public void getOrdersCourierTest()
    {

        courier.createCourier();
        courierId = courier.loginCourier();
        // сoздать и принять  заказ
        track = order.createOrder();
        orderId = order.getOrder(track);
        order.acceptOrder(orderId, courierId);
        order.getCourierOrders(courierId);
    }


}
