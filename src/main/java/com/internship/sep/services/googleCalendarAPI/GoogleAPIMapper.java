package com.internship.sep.services.googleCalendarAPI;


public interface GoogleAPIMapper<E, G> {
    E map(G sepEntity);
    G unmap(E googleEntity);
}
