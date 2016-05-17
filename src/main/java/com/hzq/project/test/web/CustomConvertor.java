package com.hzq.project.test.web;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzq on 16/5/9.
 */
public class CustomConvertor implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
//        return Collections.singleton(new ConvertiblePair(String.class, User.class));
        return null;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        String string = (String) source;
        RequestParam requestParam = targetType.getAnnotation(RequestParam.class);
        Map map = new HashMap<>();

        return null;
//        User user = new User();
//        user.setName(string);
//        return user;
    }
}
