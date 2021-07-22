package com.internship.sep.services.googleCalendarAPI;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttachment;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.LinkDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@Component
public class GEventMapper {

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
            gAttendees[i] = new EventAttendee().setEmail(attendees[i].getEmail());
        }

        LinkDB[] attachments = new LinkDB[sepEvent.getLinks().size()];
        sepEvent.getLinks().toArray(attachments);
        EventAttachment[] gAttachments = new EventAttachment[attachments.length];
        for (int i = 0; i < gAttachments.length; i++) {
            gAttachments[i] = new EventAttachment().setFileUrl(attachments[i].getLink());
        }

        gEvent.setStart(start);
        gEvent.setEnd(end);
        gEvent.setAttendees(Arrays.asList(gAttendees));
        gEvent.setAttachments(Arrays.asList(gAttachments));

        return gEvent;
    }
}
