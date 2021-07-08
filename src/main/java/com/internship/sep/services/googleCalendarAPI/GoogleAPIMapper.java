package com.internship.sep.services.googleCalendarAPI;

import java.util.List;
import java.util.stream.Collectors;

public interface GoogleAPIMapper<E, G> {
    E map(G sepEntity);
    G unmap(E googleEntity);

}
