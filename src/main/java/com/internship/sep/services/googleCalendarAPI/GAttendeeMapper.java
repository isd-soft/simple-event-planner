package com.internship.sep.services.googleCalendarAPI;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GAttendeeMapper implements GoogleAPIMapper<EventAttendee, Attendee> {
    @Override
    public EventAttendee map(Attendee entity) {
        log.warn("Attendee map to google attendee doesn't work, don't use it!");
        return new EventAttendee().setEmail(entity.getEmail());
    }

    @Override
    public Attendee unmap(EventAttendee googleEntity) {
        Attendee attendee = new Attendee();
        attendee.setEmail(googleEntity.getEmail());
        String responseStatus = googleEntity.getResponseStatus();

        if(responseStatus.equalsIgnoreCase("accepted")) {
            attendee.setStatus(Status.ACCEPTED);
        } else if (responseStatus.equalsIgnoreCase("declined")) {
            attendee.setStatus(Status.DECLINED);
        } else {
            attendee.setStatus(Status.PENDING);
        }

        return attendee;
    }
}
