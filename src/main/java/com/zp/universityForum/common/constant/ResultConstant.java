package com.zp.universityForum.common.constant;

/**
 * 返回标准常量
 * @author zp
 */
public interface ResultConstant {

    /**
     * 请求成功代码
     */
    Integer SUCCESS_CODE = 200;

    /**
     * 刷新token代码
     */
    Integer REFRESH_TOKEN = 210;

    /**
     * 请求地址未找到代码
     */
    Integer NOT_FOUND_CODE = 404;

    /**
     * 等待举报处理
     */
    Integer WAIT_REPORT_HANDLE_CODE = 320;

    /**
     * 举报已处理
     */
    Integer REPORT_HANDLE_DONE_CODE = 321;

    /**
     * 请求失败代码
     */
    Integer FALSE_CODE = 500;

    Integer ACCOUNT_PSW_ERROR_CODE = 300;

    Integer ACCOUNT_NOT_LOGIN_CODE = 301;

    Integer ACCOUNT_NEED_RE_LOGIN_CODE = 302;

    Integer USER_INFO_UPDATE_FAILED_CODE = 310;

    Integer ARTICLE_NOT_FOUND_CODE = 314;

    String ACCOUNT_NOT_EXIST_MSG = "账户不存在！";

    String ACCOUNT_PSW_ERROR_MSG = "密码错误！";

    String ACCOUNT_CREATED_FAILED_MSG = "账户创建失败！";

    String USER_INFO_UPDATE_FAILED_MSG = "用户信息更新失败！";

    String ARTICLE_NOT_FOUND_MSG = "暂无获取到的帖子信息";

    String UPLOAD_IMG_EMPTY_MSG = "上传图片不能为空！";

    String UPLOAD_IMG_ERROR_MSG = "上传图片异常！";

    String LOGIN_SUCCESS_MSG = "登陆成功！";

    String ACCOUNT_CREATED_SUCCESS_MSG = "注册成功！";

    String ACCOUNT_NOT_LOGIN_MSG = "用户未登录!";

    String ACCOUNT_NEED_RE_LOGIN_MSG = "需要重新登陆!";

    String WAIT_REPORT_HANDLE_MSG = "您已经举报过了，请耐心等待！";

    String REPORT_HANDLE_DONE_MSG = "您的举报已经处理，请查看消息！";
}
