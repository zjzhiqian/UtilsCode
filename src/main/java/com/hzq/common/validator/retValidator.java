package com.hzq.common.validator;

import java.util.Map;

/**
 * Created by hzq on 16/5/13.
 */
public interface retValidator<T> {
    void valid(Map<String, T> map);
}
