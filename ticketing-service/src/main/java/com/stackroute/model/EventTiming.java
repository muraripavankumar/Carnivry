package com.stackroute.model;


import lombok.Data;


import java.io.Serializable;
import java.util.Date;

@Data

public class EventTiming implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;

    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;

}
