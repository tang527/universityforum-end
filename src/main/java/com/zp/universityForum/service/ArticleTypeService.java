package com.zp.universityForum.service;

import com.zp.universityForum.bean.ArticleType;

import java.util.List;

public interface ArticleTypeService {
    List<ArticleType> listAllArticleType();
    List<ArticleType> listHotArticleType();

}
