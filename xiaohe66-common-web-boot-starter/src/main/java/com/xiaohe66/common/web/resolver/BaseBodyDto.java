package com.xiaohe66.common.web.resolver;

import lombok.Data;

/**
 * @author xiaohe
 * @since 2022.01.06 09:50
 */
@Data
public class BaseBodyDto implements BodyDto {

    private transient String body;

}
