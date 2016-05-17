package com.hzq.project.common.validator.commonValidator;

import java.util.Map;

/**
 * 使ValidatorComposite 储存 返回数据
 * Created by hzq on 16/5/13.
 */
public interface RetValidator<T> {
    void valid(Map<String, T> map);
}
