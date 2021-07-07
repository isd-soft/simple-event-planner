package com.internship.sep.repositories;

import com.internship.sep.models.Attendee;

import com.internship.sep.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee,Long> {
    Optional<Attendee> findByEmail(String email);
}
