package com.yzm.redis_learn.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 常用命令
 */
@Slf4j
@Component
@EnableScheduling
public class RedisDemo {

    @Autowired
    private RedisUtils redisUtils;

//    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void demo() throws InterruptedException {
        string01();
    }

    private void string01() throws InterruptedException {
        String key = "key";
        redisUtils.set(key, "value");
        redisUtils.expireAt(key, new Date(System.currentTimeMillis() + 60 * 1000));
        log.info(redisUtils.get(key));
        Thread.sleep(30 * 1000);
        log.info(redisUtils.get(key));
        Thread.sleep(31 * 1000);
        log.info(redisUtils.get(key));
    }

    private void string02() throws InterruptedException {
        String key = "key";
        redisUtils.set(key, "1", 10, TimeUnit.SECONDS);
        System.out.println(redisUtils.get(key));
        Thread.sleep(2 * 1000);

        redisUtils.increment(key, 2);
        System.out.println(redisUtils.get(key));
        Thread.sleep(2 * 1000);

        redisUtils.increment(key, -1);
        System.out.println(redisUtils.get(key));
        Thread.sleep(10 * 1000);

        System.out.println(redisUtils.get(key));
    }

    private void list01() throws InterruptedException {
        String key = "list";
        redisUtils.l_leftPushAll(key, "1", "2", "3");
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);

        redisUtils.l_rightPushAll(key, "4", "5", "6");
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);

        redisUtils.l_set(key, 0, "9");
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);

        redisUtils.l_trim(key, 2, 3);
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);

        redisUtils.expire(key, 60 * 1000);
    }

    private void list02() throws InterruptedException {
        String key = "list2";
        redisUtils.l_rightPushAll(key, "1", "2", "3", "1", "2");
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);

        System.out.println(redisUtils.l_index(key, 1));
        Thread.sleep(2 * 1000);

        redisUtils.l_remove(key, "1", 1);
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);

        redisUtils.l_remove(key, "2", 2);
        System.out.println(redisUtils.l_range(key, 0, -1));
        Thread.sleep(2 * 1000);
    }

    private void zSet01() throws InterruptedException {
        String key = "zset1";
        String key2 = "zset2";
        String key3 = "zset7";
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>(
                Arrays.asList(
                        new DefaultTypedTuple<>("C", 0.95),
                        new DefaultTypedTuple<>("D", 0.80),
                        new DefaultTypedTuple<>("F", 0.75),
                        new DefaultTypedTuple<>("G", 0.60),
                        new DefaultTypedTuple<>("E", 0.40)));
//        redisUtils.z_add(key, tuples);
//        System.out.println(redisUtils.z_range(key, 0, -1));
//        Cursor<ZSetOperations.TypedTuple<String>> cursors = redisUtils.z_scan(key, ScanOptions.scanOptions().match("A*").build());
//        while (cursors.hasNext()) {
//            ZSetOperations.TypedTuple<String> next = cursors.next();
//            System.out.println(next.getValue() + "=" + next.getScore());
//        }
        Set<String> set = redisUtils.z_rangeByLex(key,
                RedisZSetCommands.Range.range().gt("AB").lte("AE"),
                RedisZSetCommands.Limit.limit().offset(1).count(1));
        System.out.println("set = " + set);
    }

    private void geo01() throws InterruptedException {
        String key = "city";
        redisUtils.g_add(key, new Point(116.405285D, 39.904989D), "北京");
        redisUtils.g_add(key, new Point(114.055235D, 22.381754D), "深圳");
    }

}
