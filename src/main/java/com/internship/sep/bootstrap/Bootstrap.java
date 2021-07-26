package com.internship.sep.bootstrap;
import com.internship.sep.models.Role;
import com.internship.sep.services.EventCategoryService;
import com.internship.sep.services.UserService;
import com.internship.sep.web.EventCategoryDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EventCategoryService eventCategoryService;

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadEvents();
    }

    private void loadUsers() {

        UserDTO testAdmin = new UserDTO();
        testAdmin.setEmail("admin@email.com");
        testAdmin.setPassword(passwordEncoder.encode("password"));
        testAdmin.setFirstName("admin");
        testAdmin.setLastName("admin");
        testAdmin.setAge(69);
        testAdmin.setRole(Role.ADMIN);
        testAdmin.setPhoneNumber("069696969");

        userService.addUser(testAdmin);
    }

    private void loadEvents() {

        EventCategoryDTO eventCategoryDTO = new EventCategoryDTO();
        EventCategoryDTO eventCategoryDTO1 = new EventCategoryDTO();
        EventCategoryDTO eventCategoryDTO2 = new EventCategoryDTO();

        eventCategoryDTO.setName("Meeting");
        eventCategoryDTO1.setName("Conference");
        eventCategoryDTO2.setName("Party");

        eventCategoryService.addCategory(eventCategoryDTO);
        eventCategoryService.addCategory(eventCategoryDTO1);
        eventCategoryService.addCategory(eventCategoryDTO2);
    }
}