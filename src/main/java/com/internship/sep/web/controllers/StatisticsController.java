package com.internship.sep.web.controllers;

import com.internship.sep.services.StatisticsService;
import com.internship.sep.web.StatisticsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public StatisticsDTO getStatistics() {
        return statisticsService.getStatistics();
    }

}
