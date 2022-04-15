package org.citizenGuesser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
public class DateService {
    @Autowired
    private Clock clock;

    public Instant now() {
        return Instant.now(clock);
    }
}
