package com.xiaohe66.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 分布式高效有序ID生成器
 * <p>
 * 源于mybatis-plus的com.baomidou.mybatisplus.core.toolkit.Sequence
 * <p>
 * 64位 = [0][时间戳:41位][数据中心:5位][工作id:5位][序列号:12位]
 *
 * @author hubin
 * @author xiaohe66
 * @since 2021.11.05
 */
@Slf4j
public final class Sequence {

    /**
     * 机器标识位数
     */
    public static final long WORKER_ID_BITS = 5L;
    public static final long DATA_CENTER_ID_BITS = 5L;
    public static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    public static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 毫秒内自增位
     */
    public static final long SEQUENCE_BITS = 12L;
    /**
     * = 5
     */
    public static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * = 12 + 5 = 17
     */
    public static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * = 17 + +5 + 5 = 22
     */
    public static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    public static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private final long workerId;

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private final long startTime;

    /**
     * 数据标识 ID 部分
     */
    private final long dataCenterId;
    /**
     * 并发控制
     */
    private long no = 0L;
    /**
     * 上次生产 ID 时间戳
     */
    private long lastTimestamp = -1L;

    public Sequence(long startTime) {
        this.dataCenterId = getDataCenterId(MAX_DATA_CENTER_ID);
        this.workerId = getMaxWorkerId(dataCenterId, MAX_WORKER_ID);
        this.startTime = startTime;
    }

    /**
     * 有参构造器
     *
     * @param workerId     工作机器 ID
     * @param dataCenterId 序列号
     */
    public Sequence(long workerId, long dataCenterId, long startTime) {

        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }

        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.startTime = startTime;
    }

    /**
     * 获取 MAX_WORKER_ID
     */
    private static long getMaxWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dataCenterId);

        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotEmpty(name)) {
            /*
             * GET jvmPid
             */
            stringBuilder.append(name.split("@")[0]);
        }

        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (stringBuilder.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 数据标识id部分
     */
    private static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (maxDataCenterId + 1);
                }
            }
        } catch (Exception e) {
            log.warn(" getDataCenterId: {}", e.getMessage());
        }
        return id;
    }

    /**
     * 获取下一个 ID
     *
     * @return 下一个 ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 时钟回退时的处理逻辑
        if (timestamp < lastTimestamp) {

            long offset = lastTimestamp - timestamp;

            // 时钟回退太多时，直接抛出异常
            if (offset > 5) {
                throw new IllegalStateException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }

            // 时钟回退不多，等待小会后再尝试一次
            try {
                // 超时等待，释放cpu后若未在指定时间内被激活则自动激活
                wait(offset << 1);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }

            timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                throw new IllegalStateException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }
        }

        if (lastTimestamp == timestamp) {
            // 相同毫秒内，序列号自增
            no = (no + 1) & SEQUENCE_MASK;
            if (no == 0) {
                // 同一毫秒的序列数已经达到最大
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为 1 - 3 随机数
            no = ThreadLocalRandom.current().nextLong(1, 3);
        }

        lastTimestamp = timestamp;

        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return ((timestamp - startTime) << TIMESTAMP_LEFT_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | no;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return SystemClock.currentTimeMillis();
    }

}