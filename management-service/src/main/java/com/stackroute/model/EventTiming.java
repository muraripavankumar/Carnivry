package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTiming {
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;

}
