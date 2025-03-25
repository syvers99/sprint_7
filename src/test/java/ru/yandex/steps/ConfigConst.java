package ru.yandex.steps;
import java.io.File;
public class ConfigConst {
    public static final Order ORDER_GRAY = new Order(new File("src/main/resources/orderGrey.json"));
    public static final Order ORDER_BLACK_GRAY = new Order(new File("src/main/resources/orderBlackGrey.json"));
    public static final Order ORDER_BLACK= new Order(new File("src/main/resources/orderBlack.json"));
    public static final Order ORDER_EMPTY = new Order(new File("src/main/resources/orderEmptyColor.json"));
    public static final Order ORDER_NULL = new Order(new File("src/main/resources/orderNullColor.json"));
    static DataRandom data = new DataRandom();
    public static final Courier PASSWORD_NULL = new Courier(data.getFirstName(), data.getLogin(), null);
    public static final Courier LOGIN_NULL = new Courier(data.getFirstName(), null, data.getPassword());
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String MESSAGE_CREATE = "Этот логин уже используется. Попробуйте другой.";
    public static final String MESSAGE_LOGIN = "Учетная запись не найдена";
    public static final String COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_PATH = "/api/v1/courier/login";
    public static final String ORDERS_PATH = "/api/v1/orders";
    public static final String TRACK_PATH = "/api/v1/orders/track";
    public static final String CANCEL_PATH = "/api/v1/orders/cancel";
    public static final String FINISH_PATH = "/api/v1/orders/finish";
    public static final boolean TRUE = true;
    public static final String NULL = null;
    public static final int PAGE = 0;
    public static final int LIMIT = 10;
    public static final String NEAREST_STATION = "[\"1\",\"26\"]";

}
