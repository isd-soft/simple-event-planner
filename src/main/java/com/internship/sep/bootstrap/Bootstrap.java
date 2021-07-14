package com.internship.sep.bootstrap;
import com.internship.sep.models.Event;
import com.internship.sep.models.Role;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.security.jwt.JwtTokenUtil;
import com.internship.sep.services.EventService;
import com.internship.sep.services.ResourceNotFoundException;
import com.internship.sep.services.UserService;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EventService eventService;
    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;

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
//        testUser1.setRole(Role.USER);
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

        EventDTO event1 = new EventDTO();

        event1.setName("ISD Party");
        event1.setLocation("Moldova");
        event1.setIsApproved(true);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(2);

        event1.setStartDateTime(tomorrow);
        event1.setEndDateTime(tomorrow);
        event1.setDescription("Something...");

        event1.setHost(testUser3);

        eventService.createNewEvent(event1,testUser3.getEmail());

  //      Event newEvent = eventRepository.findByName("ISD Party").orElseThrow(ResourceNotFoundException::new);
   //     eventService.deleteEventById(newEvent.getId());

    }

}

