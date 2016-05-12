package com.hzq.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by hzq on 16/5/13.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusyOperationException extends RuntimeException {
    public BusyOperationException(String msg) {
        super(msg);
    }
}