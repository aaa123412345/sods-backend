package org.sods.security.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long id;

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


    private Long createBy;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;

    private Integer delFlag;

    private String userType;


}