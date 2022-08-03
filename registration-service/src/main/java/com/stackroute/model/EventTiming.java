package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTiming {


    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;

}
