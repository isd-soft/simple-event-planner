package com.internship.sep.services.googleCalendarAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.internship.sep.SepApplication;
import com.internship.sep.models.Status;
import com.internship.sep.repositories.AttendeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import java.util.List;

@Slf4j
@Service
public class GEventServiceImpl implements GEventService {

    private static final String APPLICATION_NAME = "SEP";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = List.of(CalendarScopes.CALENDAR, CalendarScopes.CALENDAR_EVENTS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private final String calendarId;
    private final GEventMapper eventMapper;
    private final Calendar service;
    private final AttendeeRepository attendeeRepository;

    public GEventServiceImpl(@Value("${sep.google.calendar.id}") String calendarId,
                             GEventMapper eventMapper, AttendeeRepository attendeeRepository) throws GeneralSecurityException, IOException {
        this.attendeeRepository = attendeeRepository;
        this.calendarId = calendarId;
        this.eventMapper = eventMapper;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SepApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void createEvent(com.internship.sep.models.Event sepEvent) throws GeneralSecurityException, IOException {
        Event event = eventMapper.map(sepEvent);

        event = service.events().insert(calendarId, event).setSupportsAttachments(true).execute();
        sepEvent.setGoogleEventId(event.getId());
        log.info(event.toPrettyString());
    }

    public void deleteEvent(String eventId) throws GeneralSecurityException, IOException {

        if(eventId == null) {
            return;
        }

        service.events().delete(calendarId, eventId).execute();
        log.info("Google Event Deleted");
    }

    @Override
    public void updateEvent(com.internship.sep.models.Event sepEvent) throws GeneralSecurityException, IOException {

        if(sepEvent.getGoogleEventId() == null) {
            return;
        }

        log.info("Start event updating");
        Event event = eventMapper.map(sepEvent);

        Event updatedEvent = service.events().update(calendarId, sepEvent.getGoogleEventId(), event).setSupportsAttachments(true).execute();
        sepEvent.setGoogleEventId(updatedEvent.getId());
        log.info(updatedEvent.toPrettyString());
    }

    @Override
    public void updateAttendeesStatus(com.internship.sep.models.Event event) throws GeneralSecurityException, IOException {

        if(event.getGoogleEventId() == null) {
            return;
        }

        Event gEvent = service.events().get(calendarId, event.getGoogleEventId()).execute();

        List<EventAttendee> gAttendees = gEvent.getAttendees();

        for (int i = 0; i < event.getAttendees().size(); i++) {
            for (int j = 0; j < gAttendees.size(); j++) {
                if (gAttendees.get(j).getEmail().equals(event.getAttendees().get(i).getEmail())) {
                    event.getAttendees().get(i).setStatus(getAttendeeStatus(gAttendees.get(j)));
                    attendeeRepository.save(event.getAttendees().get(i));
                }
            }
        }
    }

    private Status getAttendeeStatus(EventAttendee googleEntity) {
        String responseStatus = googleEntity.getResponseStatus();

        if (responseStatus.equalsIgnoreCase("accepted")) {
            return Status.ACCEPTED;
        } else if (responseStatus.equalsIgnoreCase("declined")) {
            return Status.DECLINED;
        } else {
            return Status.PENDING;
        }
    }
}