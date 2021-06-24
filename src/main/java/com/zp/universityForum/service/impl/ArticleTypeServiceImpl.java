package com.zp.universityForum.service.impl;

import com.github.pagehelper.Page;
import com.zp.universityForum.bean.ArticleType;
import com.zp.universityForum.mapper.ArticleTypeMapper;
import com.zp.universityForum.service.ArticleTypeService;
import com.zp.universityForum.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Autowired
    ArticleTypeMapper articleTypeMapper;

    @Autowired
    CommonService commonService;

    @Override
    public List<ArticleType> listAllArticleType() {
        return articleTypeMapper.listAllArticleType();
    }

    @Override
    public List<ArticleType> listHotArticleType() {
        // 首先获取热门板块数量 依据数量去查询
        Integer hotTypeNum = commonService.getHotTypeNum();
        return articleTypeMapper.listHotArticleType(hotTypeNum);
    }
}
