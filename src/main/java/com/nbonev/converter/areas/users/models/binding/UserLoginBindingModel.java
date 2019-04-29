package com.nbonev.converter.areas.users.models.binding;

import com.nbonev.converter.areas.users.util.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {

    @NotNull
    @NotEmpty(message = Constants.USERNAME_NOT_EMPTY)
    @Size(min = 4, max = 30, message = Constants.USERNAME_LENGTH)
    private String username;

    @NotNull
    @NotEmpty(message = Constants.PASSWORD_NOT_EMPTY)
    @Size(min = 4, max = 14, message = Constants.PASSWORD_LENGTH)
    private String password;

    public UserLoginBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
