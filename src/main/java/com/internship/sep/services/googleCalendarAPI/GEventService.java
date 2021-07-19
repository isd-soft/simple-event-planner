package com.internship.sep.services.googleCalendarAPI;
import com.internship.sep.models.Event;
import java.io.IOException;
import java.security.GeneralSecurityException;


public interface GEventService {

    void createEvent(Event event) throws GeneralSecurityException, IOException;
    void updateEvent(Event event) throws GeneralSecurityException, IOException;
    void deleteEvent(String eventId) throws GeneralSecurityException, IOException;
    void updateAttendeesStatus(Event event) throws GeneralSecurityException, IOException;
}
