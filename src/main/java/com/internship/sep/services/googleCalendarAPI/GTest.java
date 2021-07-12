package com.internship.sep.services.googleCalendarAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GTest {

    private final GEventServiceImpl gService;

    @Autowired
    public GTest(GEventServiceImpl gService) {
        this.gService = gService;
    }

    public void test() throws IOException, GeneralSecurityException {

        gService.test();

    }
}
