package com.zp.universityForum.bean.common;

import lombok.Data;

import java.util.Date;

/**
 * @author zp
 * @date 2021-04-14 14:34
 */
@Data
public class SystemConfig {
    private Integer id;
    private String configKey;
    private Integer configValue;
    private String remark;
    private Date created;
    private Date updated;
}
