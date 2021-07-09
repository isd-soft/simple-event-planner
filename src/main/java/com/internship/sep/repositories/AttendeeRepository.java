package com.internship.sep.repositories;

import com.internship.sep.models.Attendee;

import com.internship.sep.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee,Long> {

    @Query(value = "select user from User user where user.email = :email")
    Optional<User> findByAttendeeEmail(String email);

    Optional<Attendee> findByEmail(String email);
}
