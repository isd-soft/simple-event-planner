package com.internship.sep.services.googleCalendarAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequestInitializer;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GEventService {

    @Value("${sep.google.calendar.id}")
    String calendarId;

    @Value("${sep.google.calendar.api.key}")
    String apiKey;

    private GEventMapper eventMapper;

    public void createEvent(com.internship.sep.models.Event sepEvent) throws GeneralSecurityException, IOException {
        Event event = eventMapper.map(sepEvent);
        CalendarRequestInitializer initializer = new CalendarRequestInitializer(apiKey);
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, new GsonFactory(), null).build();

        service.events().insert(calendarId, event);
    }

    public void updateEvent() {

    }

    public void deleteEvent(com.internship.sep.models.Event sepEvent) throws GeneralSecurityException, IOException {
        Event event = eventMapper.map(sepEvent);
        CalendarRequestInitializer initializer = new CalendarRequestInitializer(apiKey);
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, new GsonFactory(), null).build();

        service.events().delete(calendarId, event.getId());
    }

}