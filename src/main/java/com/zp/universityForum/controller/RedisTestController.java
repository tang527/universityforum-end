package com.zp.universityForum.controller;

import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.service.UserService;
import com.zp.universityForum.utils.RedisUtil;
import com.zp.universityForum.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "redis")
public class RedisTestController {

    private Logger logger = LoggerFactory.getLogger(RedisTestController.class);


    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;

    @RequestMapping(value = "save", method = {RequestMethod.GET})
    public Object redisSave(String key, String value) {
        redisUtil.set(key, value);
        return redisUtil.get(key);
    }

    @RequestMapping(value = "get_all_key_value", method = {RequestMethod.GET})
    public Object redisGetAllKeyValue(){
        Set<String> keys = redisUtil.keys("*");
        Iterator<String> iterator = keys.iterator();
        Map<String, Object> map = new HashMap<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object o = redisUtil.get(key);
            map.put(key, o);
        }
        return map;
    }

    /**
     * 注：此方法太麻烦已弃用, 需要手动在Controller中标明 request请求，再传递request参数到此方法。改为CurrentUser注解
     * @param request
     * @return
     */
    @RequestMapping(value = "test", method = {RequestMethod.GET})
    public Result test(HttpServletRequest request) {
        Long userId = null;
        try {
            userId = Long.valueOf(String.valueOf(userService.getLoginUserId(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("从拦截器中获取的userId为：" + userId);
        return ResultUtil.setData(null);
    }

    @RequestMapping(value = "test_annotation", method = {RequestMethod.GET})
    public Result testAnnotation(@CurrentUser Long userId) {
        System.out.println("从注解和拦截器中获取的userId为：" + userId);
        return ResultUtil.setData(null);
    }


}
