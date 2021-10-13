package com.xiaohe66.common.net;

import com.xiaohe66.common.net.ex.RequesterException;
import com.xiaohe66.common.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiaohe
 * @time 2020.07.22 10:44
 */
@Slf4j
public class RequesterTest {


    XhRequesterImpl<XhRequesterParam, XhRequesterResult> requester = new XhRequesterImpl<>("/test2", XhRequesterResult.class);

    XhRequesterParam param = new XhRequesterParam();

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    //@Test
    public void test1() throws InterruptedException {

        try {
            XhRequesterResult result = requester.get(1);
            log.info("sync result : {}", result);
        } catch (RequesterException e) {
            // holder ex
        }

        requester.get(1, new IRequesterCallback<XhRequesterResult>() {
            @Override
            public void onSuccess(Call call, XhRequesterResult bean) {
                log.info(GsonUtils.toString(bean));
                countDownLatch.countDown();
            }

            @Override
            public void onFail(Call call, RequesterException e) {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
    }

    //@Test
    public void test2() throws InterruptedException {

        try {
            Integer result = requester.post(param);
            log.info("sync result : {}", result);
        } catch (RequesterException e) {
            // holder ex
        }

        requester.post(param, new IRequesterCallback<Integer>() {
            @Override
            public void onSuccess(Call call, Integer bean) {
                log.info(GsonUtils.toString(bean));
                countDownLatch.countDown();
            }

            @Override
            public void onFail(Call call, RequesterException e) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    //@Test
    public void test3() throws InterruptedException {

        try {
            Boolean isSuccess = requester.put(param);
            log.info("sync result : {}", isSuccess);
        } catch (RequesterException e) {
            // holder ex
        }

        requester.put(param, new IRequesterCallback<Boolean>() {
            @Override
            public void onSuccess(Call call, Boolean bean) {
                log.info(GsonUtils.toString(bean));
                countDownLatch.countDown();
            }

            @Override
            public void onFail(Call call, RequesterException e) {
                log.error(e.getMessage(), e);
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }


    //@Test
    public void test4() throws InterruptedException {

        try {
            Boolean isSuccess = requester.delete(1);
            log.info("sync result : {}", isSuccess);
        } catch (RequesterException e) {
            // holder ex
        }

        requester.delete(1, new IRequesterCallback<Boolean>() {
            @Override
            public void onSuccess(Call call, Boolean bean) {
                log.info(GsonUtils.toString(bean));
                countDownLatch.countDown();
            }

            @Override
            public void onFail(Call call, RequesterException e) {
                countDownLatch.countDown();
            }

        });
        countDownLatch.await();

    }


    //@Test
    public void test5() throws InterruptedException {


        try {
            Page<XhRequesterResult> page = requester.page(param, 1, 10);
            log.info("sync result : {}", GsonUtils.toString(page));
        } catch (RequesterException e) {
            // holder ex
        }

        requester.page(param, 1, 10, new IRequesterCallback<Page<XhRequesterResult>>() {
            @Override
            public void onSuccess(Call call, Page<XhRequesterResult> bean) {
                log.info(GsonUtils.toString(bean));
                countDownLatch.countDown();
            }

            @Override
            public void onFail(Call call, RequesterException e) {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
    }
}
