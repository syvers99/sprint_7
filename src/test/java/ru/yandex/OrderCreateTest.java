package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.steps.Order;


import static ru.yandex.steps.ConfigConst.*;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    String[] color;
    Order order;

    public OrderCreateTest(String[] color){
        this.color = color;
    }


    // Тестовые данные
    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][]{
                {GRAY},
                {BLACK},
                {BLACK_GRAY},
                {COLOR_EMPTY},
                {COLOR_NULL},
        };
    }
    //можно указать один из цветов — BLACK или GREY;
    //можно указать оба цвета;
    //можно совсем не указывать цвет;
    //тело ответа содержит track

    @Test
    @DisplayName("you can specify the colors(parametrized)")
    @Description("1.you can specify one of the colors - BLACK or GREY;\n" +
            "2.you can specify both colors;\n" +
            "3.you can not specify a color at all;\n" +
            "4.the response body contains track")
    public void createOrderPositiveTest() {
        // задать цвет
        ORDER.setColor(color);
        order = new Order(ORDER);
        // сoздать  заказ
        String orderTrack = order.createOrder();
        // получить созданный заказ по треку
        order.getOrder(orderTrack);
        // отменить созданный заказ
        order.cancelOrder(orderTrack);

    }
}