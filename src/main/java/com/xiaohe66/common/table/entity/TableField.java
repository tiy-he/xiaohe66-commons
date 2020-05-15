package com.xiaohe66.common.table.entity;

import com.xiaohe66.common.table.TableFieldVerifier;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author xiaohe
 * @time 2020.04.08 18:02
 */
@Getter
@Builder
public class TableField {

    private String tableTitle;
    private String fieldName;
    private boolean isUnique;
    private boolean isUpdate;
    private boolean isRequire;
    private String regex;
    private Object defVal;
    private TableFieldVerifier verifyer;
    private BiFunction<ReaderContext, List<Object>, Object> valueCreator;

    private boolean isShow;
    private String showTitle;
}
