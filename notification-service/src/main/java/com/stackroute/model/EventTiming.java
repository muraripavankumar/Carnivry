package com.stackroute.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventTiming {

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

}
