package com.zp.universityForum.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.*;
import com.zp.universityForum.common.exception.BusinessException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * @author zp
 * @date 2021-03-06 9:49
 */
public class QiniuCloudUtil {

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "七牛云密钥";
    private static final String SECRET_KEY = "xxx";

    // 要上传的空间
    private static final String BUCKET = "你的空间";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    // 带指定Region对象的配置类 华南属于 region2
    private static Configuration cfg = new Configuration(Region.region2());

    // 图片外链域名
    private static final String DOMAIN = "cdn.colonel.wang/";

    // 获取上传凭证
    public static String getUpToken() {
        String uploadToken = auth.uploadToken(BUCKET, null, 3600, new StringMap().put("insertOnly", 1));
        return uploadToken;
    }

    public static String getCoverToken(String key) {
        String uploadToken = auth.uploadToken(BUCKET, key, 3600, null);
        return uploadToken;
    }


    public static String upload(String filePath, String fileName) throws IOException {
        // 创建上传对象
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            String token = auth.uploadToken(BUCKET);
            if(!StringUtils.isNullOrEmpty(token)) {
                throw new BusinessException("未获取到token, 请重试！");
            }
            Response res = uploadManager.put(filePath, fileName, token);
        } catch (QiniuException e) {
            Response res = e.response;
        }
        return null;
    }

    // base64方式上传
    public static String put64Image(byte[] base64, String key) throws Exception {
        String file64 = Base64.encodeToString(base64, 0);
        Integer len = base64.length;

        String url = "http://upload-z2.qiniu.com/putb64/" + len + "/key/" + UrlSafeBase64.encodeToString(key);
        RequestBody requestBody = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(requestBody).build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();

        // 返回图片地址
        return DOMAIN + key;
    }

    // base64方式上传
    public static String cover64Image(byte[] base64, String key) throws Exception {
        String file64 = Base64.encodeToString(base64, 0);
        Integer len = base64.length;

        String url = "http://upload-z2.qiniu.com/putb64/" + len + "/key/" + UrlSafeBase64.encodeToString(key);
        RequestBody requestBody = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getCoverToken(key))
                .post(requestBody).build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();

        // 返回图片地址
        return DOMAIN + key;
    }

    // 判断七牛云是否已有此文件名
    public static boolean isImageExist(String key) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(BUCKET, key);
            if (null != fileInfo) {
                return true;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return false;
    }

}