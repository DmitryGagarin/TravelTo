package com.travel.to.travel_to;

import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.form.user.UserSignInForm;
import com.travel.to.travel_to.form.user.UserSignUpFirstForm;
import com.travel.to.travel_to.service.AuthenticationService;
import com.travel.to.travel_to.service.RoleService;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminUserCreation implements CommandLineRunner {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final RoleService roleService;

    @Autowired
    public AdminUserCreation(
        UserService userService,
        AuthenticationService authenticationService,
        RoleService roleService
    ) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        if (userService.findByEmail("admin@travel.com").isEmpty()) {
            UserSignUpFirstForm userSignUpFirstForm = new UserSignUpFirstForm();
            userSignUpFirstForm
                .setEmail("admin@travel.com")
                .setPassword("password");
            userService.registration(userSignUpFirstForm);

            User admin = userService.findByEmail("admin@travel.com").get();
            admin
                .setRoles(roleService.getAllRoles())
                .setName("ADMIN")
                .setVerified(true);
            userService.save(admin);

            UserSignInForm userSignInForm = new UserSignInForm();
            userSignInForm
                .setEmail("admin@travel.com")
                .setPassword("password");
            authenticationService.login(userSignInForm);
        }
    }
}
