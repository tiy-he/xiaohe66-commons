package com.xiaohe66.commons.log;

import com.xiaohe66.commons.log.annotation.EnableLogRecord;
import com.xiaohe66.commons.log.tool.LogRecordExtend;
import com.xiaohe66.commons.log.tool.LogRecordService;
import com.xiaohe66.commons.log.annotation.LogRecord;
import com.xiaohe66.commons.log.context.LogContext;
import com.xiaohe66.commons.log.context.LogContextHolder;
import com.xiaohe66.commons.log.context.LogRecordAccount;
import com.xiaohe66.commons.log.LogRecordAutoConfiguration;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LogRecordTest {

    @Test
    public void test() throws InterruptedException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(UserService.class);
        applicationContext.register(LogSaveServiceImpl.class);
        applicationContext.register(UserLogRecordExtend.class);
        // applicationContext.register(LogRecordAutoConfiguration.class);
        applicationContext.register(Main.class);
        applicationContext.refresh();

        UserService userService = applicationContext.getBean(UserService.class);

        User user = new User();

        user.name = "mr.test";

        userService.save(user);

        // 处理日志是异步执行的，因此这里需要等待一下
        Thread.sleep(2000);
    }

    /**
     * 启动类
     */
    @EnableAspectJAutoProxy
    @EnableLogRecord
    public static class Main {

    }

    /**
     * 实体类
     */
    @Data
    public static class User {

        private Long id;
        private String name;

    }

    /**
     * 使用操作日志的业务类
     */
    @RequiredArgsConstructor
    public static class UserService {

        private final LogContextHolder logContextHolder;

        @LogRecord(success = "{{#user.name}} do {{#sth}}.",
                type = "abc",
                // operationType = OperationLogConstants.ADD,
                // functionType = OperationLogConstants.USER,
                extendClass = UserLogRecordExtend.class)
        public Long save(User user) {

            log.info("save user : {}", user);

            // 可以在业务逻辑中添加模板参数
            logContextHolder.putVariable("sth", "测试");

            return 10000L;
        }
    }

    /**
     * 操作日志扩展
     */
    @RequiredArgsConstructor
    public static class UserLogRecordExtend implements LogRecordExtend<AtomicInteger> {

        private final LogContextHolder logContextHolder;

        @Override
        public AtomicInteger executeBefore(LogContext context) {

            log.info("扩展被执行, executeBefore : {}", context);

            // 这里返回了一个参数，在后续的方法中，将会被传递
            return new AtomicInteger(1);
        }

        @Override
        public boolean parseBefore(LogContext context, AtomicInteger data) {
            log.info("扩展被执行, {}, parseBefore : {}", data.getAndIncrement(), context);

            // 扩展中可以修改模板中的参数
            logContextHolder.putVariable("sth", "新测试");

            return true;
        }

        @Override
        public boolean parseAfter(LogContext context, AtomicInteger data) {

            log.info("扩展被执行, {}, parseAfter : {}", data.getAndIncrement(), context);

            // 这里返回了 false, 后续的 保存日志方法和 saveAfter 将不会被执行
            return false;
        }

        @Override
        public void saveAfter(LogContext context, AtomicInteger data) {

            log.info("扩展被执行, saveAfter : {}", context);
        }
    }

    /**
     * 操作日志的实际保存方法
     */
    public static class LogSaveServiceImpl implements LogRecordService {

        public LogSaveServiceImpl() {
            log.debug("instance LogSaveServiceImpl");
        }

        @Override
        public void saveLog(LogContext logContext) {

            log.info("save log : {}", logContext);
        }

        @Override
        public LogRecordAccount getCurrentAccount() {
            return new LogRecordAccount() {
                @Override
                public String getId() {
                    return "1";
                }

                @Override
                public String getName() {
                    return "xiaohe66";
                }
            };
        }
    }

}
