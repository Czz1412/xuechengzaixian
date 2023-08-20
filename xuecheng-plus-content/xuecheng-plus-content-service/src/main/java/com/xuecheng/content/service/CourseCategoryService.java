package com.xuecheng.content.service;

import java.util.List;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

/**
 * description
 *
 * @author czz 2023/08/20 22:08
 */
public interface CourseCategoryService {
    /**
     * 课程分类树形结构查询
     *
     * @return
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}