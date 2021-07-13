package com.internship.sep.services.googleCalendarAPI;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
public class GEventMapper implements Mapper<com.internship.sep.models.Event, Event> {

    private GoogleAPIMapper<EventAttendee, Attendee> attendeeMapper;

    @Override
    public Event map(com.internship.sep.models.Event sepEvent) {
        Event gEvent = new Event();
        gEvent.setSummary(sepEvent.getName());
        gEvent.setDescription(sepEvent.getDescription());
        gEvent.setLocation(sepEvent.getLocation());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime startDateTime = ZonedDateTime.of(sepEvent.getStartDateTime(), ZoneId.of("Europe/Chisinau"));
        DateTime startTime = new DateTime(formatter.format(startDateTime));
        EventDateTime start = new EventDateTime()
                .setDateTime(startTime);

        ZonedDateTime endDateTime = ZonedDateTime.of(sepEvent.getEndDateTime(), ZoneId.of("Europe/Chisinau"));
        DateTime endTime = new DateTime(formatter.format(endDateTime));
        EventDateTime end = new EventDateTime()
                .setDateTime(endTime);

        Attendee[] attendees = new Attendee[sepEvent.getAttendees().size()];
        sepEvent.getAttendees().toArray(attendees);
        EventAttendee[] gAttendees = new EventAttendee[attendees.length];
        for (int i = 0; i < gAttendees.length; i++) {
            gAttendees[i] = attendeeMapper.map(attendees[i]);
        }

        gEvent.setStart(start);
        gEvent.setEnd(end);
        gEvent.setAttendees(Arrays.asList(gAttendees));

        return gEvent;
    }

    @Override
    public com.internship.sep.models.Event unmap(Event gEvent) {
        com.internship.sep.models.Event sepEvent = new com.internship.sep.models.Event();
        sepEvent.setName(gEvent.getSummary());
        sepEvent.setDescription(gEvent.getDescription());
        sepEvent.setLocation(gEvent.getLocation());

        sepEvent.setStartDateTime(convertToLocalDateTime(new Date(gEvent.getStart().getDateTime().getValue())));
        sepEvent.setEndDateTime(convertToLocalDateTime(new Date(gEvent.getEnd().getDateTime().getValue())));

        EventAttendee[] attendees = new EventAttendee[gEvent.getAttendees().size()];
        gEvent.getAttendees().toArray(attendees);
        Attendee[] sepAttendee = new Attendee[attendees.length];
        for (int i = 0; i < attendees.length; i++) {
            sepAttendee[i] = attendeeMapper.unmap(attendees[i]);
        }
        sepEvent.setAttendees(Arrays.asList(sepAttendee));

        return sepEvent;
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
