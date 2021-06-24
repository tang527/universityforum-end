package com.zp.universityForum.common.handler;

import com.zp.universityForum.bean.Article;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.service.ArticleService;
import com.zp.universityForum.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author zp
 * @date 2021-05-08 1:45
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ListenHandler() {
        logger.info("开始数据初始化");
    }

    @PostConstruct
    public void init() throws Exception{
        logger.info("数据库 初始化开始");
        List<Article> articles = articleService.listAllArticle();
        articles.forEach(article -> {
            redisUtil.hset("viewNum", Long.toString(article.getArtId()), article.getArtLikeNum());
            redisUtil.hset("likeNum", Long.toString(article.getArtId()), article.getArtViewNum());
        });
        logger.info("写入 redis");
    }

    @PreDestroy
    public void afterDestroy() throws Exception{
        logger.info("关闭====================================");
        // 将redis中数据写入数据库
        Set<DefaultTypedTuple> viewNum = redisUtil.zsReverseRangeWithScores("viewNum");
        Set<DefaultTypedTuple> likeNum = redisUtil.zsReverseRangeWithScores("likeNum");
        this.writeNum(viewNum, Constant.REDIS_KEY_VIEW_NUM);
        this.writeNum(likeNum, Constant.REDIS_KEY_LIKE_NUM);
        logger.info("数据库拿出到redis完毕");
    }

    private void writeNum(Set<DefaultTypedTuple> set, String fieldName) {
        set.forEach(item-> {
            DefaultTypedTuple li = (DefaultTypedTuple) item;
            Long id = Long.valueOf((String) li.getValue());
            int num = li.getScore().intValue();

            Article article = articleService.getArticleById(id);
            if(fieldName.equals("viewNum")) {
                article.setArtViewNum(num);
            } else if (fieldName.equals("likeNum")) {
                article.setArtLikeNum(num);
            }
            articleService.updateArticle(article);
            logger.info(fieldName + "更新完毕");
        });
    }
}
