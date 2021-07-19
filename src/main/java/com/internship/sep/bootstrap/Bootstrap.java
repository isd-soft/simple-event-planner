package com.internship.sep.bootstrap;

import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;
import com.internship.sep.models.Role;
import com.internship.sep.models.Status;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.security.jwt.JwtTokenUtil;
import com.internship.sep.services.*;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;
    private final EventService eventService;
    private final AttendeeService attendeeService;

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadEvents();
    }

    private void loadUsers() {
        UserDTO testUser1 = new UserDTO();
        testUser1.setEmail("user1@email.com");
        testUser1.setPassword(passwordEncoder.encode("password"));
        testUser1.setFirstName("user1");
        testUser1.setLastName("user1");
        testUser1.setAge(100);
        testUser1.setPhoneNumber("068111111");

        userService.addUser(testUser1);
        System.out.println("This is token for USER!: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser1.getEmail())));


        UserDTO testUser2 = new UserDTO();
        testUser2.setEmail("user2@email.com");
        testUser2.setPassword(passwordEncoder.encode("password"));
        testUser2.setFirstName("user2");
        testUser2.setLastName("user2");
        testUser2.setAge(101);
        testUser2.setRole(Role.USER);
        testUser2.setPhoneNumber("078142536");

        userService.addUser(testUser2);
        System.out.println("This is token for USER2: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser2.getEmail())));

        UserDTO testAdmin = new UserDTO();
        testAdmin.setEmail("admin@email.com");
        testAdmin.setPassword(passwordEncoder.encode("password"));
        testAdmin.setFirstName("admin");
        testAdmin.setLastName("admin");
        testAdmin.setAge(102);
        testAdmin.setRole(Role.ADMIN);
        testAdmin.setPhoneNumber("069654321");

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
        testUser3.setPhoneNumber("079123456");

        userService.addUser(testUser3);
        System.out.println("This is token for USER3: " +
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(testUser3.getEmail())));

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(2);

        EventDTO event1 = new EventDTO();
        EventDTO event2 = new EventDTO();
        EventDTO event3 = new EventDTO();
        EventDTO event4 = new EventDTO();
        EventDTO event5 = new EventDTO();

        event1.setName("ISD Party");
        event1.setLocation("Moldova");
        event1.setIsApproved(true);
        event1.setStartDateTime(today);
        event1.setEndDateTime(tomorrow);
        event1.setDescription("Something...");
        event1.setHost(testUser3);

        event2.setName("boring party");
        event2.setLocation("Caushani");
        event2.setIsApproved(true);
        event2.setStartDateTime(today);
        event2.setEndDateTime(tomorrow);
        event2.setDescription("Something...");
        event2.setHost(testUser3);

        event3.setName("Party hard in Orhei");
        event3.setLocation("Orhei");
        event3.setIsApproved(true);
        event3.setStartDateTime(today);
        event3.setEndDateTime(tomorrow);
        event3.setDescription("Something...Interesting");
        event3.setHost(testUser3);

        event4.setName("Party hard in Monaco");
        event4.setLocation("Monaco");
        event4.setIsApproved(true);
        event4.setStartDateTime(today);
        event4.setEndDateTime(tomorrow);
        event4.setDescription("Something...Interesting");
        event4.setHost(testUser3);

        event5.setName("Halloween");
        event5.setLocation("New York");
        event5.setIsApproved(true);
        event5.setStartDateTime(today);
        event5.setEndDateTime(tomorrow);
        event5.setDescription("Something...Interesting");
        event5.setHost(testUser3);

        AttendeeDTO attendee1 = new AttendeeDTO();
        AttendeeDTO attendee2 = new AttendeeDTO();
        AttendeeDTO attendee3 = new AttendeeDTO();

        attendee1.setEmail("attendee1@mail.ru");
        attendee2.setEmail("attendee2@mail.ru");
        attendee3.setEmail("attendee3@mail.ru");

        attendee1.setStatus(Status.ACCEPTED);
        attendee2.setStatus(Status.DECLINED);

        List<AttendeeDTO> attendees = new ArrayList<>();
        attendees.add(attendee1);
        attendees.add(attendee2);
        attendees.add(attendee3);

        event1.setAttendees(attendees);
        event2.setAttendees(attendees);
        event3.setAttendees(attendees);
        event4.setAttendees(attendees);
        event5.setAttendees(attendees);

        attendeeService.addAttendees(attendees);
        eventService.createNewEvent(event1, testUser3.getEmail());
        eventService.createNewEvent(event2, testUser3.getEmail());
        eventService.createNewEvent(event3, testUser3.getEmail());
        eventService.createNewEvent(event4, testUser3.getEmail());
        eventService.createNewEvent(event5, testUser3.getEmail());

    }

}

