package com.orient.download.enums;

/**
 * ${DESCRIPTION}
 * json数据返回是否成功的判断类型
 *
 * @author GNY
 * @create 2017-10-20 13:53
 */
public enum HttpResponseStatus {

    SUCCESS("success"), FAIL("fail");

    private String status;

    HttpResponseStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

}
