package com.zp.universityForum.mapper;

import com.github.pagehelper.Page;
import com.zp.universityForum.bean.Article;
import com.zp.universityForum.bean.vo.ArticleDetailsVO;
import com.zp.universityForum.bean.vo.ArticleRespVO;
import com.zp.universityForum.resultmap.ViewArtAndUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Pageable;
import java.util.List;

@Mapper
public interface ArticleMapper {

    int insertArticle(Article article);
    List<Article> listAllArticle();
    List<ArticleRespVO> listHotArticle(Integer num);
    List<ArticleRespVO> listNewArticle(Integer num);
    List<ArticleRespVO> listArticleByTypeId(@Param("typeId") Long typeId, @Param("order") String order);
    List<ArticleRespVO> listArticlesByUserId(Long userId);
    //    此处细节无需查询type 故写了另一个DTO (系统未明确区分DTO与VO 此乃笔者的水平问题 见谅)
    ArticleDetailsVO getDetails(@Param("artId") Long artId);
    Article getArticleById(@Param("artId") Long artId);
    int updateArticle(Article article);
}
