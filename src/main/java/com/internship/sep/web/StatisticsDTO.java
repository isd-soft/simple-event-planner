package com.internship.sep.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StatisticsDTO {
    private Integer totalEvents;
    private Integer approvedEvents;
    private Integer unapprovedEvents;
    private Integer numberOfUsers;
}
