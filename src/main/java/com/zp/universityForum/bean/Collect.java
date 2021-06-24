package com.zp.universityForum.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Collect {

    private Long colId;

    private Long colArtId;//收藏文章id
    private Long colUserId;//收藏用户得id/谁收藏了id
}
