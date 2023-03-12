package org.sods.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RedisUrlTimeFlag {

    private String url;
    private LocalDateTime time;

    @JSONField(serialize = false)
    public static RedisUrlTimeRecord toRedisUrlTimeRecord(RedisUrlTimeFlag old, LocalDateTime newDateTime, String user) {
       String tmpUser = user;
       String tmpUrl = old.getUrl();
       LocalDateTime oldTime = old.getTime();
       Duration duration = Duration.between(oldTime, newDateTime);
       long seconds = duration.getSeconds();
       return new RedisUrlTimeRecord(tmpUser, tmpUrl, (int) seconds);
    }



}
