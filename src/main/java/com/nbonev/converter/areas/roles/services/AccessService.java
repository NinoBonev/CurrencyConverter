package com.nbonev.converter.areas.roles.services;

import com.nbonev.converter.areas.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    public AccessService() {
    }

    public boolean isInRoleAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"));
    }
}
