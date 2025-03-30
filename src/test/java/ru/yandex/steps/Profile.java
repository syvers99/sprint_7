package ru.yandex.steps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@Getter
@Setter
public class Profile {
    private String firstName;
    private String login;
    private String password;

    public Profile() {
        password = UUID.randomUUID().toString();
        login = UUID.randomUUID().toString();
        firstName = UUID.randomUUID().toString();
    }

}

