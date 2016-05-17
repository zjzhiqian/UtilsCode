package com.hzq.project.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by hzq on 16/4/23.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LockObtainTimeOutException extends RuntimeException {
    public LockObtainTimeOutException(String msg) {
        super(msg);
    }
}
