package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author czz
 * @version 1.0
 * @description 课程信息编辑接口
 * @date 2022/9/6 11:29
 */
@Slf4j
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private TeachplanService teachplanService;

    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams) {
        log.info("接收到的分页参数为：{};查询条件为{}", pageParams, queryCourseParams);
        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParams);

    }

    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated({ValidationGroups.Inster.class}) AddCourseDto addCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);
    }

    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,editCourseDto);
    }

    @ApiOperation("删除课程基本信息")
    @DeleteMapping("/course/{courseid}")
    public void deleteCourse(@PathVariable Long courseid){
        courseBaseInfoService.deleteCourse(courseid);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("删除课程计划信息")
    @DeleteMapping("/teachplan/{id}")
    public void deleteTeachplan(@PathVariable Long id){
        teachplanService.deleteTeachplan(id);
    }


    @ApiOperation("课程计划排序——下移")
    @PostMapping("/teachplan/movedown/{id}")
    public void moveDown(@PathVariable Long id){
        teachplanService.moveDown(id);
    }

    @ApiOperation("课程计划排序——上移")
    @PostMapping("/teachplan/moveup/{id}")
    public void moveup(@PathVariable Long id){
        teachplanService.moveUp(id);
    }

    @ApiOperation("查询教师")
    @GetMapping("/courseTeacher/list/{courseid}")
    public List<CourseTeacher> getTeacherList(@PathVariable Long courseid){
        return courseBaseInfoService.getTeacherList(courseid);
    }

    @ApiOperation("添加教师")
    @PostMapping("/courseTeacher")
    public CourseTeacher saveCourseTeacher(@RequestBody CourseTeacher courseTeacher){
        return courseBaseInfoService.saveCourseTeacher(courseTeacher);
    }

    @ApiOperation("修改教师")
    @PutMapping("/courseTeacher")
    public CourseTeacher updateCourseTeacher(@RequestBody CourseTeacher courseTeacher){
        return courseBaseInfoService.updateCourseTeacher(courseTeacher);
    }

    @ApiOperation("删除教师")
    @DeleteMapping("/ourseTeacher/course/{courseid}/{id}")
    public void deleteCourserTeacher(@PathVariable Long courseid, @PathVariable Long id){
        courseBaseInfoService.deleteCourseTeacher(courseid, id);
    }
}