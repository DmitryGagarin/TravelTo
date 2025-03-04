package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignupForm;
import com.travel.to.travel_to.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserSignupForm userSignupForm) {
        User user = new User();
        user.setUsername(userSignupForm.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userSignupForm.getPassword()));
        user.setEmail(userSignupForm.getEmail());
        return userRepository.save(user);
    }

}
