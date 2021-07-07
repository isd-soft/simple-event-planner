package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Attendee;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.web.AttendeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final Mapper<Attendee,AttendeeDTO> attendeeMapper;

    @Override
    public List<AttendeeDTO> getAllAttendees() {
        return attendeeRepository.findAll()
                .stream()
                .map(attendeeMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public AttendeeDTO getAttendeeByName(String name) {
        return attendeeMapper.map(attendeeRepository.findByName(name).orElseThrow(ResourceNotFoundException::new));

    }
}
