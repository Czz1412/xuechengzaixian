package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @author czz
 * @version 1.0
 * @description 课程基本信息管理业务接口
 * @date 2022/9/6 21:42
 */
public interface CourseBaseInfoService {
    /**
     * @description 课程查询接口
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);


    /**
     * @description 添加课程基本信息
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * @description 根据id查询课程基本信息
     */
    public CourseBaseInfoDto getCourseBaseInfo(long courseId);

    /**
     * @description 修改课程信息
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);

    /**
     * 根据课程Id查询教师信息
     * @param courseid
     * @return
     */
    List<CourseTeacher> getTeacherList(Long courseid);

    /**
     * 新增教师
     * @param courseTeacher
     * @return
     */
    CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher);

    /**
     * 修改教师
     * @param courseTeacher
     * @return
     */
    CourseTeacher updateCourseTeacher(CourseTeacher courseTeacher);

    /**
     * 删除教师
     * @param courseid
     * @param id
     */
    void deleteCourseTeacher(Long courseid, Long id);
}