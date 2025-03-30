package ru.yandex.steps;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
public class ConfigConst {

    public static final Profile profile = new Profile();
    public static final Courier PASSWORD_NULL = new Courier(profile.getFirstName(), profile.getLogin(), null);
    public static final Courier LOGIN_NULL = new Courier(profile.getFirstName(), null, profile.getPassword());
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String MESSAGE_CREATE = "Этот логин уже используется. Попробуйте другой.";
    public static final String MESSAGE_LOGIN = "Учетная запись не найдена";
    public static final String COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_PATH = "/api/v1/courier/login";
    public static final String ORDERS_PATH = "/api/v1/orders";
    public static final String NULL = null;
    public static final String FAILED = "failed";
    public static final int PAGE = 0;
    public static final int LIMIT = 10;
    public static final String NEAREST_STATION = "[\"1\",\"26\"]";
    static BufferedReader br;
    static {
        try {
            br = new BufferedReader(new FileReader("src/main/resources/orderGrey.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static OrderData ORDER = new Gson().fromJson(br, OrderData.class);
    public static final String[] GRAY = {"GRAY"};
    public static final String[] BLACK_GRAY = {"GRAY","BLACK"};
    public static final String[] BLACK = {"BLACK"};
    public static final String[] COLOR_EMPTY = {};
    public static final String[] COLOR_NULL = null;

}

