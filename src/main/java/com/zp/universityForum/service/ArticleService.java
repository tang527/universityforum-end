package com.zp.universityForum.service;

import com.github.pagehelper.Page;
import com.zp.universityForum.bean.Article;
import com.zp.universityForum.bean.vo.ArticleDetailsVO;
import com.zp.universityForum.bean.vo.ArticleReqVO;
import com.zp.universityForum.bean.vo.ArticleRespVO;
import com.zp.universityForum.resultmap.ViewArtAndUser;

import java.util.List;

public interface ArticleService {
    Long createArticle(ArticleReqVO vo, Long userId);
    List<Article> listAllArticle();
    List<ArticleRespVO> listHotArticle();
    List<ArticleRespVO> listNewArticle();
    List<ArticleRespVO> listArticleByTypeId(Long typeId, String order);
    List<ArticleRespVO> listArticlesByUserId(Long userId);
    ArticleDetailsVO getDetails(Long artId);
    Article getArticleById(Long artId);
    int updateArticle(Article article);

    List<Article> getAllArticle();
    Page<Article> findArticleNoCriteria(Integer page, Integer size);//无条件查询
    List<ViewArtAndUser>findAllByArtTypeId(Long typeId);
    Page<ViewArtAndUser>findArtAndUser(Integer page,Integer size);
    Page<ViewArtAndUser>findnew(Integer page,Integer size);
    Page<ViewArtAndUser>findAllArtAndUser(Integer page,Integer size);
    Article Post(Article article);
}
