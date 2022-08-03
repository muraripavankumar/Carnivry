package com.stackroute.model;


import lombok.AllArgsConstructor;
import lombok.Data;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class EventTiming implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;

    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;

}
