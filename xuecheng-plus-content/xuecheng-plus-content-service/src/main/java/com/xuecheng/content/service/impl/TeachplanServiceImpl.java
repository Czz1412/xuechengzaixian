package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description 课程计划service接口实现类
 * @author Mr.M
 * @date 2022/9/9 11:14
 * @version 1.0
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }


    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        //课程计划id
        Long id = teachplanDto.getId();
        //修改课程计划
        if(id!=null){
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else{
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            BeanUtils.copyProperties(teachplanDto,teachplanNew);

            teachplanMapper.insert(teachplanNew);

        }
    }

    @Override
    public void deleteTeachplan(Long id) {
        //先根据ID查询信息
        Teachplan teachplan = teachplanMapper.selectById(id);
        //判断parentid是否为0
        if (teachplan.getParentid() != 0){
            //不为0说明是小节，可以直接删除
            teachplanMapper.deleteById(id);
            //同时删除关联的信息
            teachplanMediaMapper.deleteByTeachplanId(id);
            return;
        }
        //为0说明是章节,需要判断是否有子小节
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectByParentId(id);
        if (teachplanDtos.size() == 0){
            //没有子小节，可以删除章节
            teachplanMapper.deleteById(id);
            return;
        }
        //否则报错
        throw new XueChengPlusException("课程计划信息还有子级信息，无法操作");
    }

    @Override
    public void moveDown(Long id) {
        //先查询出当前的排序
        Teachplan teachplan = teachplanMapper.selectById(id);
        Long parentid = teachplan.getParentid();
        Integer orderby = teachplan.getOrderby();
        Long courseId = teachplan.getCourseId();
        //如果是章节并且后面没有章节，直接返回
        if (parentid == 0){
            //查询orderby+1的章节
            Teachplan teachplanChange = teachplanMapper.selectByCourseIdAndParentIdAndOrderBy(courseId, parentid, orderby+1);
            if (teachplanChange == null){
                return;
            }
            //本章节后面还有章节，进行交换操作
            Long idChange = teachplanChange.getId();
            Integer orderbyChange = teachplanChange.getOrderby();
            teachplanMapper.updateOrderbyByid(id, orderbyChange);
            teachplanMapper.updateOrderbyByid(idChange, orderby);
            return;
        }

        //如果是小节并且后面没有小节，直接返回
        //查询orderby+1的小节
        Teachplan teachplanChange = teachplanMapper.selectByParentIdAndOrderBy(parentid, orderby+1);
        if (teachplanChange == null){
            return;
        }
        //本小节后面还有小节，进行交换操作
        Long idChange = teachplanChange.getId();
        Integer orderbyChange = teachplanChange.getOrderby();
        teachplanMapper.updateOrderbyByid(id, orderbyChange);
        teachplanMapper.updateOrderbyByid(idChange, orderby);
    }

    @Override
    public void moveUp(Long id) {
        //先查询出当前的排序
        Teachplan teachplan = teachplanMapper.selectById(id);
        Long parentid = teachplan.getParentid();
        Integer orderby = teachplan.getOrderby();
        Long courseId = teachplan.getCourseId();
        //如果是章节并且前面没有章节，直接返回
        if (parentid == 0){
            //查询orderby-1的章节
            Teachplan teachplanChange = teachplanMapper.selectByCourseIdAndParentIdAndOrderBy(courseId, parentid, orderby-1);
            if (teachplanChange == null){
                return;
            }
            //本章节后面还有章节，进行交换操作
            Long idChange = teachplanChange.getId();
            Integer orderbyChange = teachplanChange.getOrderby();
            teachplanMapper.updateOrderbyByid(id, orderbyChange);
            teachplanMapper.updateOrderbyByid(idChange, orderby);
            return;
        }

        //如果是小节并且前面没有小节，直接返回
        //查询orderby-1的小节
        Teachplan teachplanChange = teachplanMapper.selectByParentIdAndOrderBy(parentid, orderby-1);
        if (teachplanChange == null){
            return;
        }
        //本小节前面还有小节，进行交换操作
        Long idChange = teachplanChange.getId();
        Integer orderbyChange = teachplanChange.getOrderby();
        teachplanMapper.updateOrderbyByid(id, orderbyChange);
        teachplanMapper.updateOrderbyByid(idChange, orderby);
    }

    /**
     * @description 获取最新的排序号
     * @param courseId  课程id
     * @param parentId  父课程计划id
     * @return int 最新排序号
     * @author czz
     * @date 2022/9/9 13:43
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;
    }
}