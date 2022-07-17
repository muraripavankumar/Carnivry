package com.stackroute.event;

import com.stackroute.entity.CarnivryUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final CarnivryUser carnivryUser;
    private final String applicationUrl;

    public RegistrationCompleteEvent(CarnivryUser user, String applicationUrl) {
        super(user);
        this.carnivryUser = user;
        this.applicationUrl = applicationUrl;
    }
}

