package com.zp.universityForum.controller;

import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.common.annotation.CurrentUserName;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.service.CommonService;
import com.zp.universityForum.utils.QiniuCloudUtil;
import com.zp.universityForum.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author zp
 * @date 2021-04-06 10:41
 */
//@CrossOrigin(origins = "http://1.15.243.160", allowedHeaders = "Content-Type,Content-Length, Authorization, Accept,X-Requested-With",maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "common")
public class CommonController {


    @ResponseBody
    @GetMapping("search_comments")
    public Result SearchComments(
            @RequestParam("key") String key,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResultUtil.setData();
    }

    /**
     * TODO
     * 此处应当知道用户id 避免某类用户上传不当图片 后续处理
     */
    @ResponseBody
    @RequestMapping(value = "/upload_avatar", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam MultipartFile file, @CurrentUser Long userId, @CurrentUserName String userName) {
        if(file.isEmpty()) {
            return ResultUtil.setErrorMsg(ResultConstant.UPLOAD_IMG_EMPTY_MSG);
        }
        try {
            byte[] bytes = file.getBytes();
            // TODO 改为用户相关 之后好覆盖
            String imgName = Constant.IMG_USER_PREFIX + userId + Constant.IMG_USER_SUFFIX;
            try {
                System.out.println("判断七牛云是否有此文件：" + QiniuCloudUtil.isImageExist(imgName));
                String url;
                if (QiniuCloudUtil.isImageExist(imgName)) {
                    url = QiniuCloudUtil.cover64Image(bytes, imgName);
                } else {
                    url = QiniuCloudUtil.put64Image(bytes, imgName);
                }
                System.out.println("上传地址为: " + url);
                return ResultUtil.setData(url);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.setErrorMsg(ResultConstant.UPLOAD_IMG_ERROR_MSG);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.setErrorMsg(ResultConstant.UPLOAD_IMG_ERROR_MSG);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/upload_img", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam MultipartFile file, @CurrentUser Long userId) {
        if(file.isEmpty()) {
            return ResultUtil.setErrorMsg(ResultConstant.UPLOAD_IMG_EMPTY_MSG);
        }
        try {
            byte[] bytes = file.getBytes();
            // 用户id被 - userId - 包围可通过String查找获取
            String s = UUID.randomUUID().toString();
            String imgName = Constant.IMG_ART_PREFIX + s + '-' +userId + Constant.IMG_ART_SUFFIX;
            try {
                String url;
                url = Constant.HTTP_PROTOCOL +QiniuCloudUtil.put64Image(bytes, imgName);
                System.out.println("上传地址为: " + url);
                return ResultUtil.setData(url);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.setErrorMsg(ResultConstant.UPLOAD_IMG_ERROR_MSG);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.setErrorMsg(ResultConstant.UPLOAD_IMG_ERROR_MSG);
        }
    }

}
