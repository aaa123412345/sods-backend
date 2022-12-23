package org.sods.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.sods.security.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userid);
}
