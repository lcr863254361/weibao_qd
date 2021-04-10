package com.orient.utils.restful;

/**
 * a runtime exception when send a restful request
 *
 * @author Seraph
 *         2016-12-21 下午2:44
 */
public class RestfulRequestException extends RuntimeException{

    public RestfulRequestException(String message, Throwable cause){
        super(message, cause);
    }


}
