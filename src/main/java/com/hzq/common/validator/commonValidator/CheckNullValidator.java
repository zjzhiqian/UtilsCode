package com.hzq.common.validator.commonValidator;

import com.hzq.common.Ttttt;
import com.hzq.common.User;
import com.hzq.common.validator.Annotation.CheckNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行非空校验的Validator,支持String,List,Map空校验,支持list级联校验
 * Created by hzq on 16/5/13.
 */
public class CheckNullValidator implements Validator {

    /**
     * 需要校验的对象
     */
    private Object object;
    /**
     * 校验失败的message
     */
    private String message;
    /**
     * true表示使用注解校验
     */
    private boolean multyCheck;

    public CheckNullValidator(Object object, String message) {
        this(object, message, false);
    }

    public CheckNullValidator(Object object, String message, boolean multyCheck) {
        this.object = object;
        this.message = message;
        this.multyCheck = multyCheck;
    }


    private void simpleCheck() {
        if (object == null) {
            throwException(message);
        }
        if (!(object instanceof String || object instanceof List || object instanceof Map)) {
            throw new UnSupportNullCheckException("不支持的Null校验类型");
        }
        if (object instanceof String && StringUtils.isEmpty((String) object)) {
            throwException(message);
        } else if (object instanceof List && ((List) object).size() == 0) {
            throwException(message);
        } else if (object instanceof Map && ((Map) object).size() == 0) {
            throwException(message);
        }
    }

    @Override
    public void valid() {
        if (multyCheck) {
            handleObject(object);
        } else {
            simpleCheck();
        }
    }

    /**
     * 对List进行非空校验和具体参数空校验
     */
    private void handleList(List<Object> target, String message) {
        //List长度空校验
        if (target.size() == 0) {
            throwException(message);
        }
        //长度不为空,对object进行校验
        target.forEach(this::handleObject);
    }


    /**
     * 对object进行非空校验
     *
     * @param target
     */
    private void handleObject(Object target) {
        for (Field field : target.getClass().getDeclaredFields()) {
            CheckNull anno = field.getAnnotation(CheckNull.class);
            //过滤出带注解的字段
            if (anno != null) {
                Object val = null;
                try {
                    field.setAccessible(true);
                    val = field.get(target);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (val == null) {//为空,抛异常,msg为注解的value
                    throwException(anno.value());
                }
                if (val instanceof List) {//List字段,调用handleList方法
                    handleList((List) val, anno.value());
                } else if (target instanceof String && StringUtils.isEmpty((String) target)) { //Sting类型
                    throwException();
                } else if (object instanceof Map && ((Map) object).size() == 0) { //map判断size
                    throwException();
                } else {
                    throw new UnSupportNullCheckException("不支持的Null校验类型");
                }
            }
        }
    }


    public static void main(String args[]) {
        List<User> user = new ArrayList<>();
        User u = new User();
        u.setName("123");
        u.setAge(3);
        u.setMap(new HashMap<>());
        List list = new ArrayList();
        u.setLists(list);
        Ttttt tt = new Ttttt();
        tt.setName("mc");
        Map map = new HashMap<>();
//        map.put("1","2");
        tt.setMap(map);
        list.add(tt);
        user.add(u);
        new CheckNullValidator(user, "校验失败", true).valid();
    }


    /**
     * 简单的抛异常方法
     */
    private void throwException() {
        throw new NullPointExcepton(message);
    }

    private void throwException(String msg) {
        throw new NullPointExcepton(msg);
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
