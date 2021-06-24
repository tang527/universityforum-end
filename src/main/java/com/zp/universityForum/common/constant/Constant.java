package com.zp.universityForum.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zp
 * @date 2021-04-02 18:15
 */
public interface Constant {
    String LOGIN_USER_ID ="userId";
    String LOGIN_USER_NAME = "userName";

    String IMG_CDN_PREFIX = "cdn.colonel.wang/";
    String IMG_USER_PREFIX = "forum/img/user/";
    String IMG_USER_SUFFIX = "-user_avatar";
    String IMG_ART_PREFIX = "forum/img/art/";
    String IMG_ART_SUFFIX = "-user_art";

    String HTTP_PROTOCOL = "http://";

    String CONFIG_HOT_TYPE_NUM_KEY = "hot_type_num";
    String CONFIG_HOT_ART_NUM_KEY = "hot_art_num";
    String CONFIG_NEW_ART_NUM_KEY = "new_art_num";

    String REDIS_KEY_VIEW_NUM = "viewNum";
    String REDIS_KEY_LIKE_NUM = "likeNum";
    String REDIS_KEY_DISABLED_TOKEN = "disabled_token";

    String OMIT_IMG_STR = "图片消息 已省略...";

    /**
     * 此处Map为常量映射，根据前端传输的order值决定排序方式
     */
    Map<String, String> ARTICLE_ORDER_MAP = new HashMap() {{
        put("like", "art.art_like_num DESC");
        put("view", "art.art_view_num DESC");
        put("comment", "art.art_com_num DESC");
        put("updated", "art.art_updated DESC");
        put("created", "art.art_created");
    }};
}
