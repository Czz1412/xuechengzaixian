package com.xuecheng.content.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author 陈泽哲
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    /**
     * 课程分类查询
     * @param id
     * @return
     */
    public List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
