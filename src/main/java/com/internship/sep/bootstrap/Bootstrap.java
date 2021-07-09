package com.internship.sep.bootstrap;

import com.internship.sep.models.Role;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.security.jwt.JwtTokenUtil;
import com.internship.sep.services.AttendeeService;
import com.internship.sep.services.EventService;
import com.internship.sep.services.UserService;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;
    private final EventService eventService;
    private final AttendeeRepository attendeeRepository;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void run(String... args) throws Exception {
       loadUsers();

    }

    private void loadUsers() {
        UserDTO testUser1 = new UserDTO();
        testUser1.setEmail("user1@email.com");
        testUser1.setPassword("password");
        testUser1.setFirstName("user1");
        testUser1.setLastName("user1");
        testUser1.setAge(100);
        testUser1.setRole(Role.USER);
        testUser1.setPhoneNumber("+111222333444");

        userService.addUser(testUser1);
        System.out.println("This is token for USER!: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser1.getEmail())));


        UserDTO testUser2 = new UserDTO();
        testUser2.setEmail("user2@email.com");
        testUser2.setPassword("password");
        testUser2.setFirstName("user2");
        testUser2.setLastName("user2");
        testUser2.setAge(101);
        testUser2.setRole(Role.USER);
        testUser2.setPhoneNumber("+222333444");

        userService.addUser(testUser2);
        System.out.println("This is token for USER2: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser2.getEmail())));

        UserDTO testAdmin = new UserDTO();
        testAdmin.setEmail("admin@email.com");
        testAdmin.setPassword("password");
        testAdmin.setFirstName("admin");
        testAdmin.setLastName("admin");
        testAdmin.setAge(102);
        testAdmin.setRole(Role.ADMIN);
        testAdmin.setPhoneNumber("+333444");

        userService.addUser(testAdmin);
        System.out.println("This is token for ADMIN: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser2.getEmail())));


    }

    private void loadEvents() {

        UserDTO testUser3 = new UserDTO();
        testUser3.setEmail("user3@email.com");
        testUser3.setPassword("password");
        testUser3.setFirstName("user3");
        testUser3.setLastName("user3");
        testUser3.setAge(100);
        testUser3.setRole(Role.USER);
        testUser3.setPhoneNumber("333444");

        userService.addUser(testUser3);
        System.out.println("This is token for USER3: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser3.getEmail())));

        EventDTO event1 = new EventDTO();
        AttendeeDTO attendee1 = new AttendeeDTO();
        AttendeeDTO attendee2 = new AttendeeDTO();
        List<AttendeeDTO> attendeeList1 = new ArrayList<>();
        attendeeList1.add(attendee1);
        attendeeList1.add(attendee2);

        attendee1.setEmail("user1@email.com");
        attendee1.setEvent(event1);

        attendee2.setEmail("user2@email.com");
        attendee2.setEvent(event1);

        event1.setName("Event1");
        event1.setLocation("Chisinau");
        event1.setIsApproved(true);
        event1.setStartDateTime(LocalDateTime.now());
        event1.setEndDateTime(LocalDateTime.now());
        event1.setDescription("event1");
        event1.setCategory("corporate party");

        event1.setAttendees(attendeeList1);
        event1.setHost(testUser3);

    }


}
