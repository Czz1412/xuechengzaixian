package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author 陈泽哲
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {
    /**
     * @description 查询某课程的课程计划，组成树型结构
     * @param courseId
     * @return com.xuecheng.content.model.dto.TeachplanDto
     * @author Mr.M
     * @date 2022/9/9 11:10
     */
    public List<TeachplanDto> selectTreeNodes(long courseId);

    /**
     * 根据id查询是否有子小节
     */
    List<TeachplanDto> selectByParentId(Long id);

    /**
     * 根据parentid和orderby查询是否存在课程计划
     * @param parentid
     * @param orderby
     * @return
     */
    Teachplan selectByParentIdAndOrderBy(@Param("parentid") Long parentid, @Param("orderby") Integer orderby);

    /**
     * 根据课程id，parentid和orderby查询是否存在课程计划
     */
    Teachplan selectByCourseIdAndParentIdAndOrderBy(@Param("courseId") Long courseId, @Param("parentid") Long parentid,@Param("orderby") Integer orderby);

    /**
     * 根据章节id修改排序顺序
     * @param id
     * @param orderby
     */
    void updateOrderbyByid(@Param("id") Long id,@Param("orderby") Integer orderby);

}
