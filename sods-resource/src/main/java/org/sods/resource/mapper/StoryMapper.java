package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sods.resource.domain.Story;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StoryMapper extends BaseMapper<Story> {
}
