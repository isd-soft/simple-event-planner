package com.internship.sep.bootstrap;

import com.internship.sep.models.Event;
import com.internship.sep.models.Role;
import com.internship.sep.models.User;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;

public class Bootstrap implements CommandLineRunner {

    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Bootstrap(AttendeeRepository attendeeRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.attendeeRepository = attendeeRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private void loadUsers (){
        User testUser1 = new User();
        testUser1.setFirstName("user1");
        testUser1.setRole(Role.USER);
        testUser1.setEmail("user1@email.com");

        userRepository.save(testUser1);

        User testUser2 = new User();
        testUser2.setFirstName("admin");
        testUser2.setRole(Role.ADMIN);
        testUser2.setEmail("user2@email.com");

        userRepository.save(testUser2);

    }

    private void loadEvents(){

        Event event1 = new Event();
        event1.setHost(userRepository.getById(1l));

    }


}
