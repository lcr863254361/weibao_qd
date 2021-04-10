package com.orient.download.enums;

import com.orient.download.enums.HttpResponseStatus;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2017-12-28 16:51
 */
public class HttpResponse<T> implements Serializable {

    private String result = HttpResponseStatus.SUCCESS.toString();
    private String msg = "";
    private T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
