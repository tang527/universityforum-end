package com.zp.universityForum.controller;


import com.github.pagehelper.PageInfo;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.ArticleType;
import com.zp.universityForum.service.ArticleTypeService;
import com.zp.universityForum.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "http://1.15.243.160", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "article_type")
public class ArticleTypeController {
    @Autowired
    ArticleTypeService articleTypeService;

    /**
     * 获取全部类别 由于类别所限，暂不做分页
     * @return
     */
    @GetMapping("get_all")
    public Result getAllArticleType(){
        List<ArticleType> typeList = articleTypeService.listAllArticleType();
        if(null != typeList && !typeList.isEmpty()) {
            return ResultUtil.setData(typeList);
        }
        return ResultUtil.setErrorMsg("版块信息查询失败!");
    }

    /**
     * 获取热门类别 根据级别排序 注意：此数量应存在数据库中 与前端对应
     * @return
     */
    @GetMapping("get_hot")
    public Result getHotArticleType() {
        List<ArticleType> typeList = articleTypeService.listHotArticleType();
        if(null != typeList && !typeList.isEmpty()) {
            return ResultUtil.setData(typeList);
        }
        return ResultUtil.setErrorMsg("热门版块查询失败!");
    }

}
