package ru.yandex.steps;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.steps.ConfigConst.*;
@AllArgsConstructor
public class Order extends Client {
    OrderData order;
    @Step("create a order")
    public String createOrder() {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then().statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("track",notNullValue())
                .extract()
                .path("track").toString();
    }
    @Step("get a order")
    public int getOrder(String track){
       return  spec()
                .queryParam("t",track)
                .when()
                .get(ORDERS_PATH + "/track")
                .then().statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("order.id",notNullValue())
                .extract()
                .path("order.id");

    }
    @Step("cancel a order")
    public void cancelOrder(String track){
        spec()
                .queryParam("track", track)
                .when()
                .log().all()
                .put(ORDERS_PATH + "/cancel")
                .then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok",is(true));


    }
    @Step("finish a order")
    public void finishOrder(int orderId){
        spec()
                .when()
                .log().all()
                .put(ORDERS_PATH + "/finish/" + orderId)
                .then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok",is(true));


    }

    @Step("accept a order")
    public void acceptOrder(int orderId, String courierId){
        spec()
                .queryParam("courierId", courierId)
                .log().all()
                .when()
                .put("/api/v1/orders/accept/" + orderId)
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok",is(true));

    }
    @Step("get all orders")
    public void getAllOrders(){
        spec()
                .get(ORDERS_PATH)
                .then().statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("orders",notNullValue());

    }
    @Step("get courier orders")
    public void getCourierOrders(String courierId){
        spec()
                .queryParam("limit",LIMIT)
                .queryParam("page",PAGE)
                .queryParam("courierId",courierId)
                .queryParam("nearestStation",NEAREST_STATION)
                .when()
                .log().all()
                .get(ORDERS_PATH)
                .then().statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("orders",notNullValue())
                .log().all();
    }
}

