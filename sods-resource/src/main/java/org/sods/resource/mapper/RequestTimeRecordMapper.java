package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.sods.resource.domain.RequestCount;
import org.sods.resource.domain.RequestTimeRecord;

import java.util.List;

public interface RequestTimeRecordMapper extends BaseMapper<RequestTimeRecord> {
    int batchInsert(List<RequestTimeRecord> objects);
}
