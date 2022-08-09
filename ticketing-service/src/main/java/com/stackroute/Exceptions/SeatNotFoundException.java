package com.stackroute.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Seats are filled")
public class SeatNotFoundException extends Throwable {
}
