package ru.yandex.steps;

import io.qameta.allure.Step;

import java.io.File;
import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.steps.ConfigConst.*;

public class Order {
    File order;
    public Order(File order) {
        this.order = order;
    }
    @Step("create a order")
    public String createOrder() {
        return given()
                .header("Content-type", "application/json")
                .and()
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
       return  given()
                .queryParam("t",track)
                .when()
                .get(TRACK_PATH)
                .then().statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("order.id",notNullValue())
                .extract()
                .path("order.id");

    }
    @Step("cancel a order")
    public void cancelOrder(String track){
        given()
                .queryParam("track", track)
                .when()
                .log().all()
                .put(CANCEL_PATH)
                .then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok",is(TRUE));


    }
    @Step("finish a order")
    public void finishOrder(int orderId){
        given()
                .when()
                .log().all()
                .put(FINISH_PATH + "/" + orderId)
                .then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok",is(TRUE));


    }

    @Step("accept a order")
    public void acceptOrder(int orderId, String courierId){
        given()
                .queryParam("courierId", courierId)
                .log().all()
                .when()
                .put("/api/v1/orders/accept/" + orderId)
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok",is(TRUE));

    }
    @Step("get all orders")
    public void getAllOrders(){
        given()
                .get(ORDERS_PATH)
                .then().statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("orders",notNullValue());

    }
    @Step("get courier orders")
    public void getCourierOrders(String courierId){
        given()
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

