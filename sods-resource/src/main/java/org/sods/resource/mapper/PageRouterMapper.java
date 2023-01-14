package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sods.resource.domain.PageRouter;


public interface PageRouterMapper extends BaseMapper<PageRouter> {

    String selectPageDataByURLAndLan(String url, String lang);
}
