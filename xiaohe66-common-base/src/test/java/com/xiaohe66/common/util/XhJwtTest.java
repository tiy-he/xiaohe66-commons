package com.xiaohe66.common.util;

import com.xiaohe66.common.util.ex.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XhJwtTest {

    private static XhJwt jwt = new XhJwt(3, "测试", "1");

    public static void main(String[] args) throws InterruptedException {

        XhJwt.CurrentAccount originAccount = new XhJwt.CurrentAccount();
        originAccount.setId(IdWorker.genId());
        originAccount.setName("xiaohe66");

        String token = jwt.genToken(originAccount);

        XhJwt.CurrentAccount account = jwt.verifyAndGet(token);

        log.info("account : {}", account);

        // 测试黑名单
        jwt.getBlacklist().add("测试");
        try {
            jwt.verifyAndGet(token);
            throw new BusinessException("黑名单测试不通过");

        } catch (BusinessException e) {
            log.info("黑名单测试通过");
            jwt.getBlacklist().clear();
        }

        // 改变 secret
        jwt.setSecret("2", SystemClock.currentTimeMillis() + 500);

        // 改变后，还未过有效时间，因此在这里获取，是可以的。
        jwt.verifyAndGet(token);

        log.info("ok");

        Thread.sleep(500);

        // 最后这行抛出异常才是对的。
        try {
            jwt.verifyAndGet(token);
            throw new BusinessException("过期测试不通过");

        } catch (BusinessException ignore) {
        }
        log.info("测试通过");
    }

}