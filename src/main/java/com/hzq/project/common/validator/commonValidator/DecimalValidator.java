package com.hzq.project.common.validator.commonValidator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

/**
 * Decimal范围校验Validator
 * Created by hzq on 16/5/13.
 */
public class DecimalValidator implements Validator {

    private BigDecimal target;
    private String message;

    /**
     * 构造方法
     * @param target 被校验的target对象
     * @param message 校验失败后的异常message
     */
    public DecimalValidator(BigDecimal target, String message) {
        this.target = target;
        this.message = message;
    }


    /**
     * 区间校验,默认不包含边界
     * @param minVal
     * @param maxVal
     * @return
     */
    public DecimalValidator validBetw(BigDecimal minVal, BigDecimal maxVal) {
        return this.validBetw(minVal, maxVal);
    }

    /**
     * 区间校验
     *
     * @param minVal      最小值
     * @param maxVal      最大值
     * @param containsMin 是否包含最小值
     * @param containsMax 是否包含最大值
     */
    public DecimalValidator validBetw(BigDecimal minVal, BigDecimal maxVal, boolean containsMin, boolean containsMax) {
        if (maxVal.compareTo(minVal) < 0) {
            BigDecimal tmp = minVal;
            minVal = maxVal;
            maxVal = tmp;
        }
        validMax(maxVal, containsMax);
        validMin(minVal, containsMin);
        return this;
    }


    /**
     * 最大值校验,不包含边界
     *
     * @param maxVal
     * @return
     */
    public DecimalValidator validMax(BigDecimal maxVal) {
        return this.validMax(maxVal, false);
    }

    /**
     * 最大值校验
     *
     * @param value       校验的值
     * @param containsMax 是否包含边界
     */
    public DecimalValidator validMax(BigDecimal value, boolean containsMax) {
        if (containsMax) {
            if (target.compareTo(value) > 0) {
                throw new DecimalBorderException(message);
            }
        } else if (target.compareTo(value) >= 0) {
            throw new DecimalBorderException(message);
        }
        return this;
    }

    /**
     * 最小值校验,不包含最小值
     *
     * @param value
     * @return
     */
    public DecimalValidator validMin(BigDecimal value) {
        return this.validMin(value, false);
    }


    /**
     * 最小值
     *
     * @param value       校验的值
     * @param containsMin 是否包含边界
     */
    public DecimalValidator validMin(BigDecimal value, boolean containsMin) {
        if (containsMin) {
            if (target.compareTo(value) < 0) {
                throw new DecimalBorderException(message);
            }
        } else if (target.compareTo(value) <= 0) {
            throw new DecimalBorderException(message);
        }
        return this;
    }

    @Override
    public void valid() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private static class DecimalBorderException extends RuntimeException {
        public DecimalBorderException(String msg) {
            super(msg);
        }
    }

}