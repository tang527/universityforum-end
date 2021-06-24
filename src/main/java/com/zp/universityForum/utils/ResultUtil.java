package com.zp.universityForum.utils;

import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.bean.common.Result;

/**
 * @author zp
 * @date 2021/03/24
 */
public class ResultUtil<T> {

    public static Result setData() {
        Result result = new Result();
        result.setData(null);
        result.setCode(ResultConstant.SUCCESS_CODE);
        return result;
    }

    public static Result setData(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setCode(ResultConstant.SUCCESS_CODE);
        return result;
    }

    public static Result setData(Object data, String msg){
        Result result = new Result();
        result.setData(data);
        result.setCode(ResultConstant.SUCCESS_CODE);
        result.setMessage(msg);
        return result;
    }

    public static Result setData(Integer code, Object data, String msg){
        Result result = new Result();
        result.setData(data);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static Result setData(Integer code, Object data){
        Result result = new Result();
        result.setData(data);
        result.setCode(code);
        return result;
    }


    public static Result setErrorMsg(Integer code, String msg){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static Result setErrorMsg(String msg){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultConstant.FALSE_CODE);
        result.setMessage(msg);
        return result;
    }
}
