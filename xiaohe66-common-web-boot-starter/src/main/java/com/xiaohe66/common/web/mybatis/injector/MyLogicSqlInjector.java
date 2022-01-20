package com.xiaohe66.common.web.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

/**
 * @author xiaohe
 * @since 2021.11.18 17:52
 */
public class MyLogicSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> list = super.getMethodList(mapperClass, tableInfo);

        list.add(new IsExistId());
        list.add(new IsExist());

        return list;
    }
}
