package com.xiaohe66.common.web.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author xiaohe
 * @since 2022.01.07 11:31
 */
public class IsExist  extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

        String sql = String.format("select ifNull((select 1 from %s where id = #{id} limit 1),0)", tableInfo.getTableName());

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        return this.addSelectMappedStatementForOther(mapperClass, "isExistId", sqlSource, Boolean.TYPE);
    }
}
