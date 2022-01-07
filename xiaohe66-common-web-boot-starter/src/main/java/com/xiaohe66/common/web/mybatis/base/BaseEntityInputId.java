package com.xiaohe66.common.web.mybatis.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author xiaohe
 * @since 2021.12.25 13:43
 */
@Data
public abstract class BaseEntityInputId {

    @TableId(type = IdType.INPUT)
    private Long id;
}
