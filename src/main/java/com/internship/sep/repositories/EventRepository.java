package com.internship.sep.repositories;
import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByName(String name);

    @Query("select e from Event e WHERE e.isApproved = false")
    List<Event> findUnapprovedEvents();

    @Query("select e from Event e WHERE e.isApproved = true ")
    List<Event> findApprovedEvents();

    @Query("select e from Event e WHERE e.host = ?1")
    List<Event> findEventsByHost(User host);


}
