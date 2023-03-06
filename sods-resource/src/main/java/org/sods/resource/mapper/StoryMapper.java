package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sods.resource.domain.Booth;
import org.sods.resource.domain.Story;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StoryMapper extends BaseMapper<Story> {


    @Select("SELECT * FROM `STORY` WHERE delete_flag = 1")
    List<Story> findDeletedStories();

}
