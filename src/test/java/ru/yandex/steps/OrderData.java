package ru.yandex.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Getter
@Setter
public class OrderData {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

}
