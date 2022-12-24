package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("PageMapper")
public class PageRouter {
    @TableId
    private String pageID;

    private String url;

    private String language;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

}
