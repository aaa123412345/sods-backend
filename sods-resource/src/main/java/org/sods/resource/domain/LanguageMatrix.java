package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_language_matrix")
public class LanguageMatrix {

    private String lsfBasic;
    private String lsfIn;
    private String languageFull;
}
