package com.xiaohe66.commons.log.context;

import com.xiaohe66.commons.log.context.impl.CacheParameterNameDiscovererTest;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class LogContextTest {

    @Test
    public void test1() throws NoSuchMethodException {

        Method method = CacheParameterNameDiscovererTest.class.getMethod("testFunction", String.class, Integer.TYPE);

        LogContext logContext = new LogContext();
        logContext.setMethod(method);
        logContext.setArgs(new Object[]{"123", 456});

        TestBean correct = new TestBean();
        correct.setS1("123");
        correct.setI1(456);

        assertNull(logContext.getArgsBean());

        TestBean testBean = logContext.formatArgsBean(TestBean.class);

        assertEquals(correct, testBean);
        assertSame(testBean, logContext.getArgsBean());

        assertEquals(correct, logContext.formatArgsBean(TestBean.class));
        assertNotSame(testBean, logContext.getArgsBean());
    }

    public String testFunction(String s1, int i1) {
        return null;
    }

    @Data
    public static class TestBean {

        private String s1;
        private Integer i1;
    }
}
