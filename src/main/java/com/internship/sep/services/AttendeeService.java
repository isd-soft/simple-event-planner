package com.internship.sep.services;

import com.internship.sep.web.AttendeeDTO;

import java.util.List;

public interface AttendeeService {

    List<AttendeeDTO> getAllAttendees();

    AttendeeDTO getAttendeeByEmail(String email);

}
