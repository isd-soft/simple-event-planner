package com.internship.sep.services.googleCalendarAPI;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.internship.sep.models.Attendee;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class GEventMapper implements GoogleAPIMapper<Event, com.internship.sep.models.Event> {

    private GoogleAPIMapper<EventAttendee, Attendee> attendeeMapper;

    @Override
    public Event map(com.internship.sep.models.Event sepEvent) {
        Event gEvent = new Event();

        gEvent.setId(sepEvent.getId().toString());
        gEvent.setSummary(sepEvent.getName());
        gEvent.setDescription(sepEvent.getDescription());
        gEvent.setLocation(sepEvent.getLocation());

        DateTime startTime = new DateTime(sepEvent.getStartDateTime().toString() + "+03:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startTime);

        DateTime endTime = new DateTime(sepEvent.getEndDateTime().toString() + "+03:00");
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

        sepEvent.setId(Long.valueOf(gEvent.getId()));
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
