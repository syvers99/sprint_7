package ru.yandex.steps;


public class Profile {
    private String login;
    private String password;
    private String firstName;

    public Profile(String firstName,String login,String password) {
        this.firstName = firstName;
        this.password = password;
        this.login = login;
    }



    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

