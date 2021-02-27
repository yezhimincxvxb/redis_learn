package com.yzm.redis_learn.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分布式锁
 */
@Slf4j
@Component
@EnableScheduling
public class RedisLock {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 加锁
     *
     * @param key
     * @param value 当前时间 + 超时时间
     */
    public boolean lock(String key, String value) {
        if (redisUtils.setIfAbsent(key, value)) {
            log.info("加锁");
            return true;
        }

        //解决死锁，且当多个线程同时来时，只会让一个线程拿到锁
        String currentValue = redisUtils.get(key);
        //如果过期
        if (StringUtils.hasLength(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = redisUtils.getAndSet(key, value);
            if (StringUtils.hasLength(oldValue) && oldValue.equals(currentValue)) {
                log.info("已过期，加锁");
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisUtils.get(key);
            if (StringUtils.hasLength(currentValue) && currentValue.equals(value)) {
                log.info("解锁");
                redisUtils.delete(key);
            }
        } catch (Exception e) {
            log.error("【redis锁】解锁失败, {}", e);
        }
    }

    //超时时间10s
    private static final int TIMEOUT = 4000;
    private AtomicInteger count = new AtomicInteger(1);

//    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void demo() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    secKill("1", finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(200);
        }
        log.info("总耗时" + (System.currentTimeMillis() - start));
    }

    public void secKill(String productId, int i) throws InterruptedException {
        long time = System.currentTimeMillis() + TIMEOUT;
        //加锁
        if (!lock(productId, String.valueOf(time))) {
            log.error("人太多了，等会儿再试吧~");
            return;
        }

        //具体的秒杀逻辑
        Thread.sleep(4001);

        //解锁
        unlock(productId, String.valueOf(time));
        log.info(i + "=====秒杀成功==========" + count.getAndIncrement());
    }

}


