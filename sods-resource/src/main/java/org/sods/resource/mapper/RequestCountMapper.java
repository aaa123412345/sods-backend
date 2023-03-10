package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.sods.resource.domain.RequestCount;

import java.util.List;


public interface RequestCountMapper extends BaseMapper<RequestCount> {
    int batchInsert(List<RequestCount> objects);
}
