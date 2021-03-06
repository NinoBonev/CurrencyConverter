package com.nbonev.converter.areas.users.models.binding;

import com.nbonev.converter.areas.roles.entities.Role;
import com.nbonev.converter.areas.users.util.Constants;
import com.nbonev.converter.areas.users.validation.UsernameUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by Nino Bonev - 27.4.2019 г., 10:37
 */
public class UserRegisterBindingModel {

    @NotNull
    @NotEmpty(message = Constants.USERNAME_NOT_EMPTY)
    @Size(min = 4, max = 30, message = Constants.USERNAME_LENGTH)
    @UsernameUnique
    private String username;

    @NotNull
    @NotEmpty(message = Constants.PASSWORD_NOT_EMPTY)
    @Size(min = 4, max = 14, message = Constants.PASSWORD_LENGTH)
    private String password;

    private Set<Role> authorities;

    public UserRegisterBindingModel() {
    }

    public UserRegisterBindingModel(String username, String password) {
        this.username = username;
        this.password = password;
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

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
