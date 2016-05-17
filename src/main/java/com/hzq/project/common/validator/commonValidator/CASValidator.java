package com.hzq.project.common.validator.commonValidator;

import com.hzq.project.common.redis.RedisHelper;
import com.hzq.project.common.validator.exception.BusyOperationException;

/**
 * Created by hzq on 16/5/13.
 */
public class CASValidator implements Validator {

    private final String business;
    private final String actionName;
    private final String errorMsg;
    private final Integer time;


    public CASValidator(String bussiness, String actionName, String errorMsg) {
        this(bussiness, actionName, errorMsg, 30);
    }

    public CASValidator(String bussiness, String actionName, String errorMsg, Integer time) {
        this.business = bussiness;
        this.actionName = actionName;
        this.errorMsg = errorMsg;
        this.time = time;
    }


    @Override
    public void valid() {
        if (RedisHelper.compareAndSetRequest(business, actionName, "", time)) {
            throw new BusyOperationException(errorMsg);
        }
    }
}
