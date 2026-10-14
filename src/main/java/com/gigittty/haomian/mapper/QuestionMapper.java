package com.gigittty.haomian.mapper;

import com.gigittty.haomian.model.entity.Post;
import com.gigittty.haomian.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author 20669
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2026-08-16 22:20:20
* @Entity generator.domain.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    @Select("select * from question where updateTime >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    @Select("select * from question")
    List<Question> listQuestionWithDelete();
}




