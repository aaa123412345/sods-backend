package org.sods.security.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;



/**
 User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    
    /**
    * Key
    */
    @TableId
    private Long userId;

    private String userName;

    private String nickName;

    private String password;
    /**
    * 1 => Stop 0 => ok
    */
    private String status;

    private String email;

    private String phonenumber;

    /**
     * 0 => Boy 1 => Gir 2 => Unknown
     */

    private String sex;

    private String avatar;


    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Integer delFlag;

    @Version
    private Integer version;

    private String userType;


}