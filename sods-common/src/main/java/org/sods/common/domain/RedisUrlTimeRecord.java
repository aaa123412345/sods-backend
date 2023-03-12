package org.sods.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RedisUrlTimeRecord {
    private String user;
    private String url;
    private Integer timeInSecond;
}
