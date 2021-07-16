package com.internship.sep.services;

import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.web.StatisticsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public StatisticsDTO getStatistics() {


        List<User> users = userRepository.findAll();
        List<Event> events = eventRepository.findAll();
        List<Event> approvedEvents = eventRepository.findApprovedEvents();
        List<Event> unapprovedEvents = eventRepository.findUnapprovedEvents();

        StatisticsDTO stats = new StatisticsDTO();

        stats.setNumberOfUsers(users.size());
        stats.setTotalEvents(events.size());
        stats.setApprovedEvents(approvedEvents.size());
        stats.setUnapprovedEvents(unapprovedEvents.size());

        return stats;
    }

}
