package com.zp.universityForum.common.exception;

/**
 * @author zp
 * @date 2021-04-02 14:56
 */
public class BusinessException extends RuntimeException {

    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
