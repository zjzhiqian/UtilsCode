package com.hzq.common.validator.commonValidator;

import com.hzq.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.Map;

/**
 * 进行非空校验的Validator
 * Created by hzq on 16/5/13.
 */
public class CheckNullValidator<T> implements Validator<T> {

    private Object object;
    private String message;

    public CheckNullValidator(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    private void checkSupport() {
        if (object instanceof String || object instanceof Collection || object instanceof Map) {
            return;
        }
        throw new UnSupportNullCheckException("不支持的非空校验类型");
    }


    @Override
    public void valid() {
        checkSupport();
        if (object == null) {
            throwException();
        } else if (object instanceof String && StringUtils.isEmpty((String) object)) {
            throwException();
        } else if (object instanceof Collection && ((Collection) object).size() == 0) {
            throwException();
        } else if (object instanceof Map && ((Map) object).size() == 0) {
            throwException();
        }
    }

    private void throwException() {
        throw new NullPointExcepton(message);
    }

}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NullPointExcepton extends RuntimeException {
    public NullPointExcepton(String msg) {
        super(msg);
    }
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class UnSupportNullCheckException extends RuntimeException {
    public UnSupportNullCheckException(String msg) {
        super(msg);
    }
}
