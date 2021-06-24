package com.zp.universityForum.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zp.universityForum.bean.Article;
import com.zp.universityForum.bean.vo.ArticleDetailsVO;
import com.zp.universityForum.bean.vo.ArticleReqVO;
import com.zp.universityForum.bean.vo.ArticleRespVO;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.mapper.ArticleMapper;
import com.zp.universityForum.resultmap.ViewArtAndUser;
import com.zp.universityForum.service.ArticleService;
import com.zp.universityForum.service.CommonService;
import com.zp.universityForum.utils.RedisUtil;
import com.zp.universityForum.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommonService commonService;


    @Override
    public Long createArticle(ArticleReqVO vo, Long userId) {
        Article article = new Article();
        article.setArtTitle(vo.getArtTitle());
        article.setArtContent(vo.getArtContent());
        article.setArtTypeId(vo.getArtTypeId());
        article.setArtUserId(userId);
        articleMapper.insertArticle(article);
        return article.getArtId();
    }

    @Override
    public List<Article> listAllArticle() {
        return articleMapper.listAllArticle();
    }

    @Override
    public List<ArticleRespVO> listHotArticle() {
        Integer hotArtNum = commonService.getHotArtNum();
        List<ArticleRespVO> articles = articleMapper.listHotArticle(hotArtNum);
        return dealWithArticles(articles);
    }

    @Override
    public List<ArticleRespVO> listNewArticle() {
        Integer newArtNum = commonService.getNewArtNum();
        List<ArticleRespVO> articles = articleMapper.listNewArticle(newArtNum);
        return dealWithArticles(articles);
    }

    @Override
    public List<ArticleRespVO> listArticlesByUserId(Long userId) {
        List<ArticleRespVO> articles = articleMapper.listArticlesByUserId(userId);
        return dealWithArticles(articles);
    }

    @Override
    public List<ArticleRespVO> listArticleByTypeId(Long typeId, String order) {
        if(!StringUtil.isEmpty(order)) {
            String orderType = Constant.ARTICLE_ORDER_MAP.get(order);
            if(!StringUtil.isEmpty(orderType)) {
                return articleMapper.listArticleByTypeId(typeId, orderType);
            }
        }
        return articleMapper.listArticleByTypeId(typeId, null);
    }

    @Override
    public ArticleDetailsVO getDetails(Long artId) {
        ArticleDetailsVO vo = articleMapper.getDetails(artId);
//        if(null != vo) {
//            vo.setArtUserAvatar(Constant.HTTP_PROTOCOL + vo.getArtUserAvatar());
//        }
        return vo;
    }

    @Override
    public Article getArticleById(Long artId) {
        return articleMapper.getArticleById(artId);
    }

    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    private List<ArticleRespVO> dealWithArticles(List<ArticleRespVO> articles) {
        articles.forEach(article -> {
            // 获取redis中的浏览量和点赞
            Long artId = article.getArtId();
            Object viewNum = redisUtil.hget(Constant.REDIS_KEY_VIEW_NUM, artId.toString());
            Object likeNum = redisUtil.hget(Constant.REDIS_KEY_LIKE_NUM, artId.toString());
            article.setArtViewNum((Integer) viewNum);
            article.setArtLikeNum((Integer) likeNum);
        });
        return articles;
    }

    @Override
    public List<Article> getAllArticle() {
        return null;
    }

    @Override
    public Page<Article> findArticleNoCriteria(Integer page, Integer size) {
        return null;
    }

    @Override
    public List<ViewArtAndUser> findAllByArtTypeId(Long typeId) {
        return null;
    }

    @Override
    public Page<ViewArtAndUser> findArtAndUser(Integer page, Integer size) {
        return null;
    }

    @Override
    public Page<ViewArtAndUser> findnew(Integer page, Integer size) {
        return null;
    }

    @Override
    public Page<ViewArtAndUser> findAllArtAndUser(Integer page, Integer size) {
        return null;
    }

    @Override
    public Article Post(Article article) {
        return null;
    }

}
