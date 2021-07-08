package com.internship.sep.services.googleCalendarAPI;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.internship.sep.models.Attendee;

public class GAttendeeMapper implements GoogleAPIMapper<EventAttendee, Attendee> {

    @Override
    public EventAttendee map(Attendee entity) {
        return new EventAttendee()
                .setId(entity.getId().toString())
                .setEmail(entity.getEmail());
    }

    @Override
    public Attendee unmap(EventAttendee googleEntity) {
        Attendee attendee = new Attendee();
        attendee.setEmail(googleEntity.getEmail());
        attendee.setId(Long.valueOf(googleEntity.getId()));
        return attendee;
    }
}
