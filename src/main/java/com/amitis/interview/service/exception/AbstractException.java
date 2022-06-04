package com.amitis.interview.service.exception;


import com.amitis.interview.dto.BaseDto;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractException extends RuntimeException  {


	private static final long serialVersionUID = 1L;
	private Map<String, Object> messageArgs;
    private HttpStatus httpStatus;

    public AbstractException() {
        super();
    }

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractException(Throwable cause) {
        super(cause);
    }

    protected AbstractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void addMessageArg(final String messageArg, final Object messageVal) {
        if (this.messageArgs==null) {
            this.messageArgs = new HashMap<>();
        }
        this.messageArgs.put(messageArg, messageVal);
    }




    public Map<String, Object> getMessageArgs() {
        if (messageArgs==null) {
            return messageArgs;
        }
        return null;
    }

    public Object getMessageArg(final String key) {
        if (messageArgs==null) {
            return "";
        } else {
            return messageArgs.get(key);
        }
    }


    public static String getFieldValue(String fieldName, BaseDto entity) {
        String res = "";
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            Field[] superClassFields = entity.getClass().getSuperclass().getDeclaredFields();
            String value = fieldName.toLowerCase();
            for (Field entityField : fields) {
                entityField.setAccessible(true);
                if (value.equalsIgnoreCase(entityField.getName().toLowerCase())) {
                    entityField.setAccessible(true);
                    String getterMethodName = "get" + capitalizeName(fieldName);
                    Method getter = entity.getClass().getDeclaredMethod(getterMethodName);
                    res = getter.invoke(entity).toString();
                    break;
                }
            }
            for (Field entityField : superClassFields) {
                entityField.setAccessible(true);
                if (value.equalsIgnoreCase(entityField.getName().toLowerCase())) {
                    entityField.setAccessible(true);
                    String getterMethodName = "get" + capitalizeName(fieldName);
                    Method getter = entity.getClass().getSuperclass().getMethod(getterMethodName);
                    res = getter.invoke(entity).toString();
                    break;
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
        return res;
    }


    private static String capitalizeName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * @return always return HttpStatus.BAD_REQUEST
     */
    public int getHttpStatusValue() {
        return httpStatus.value();
    }
    public String getHttpStatusMessage(){
        return httpStatus.getReasonPhrase();
    }

    public String getStringStackTrack() {
        String stackTrace = "";
        for (int i = 0; i < getStackTrace().length; i++) {
            StackTraceElement element = getStackTrace()[i];
            stackTrace = stackTrace + "\n" + element.toString();
        }

        stackTrace =  stackTrace + "\n"+"***********************************************************************";
        stackTrace =  stackTrace + "\n"+"***********************************************************************";
        return stackTrace;
    }
}
