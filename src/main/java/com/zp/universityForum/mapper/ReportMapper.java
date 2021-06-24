package com.zp.universityForum.mapper;

import com.zp.universityForum.bean.Report;
import com.zp.universityForum.bean.ReportType;
import com.zp.universityForum.bean.vo.ReportReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-06 0:58
 */
@Mapper
public interface ReportMapper {
    List<ReportType> listReportTypes(@Param("kind") Integer kind);
    int insertReport(ReportReqVO report);
    Report getComReportByUserAndId(
            @Param("reportUserId") Long reportUserId,
            @Param("reportedUserId") Long reportedUserId,
            @Param("keyOne") Long keyOne
    );

    Report getMulReportByUserAndId(
            @Param("reportUserId") Long reportUserId,
            @Param("reportedUserId") Long reportedUserId,
            @Param("keyOne") Long keyOne,
            @Param("keyTwo") Long keyTwo,
            @Param("keyThree") Long keyThree
    );
}
