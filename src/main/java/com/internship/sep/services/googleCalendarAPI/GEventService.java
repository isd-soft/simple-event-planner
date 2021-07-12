package com.internship.sep.services.googleCalendarAPI;

import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GEventService {

    void createEvent(Event event) throws GeneralSecurityException, IOException;
    void updateEvent(Event event) throws GeneralSecurityException, IOException;
    void deleteEvent(String eventId) throws GeneralSecurityException, IOException;
    List<Attendee> getAttendees(Event event) throws GeneralSecurityException, IOException;
}
