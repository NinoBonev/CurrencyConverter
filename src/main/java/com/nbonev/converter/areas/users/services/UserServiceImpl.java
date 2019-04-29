package com.nbonev.converter.areas.users.services;

import com.nbonev.converter.areas.roles.entities.Role;
import com.nbonev.converter.areas.roles.enums.RoleEnum;
import com.nbonev.converter.areas.roles.repositories.RoleRepository;
import com.nbonev.converter.areas.users.entities.User;
import com.nbonev.converter.areas.users.models.binding.UserRegisterBindingModel;
import com.nbonev.converter.areas.users.models.view.UserViewModel;
import com.nbonev.converter.areas.users.repositories.UserRepository;
import com.nbonev.converter.areas.users.util.Constants;
import com.nbonev.converter.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getByUsername(final String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(final UserRegisterBindingModel userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAuthorities(Stream.of(roleRepository.findByRole(RoleEnum.ADMIN.getRoleName())).collect(Collectors.toSet()));
        this.userRepository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(Constants.INCORRECT_USERNAME_OR_PASSWORD);
        }

        return user;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return userOptional.get();
    }

    @Override
    @Async
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

}
