package com.nbonev.converter.areas.users.services;


import com.nbonev.converter.areas.users.entities.User;
import com.nbonev.converter.areas.users.models.binding.UserRegisterBindingModel;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getByUsername(String username);

    User getUserById(Long id);

    void saveUser(UserRegisterBindingModel userDTO);

    void deleteUser(Long id);

}
