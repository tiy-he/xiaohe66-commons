package com.xiaohe66.gen.template.impl.prop;

import com.xiaohe66.gen.template.bo.CodeTemplateProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiaohe
 * @since 2022.02.25 11:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MapperCodeBuildProperty extends CodeTemplateProperty {

    private String entityPackage;
}
