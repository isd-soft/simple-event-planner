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
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequestInitializer;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.internship.sep.SepApplication;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.User;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
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

    public GEventServiceImpl(@Value("${sep.google.calendar.id}")String calendarId,
                             GEventMapper eventMapper) throws GeneralSecurityException, IOException {
        this.calendarId = calendarId;
        this.eventMapper = eventMapper;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Creates an authorized Credential object.
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

        event = service.events().insert(calendarId, event).execute();
        sepEvent.setGoogleEventId(event.getId());
        log.info(event.toPrettyString());
    }

    public void deleteEvent(String eventId) throws GeneralSecurityException, IOException {

        service.events().delete(calendarId, eventId).execute();
        log.info("Google Event Deleted");
    }

    @Override
    public void updateEvent(com.internship.sep.models.Event sepEvent) throws GeneralSecurityException, IOException {
        Event event = eventMapper.map(sepEvent);

        event = service.events().update(calendarId, sepEvent.getGoogleEventId(), event).execute();
        sepEvent.setGoogleEventId(event.getId());
        log.info("Google Event Updated " + event.getHtmlLink());
    }

    @Override
    public List<Attendee> getAttendees(com.internship.sep.models.Event event) throws GeneralSecurityException, IOException {
        return null;
    }
}