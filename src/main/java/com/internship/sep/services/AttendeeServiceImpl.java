package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.Status;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.web.AttendeeDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.internship.sep.web.AttendeeDTO;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;

    @Transactional
    @Override
    public List<AttendeeDTO> getAllAttendees() {
        return attendeeRepository.findAll()
                .stream()
                .map(attendeeMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addAttendees(List<AttendeeDTO> attendeeDTO) {

        attendeeRepository.saveAll(attendeeMapper.unmapList(attendeeDTO));
    }

    @Transactional
    @Override
    public AttendeeDTO getAttendeeByEmail(String email) {
        return attendeeMapper.map(attendeeRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new));
    }
}