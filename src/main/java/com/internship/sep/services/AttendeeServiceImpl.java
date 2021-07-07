package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Attendee;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;

    @Override
    public List<AttendeeDTO> getAllAttendees() {
        return attendeeRepository.findAll()
                .stream()
                .map(attendeeMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public AttendeeDTO getAttendeeByEmail(String email) {
        return attendeeMapper.map(attendeeRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new));
    }
}
