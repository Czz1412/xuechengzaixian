package com.xuecheng.content.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        //将list转map,以备使用,排除根节点
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))   //等于1的过滤掉，即过滤掉根节点
                .collect(Collectors.toMap(key -> key.getId(),
                        value -> value,
                        (key1, key2) -> key2));   //当map中的可以重复时，以那个key为主
        //最终返回的list
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        //依次遍历每个元素,排除根节点
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId()))   //过滤掉根节点
                .forEach(item -> {
                    if (item.getParentid().equals(id)) {   //如果父节点是根节点，将节点添加到集合
                        categoryTreeDtos.add(item);
                    }
                    //找到当前节点的父节点
                    CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
                    if (courseCategoryTreeDto != null) {
                        if (courseCategoryTreeDto.getChildrenTreeNodes() == null) {
                            courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        }
                        //下边开始往ChildrenTreeNodes属性中放子节点
                        courseCategoryTreeDto.getChildrenTreeNodes().add(item);
                    }
                });
        return categoryTreeDtos;
    }

}
