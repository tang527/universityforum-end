package com.zp.universityForum.mapper;

import com.zp.universityForum.bean.ArticleType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ArticleTypeMapper{
    List<ArticleType> listAllArticleType();
    List<ArticleType> listHotArticleType(Integer num);
}
