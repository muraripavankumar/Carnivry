package com.stackroute.event;

import com.stackroute.entity.CarnivryUser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Slf4j
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final CarnivryUser carnivryUser;
    private final String applicationUrl;

    public RegistrationCompleteEvent(CarnivryUser user, String applicationUrl) {
        super(user);
        this.carnivryUser = user;
        this.applicationUrl = applicationUrl;
        log.debug("Registration Email verification Event created");
    }
}

