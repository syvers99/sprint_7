package ru.yandex.steps;

import java.util.UUID;

public class DataRandom {
    private String login;
    private String password;
    private String firstName;



    public DataRandom() {
        password = UUID.randomUUID().toString();
        login = UUID.randomUUID().toString();
        firstName = UUID.randomUUID().toString();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }



    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
