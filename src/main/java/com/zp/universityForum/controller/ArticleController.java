package com.zp.universityForum.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zp.universityForum.bean.Article;
import com.zp.universityForum.bean.Msg;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.vo.ArticleDetailsVO;
import com.zp.universityForum.bean.vo.ArticleReqVO;
import com.zp.universityForum.bean.vo.ArticleRespVO;
import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.common.interceptor.LoginInterceptor;
import com.zp.universityForum.mapper.ArticleMapper;
import com.zp.universityForum.resultmap.ViewArtAndUser;
import com.zp.universityForum.service.ArticleService;
import com.zp.universityForum.service.MsgService;
import com.zp.universityForum.utils.RedisUtil;
import com.zp.universityForum.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://1.15.243.160", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "article")
public class ArticleController {

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ArticleService articleService;
    @Autowired
    MsgService msgService;

    /**
     * 此处需要设置分页
     * 获取某一分类下所有帖子
     * @return
     */
    @GetMapping("get_all_article")
    Result getAllArticle(@RequestParam("type_id") Long typeId){
        return null;
    }


    @GetMapping("get_article_by_typeId")
    Result getArticleByTypeId(
            @RequestParam("type_id") Long typeId,
            @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        logger.info("order: " + order);
        logger.info("pageNum: " + pageNum + " pageSize: " + pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleRespVO> articleList = articleService.listArticleByTypeId(typeId, order);
        if(null != articleList && !articleList.isEmpty()) {
            PageInfo<ArticleRespVO> pageInfo = new PageInfo<>(articleList);
            return ResultUtil.setData(pageInfo);
        }
        return ResultUtil.setErrorMsg("帖子信息获取失败!");
    }

    @GetMapping("user_get_articles_by_userId")
    Result getArticlesByUserId(@CurrentUser Long userId) {
        logger.info("userId: " + userId);
        List<ArticleRespVO> articleList = articleService.listArticlesByUserId(userId);
        if(null != articleList && !articleList.isEmpty()) {
            return ResultUtil.setData(articleList);
        }
        return ResultUtil.setErrorMsg(ResultConstant.ARTICLE_NOT_FOUND_CODE, ResultConstant.ARTICLE_NOT_FOUND_MSG);
    }


    @GetMapping("get_new")
    Result getNewArticle () {
        List<ArticleRespVO> articleList = articleService.listNewArticle();
        if(null != articleList && !articleList.isEmpty()) {
            return ResultUtil.setData(articleList);
        }
        return ResultUtil.setErrorMsg("最新帖子信息获取失败!");
    }

    @GetMapping("get_hot")
    Result getHotArticle() {
        List<ArticleRespVO> articleList = articleService.listHotArticle();
        if(null != articleList && !articleList.isEmpty()) {
            return ResultUtil.setData(articleList);
        }
        return ResultUtil.setErrorMsg("热门帖子信息获取失败!");
    }

    /**
     * 顺便完成redis中view的增长
     * @param artId
     */
    @GetMapping("get_details")
    Result getDetails(Long artId) {
        ArticleDetailsVO articleDetails = articleService.getDetails(artId);
        if(null != articleDetails) {
            // 浏览量增加
            try {
                redisUtil.hincr(Constant.REDIS_KEY_VIEW_NUM, artId.toString(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultUtil.setData(articleDetails);
        }
        return ResultUtil.setErrorMsg("帖子细节信息获取失败!");
    }


    @PostMapping("create_article")
    Result createArticle(@RequestBody ArticleReqVO vo, @CurrentUser Long userId) {
        logger.error("进入create_article系统......");
        Long artId = articleService.createArticle(vo, userId);
        logger.info("create_article中的artId为：" + artId);
        if (null == artId) {
           return ResultUtil.setErrorMsg("帖子创建失败!");
        }
        return ResultUtil.setData(artId);
    }

    @GetMapping("like_article")
    Result likeArticle(@RequestParam Long artId, @RequestParam Long artUserId, @CurrentUser Long userId) {
        Object id = redisUtil.hget(Constant.REDIS_KEY_LIKE_NUM, artId.toString());
        if(null == id) {
            return ResultUtil.setErrorMsg("点赞失败！");
        }
        try {
            // 点赞成功后发送消息
            Msg msg = new Msg();
            msg.setMsgReceiveUserId(artUserId);
            msg.setMsgSendUserId(userId);
            msg.setMsgContent("");
            msg.setMsgType((byte) 1);
            msg.setKeyOne(artId);
            logger.info("msg的信息: " + msg);
            msgService.createArticleMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("该article的id是：" + artId + ", 它的点赞数目前是：" + id);
        redisUtil.hincr(Constant.REDIS_KEY_LIKE_NUM, artId.toString(), 1);
        id = redisUtil.hget(Constant.REDIS_KEY_LIKE_NUM, artId.toString());
        logger.info("该article的id是：" + artId + ", 它的点赞数目前是：" + id);
        return ResultUtil.setData();
    }
}
