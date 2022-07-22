package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTiming {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;

}
