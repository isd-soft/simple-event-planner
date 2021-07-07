package com.internship.sep.repositories;

import com.internship.sep.models.Attendee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee,Long> {
    Optional<Attendee> findByName(String name);
}
