package com.xiaohe66.common.web.mybatis.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author xiaohe
 * @since 2021.12.25 13:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntityInputIdDetail extends BaseEntityInputId {

    private LocalDateTime createTime;

    @TableLogic
    private Boolean deleted;
}
