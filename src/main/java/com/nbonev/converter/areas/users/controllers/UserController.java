package com.nbonev.converter.areas.users.controllers;


import com.nbonev.converter.areas.roles.entities.Role;
import com.nbonev.converter.areas.roles.enums.RoleEnum;
import com.nbonev.converter.areas.users.models.binding.UserLoginBindingModel;
import com.nbonev.converter.areas.users.models.binding.UserRegisterBindingModel;
import com.nbonev.converter.areas.users.services.UserService;
import com.nbonev.converter.areas.users.util.Constants;
import com.nbonev.converter.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/register")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView register(@ModelAttribute("userRegisterModel") UserRegisterBindingModel userRegisterBindingModel) {
        return super.view("users/register",
                "userRegisterModel", userRegisterBindingModel);
    }


    @PostMapping(path = "/register")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView register(@Valid @ModelAttribute("userRegisterModel") UserRegisterBindingModel userRegisterBindingModel,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.register(userRegisterBindingModel);
        }

        this.userService.saveUser(userRegisterBindingModel);
        return super.redirect("/currencies/all");
    }

    @GetMapping(path = "/login")
    @PreAuthorize("!isAuthenticated()")
    public ModelAndView login(@ModelAttribute("userLoginModel") UserLoginBindingModel userLoginBindingModel,
                              @RequestParam(required = false) String error, BindingResult bindingResult) {
        if (error != null) {
            bindingResult.rejectValue("username", "error.user", Constants.INCORRECT_USERNAME_OR_PASSWORD);
            bindingResult.rejectValue("password", "error.user", Constants.INCORRECT_USERNAME_OR_PASSWORD);
        }
        return super.view("users/login", "userLoginModel", userLoginBindingModel);
    }

    @GetMapping(path = "/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return super.redirect("/");
    }
}
