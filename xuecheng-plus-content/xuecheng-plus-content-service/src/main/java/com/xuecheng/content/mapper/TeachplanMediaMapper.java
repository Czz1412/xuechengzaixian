package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.po.TeachplanMedia;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈泽哲
 */
public interface TeachplanMediaMapper extends BaseMapper<TeachplanMedia> {

    /**
     * 根据课程计划id删除关联信息
     * @param id
     */
    void deleteByTeachplanId(Long id);
}
