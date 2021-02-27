package com.yzm.redis_learn.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 删除key，返回成功与否
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    // 是否存在key
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    // 设置超时时间，单位秒
    public Boolean expire(String key, long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    // 设置超时时间，date对象
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    // 获取过期时间，单位秒
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    // 获取过期时间，转换成对应的时间单位
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    // 根据匹配规则获取key集合
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    // ===============================================================================
    // String：设置 键值对
    public void set(String key, String var) {
        redisTemplate.opsForValue().set(key, var);
    }

    // String：设置 键值对 并设置过期时间
    public void set(String key, String var, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, var, time, timeUnit);
    }

    // String：设置 键值对，当key不存在才创建设值
    public Boolean setIfAbsent(String key, String var) {
        return redisTemplate.opsForValue().setIfAbsent(key, var);
    }

    // String：设置 键值对，当key存在才创建设值
    public Boolean setIfPresent(String key, String var) {
        return redisTemplate.opsForValue().setIfPresent(key, var);
    }

    // String：获取key对应的value值
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // String：获取key对应的value值
    public String getAndSet(String key, String var) {
        return redisTemplate.opsForValue().getAndSet(key, var);
    }

    // String：对key进行自增(正数)、自减(负数)
    public Long increment(String key, long num) {
        return redisTemplate.opsForValue().increment(key, num);
    }

    // ===============================================================================
    // List：左插入
    public Long l_leftPush(String key, String var) {
        return redisTemplate.opsForList().leftPush(key, var);
    }

    // List：左插入(批量)
    public Long l_leftPushAll(String key, String... vars) {
        return redisTemplate.opsForList().leftPushAll(key, vars);
    }

    // List：左插入(批量)
    public Long l_leftPushAll(String key, Collection<String> vars) {
        return redisTemplate.opsForList().leftPushAll(key, vars);
    }

    // List：右插入
    public Long l_rightPush(String key, String var) {
        return redisTemplate.opsForList().rightPush(key, var);
    }

    // List：右插入(批量)
    public Long l_rightPushAll(String key, String... vars) {
        return redisTemplate.opsForList().rightPushAll(key, vars);
    }

    // List：右插入(批量)
    public Long l_rightPushAll(String key, Collection<String> vars) {
        return redisTemplate.opsForList().rightPushAll(key, vars);
    }

    // List：获取下标[,]之间的元素[0,-1]获取所有
    public List<String> l_range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // List：修改下标index的元素
    public void l_set(String key, long index, String var) {
        redisTemplate.opsForList().set(key, index, var);
    }

    // List：保留下标[,]之间的元素，其余移除
    public void l_trim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    // List：获取元素个数
    public Long l_size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    // List：根据下标获取元素
    public String l_index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    // List：集合中有多个相同的元素，删除一个或多个相同的元素
    public Long l_remove(String key, Object var, long count) {
        return redisTemplate.opsForList().remove(key, count, var);
    }

    // List：左弹出头部的一个元素，集合元素减少
    public String l_leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    // List：右弹出尾部的一个元素，集合元素减少
    public String l_rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    // List：右弹出尾部的一个元素，集合元素减少
    public String l_rightPopAndLeftPush(String fromKey, String toKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(fromKey, toKey);
    }

    // ===============================================================================
    // Hash：添加元素
    public void h_put(String key, Object hkey, Object hvar) {
        redisTemplate.opsForHash().put(key, hkey, hvar);
    }

    // Hash：添加元素
    public void h_putAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    // Hash：添加元素
    public Boolean h_putIfAbsent(String key, Object hkey, Object hvar) {
        return redisTemplate.opsForHash().putIfAbsent(key, hkey, hvar);
    }

    // Hash：获取元素
    public Object h_get(String key, String hkey) {
        return redisTemplate.opsForHash().get(key, hkey);
    }

    // Hash：批量获取元素
    public List<Object> h_multiGet(String key, Collection<Object> hkeys) {
        return redisTemplate.opsForHash().multiGet(key, hkeys);
    }

    // Hash：元素个数
    public Long h_size(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    // Hash：删除元素
    public Long h_delete(String key, Object... hkeys) {
        return redisTemplate.opsForHash().delete(key, hkeys);
    }

    // Hash：判断元素存在
    public Boolean h_hasKey(String key, Object hkey) {
        return redisTemplate.opsForHash().hasKey(key, hkey);
    }

    // Hash：元素增减变量
    public Long h_increment(String key, Object hkey, long num) {
        return redisTemplate.opsForHash().increment(key, hkey, num);
    }

    // Hash：获取hash的所有键Hkey
    public Set<Object> h_keys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    // Hash：获取hash的所有值HValue
    public List<Object> h_values(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    // Hash：获取hash的所有键值对
    public Map<Object, Object> h_entries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // Hash：根据HKey匹配规则搜索
    public Cursor<Map.Entry<Object, Object>> h_scan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    // ===============================================================================
    // Set：添加元素
    public Long s_add(String key, String... vars) {
        return redisTemplate.opsForSet().add(key, vars);
    }

    // Set：获取元素
    public Set<String> s_members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // Set：随机获取一个元素
    public String s_randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    // Set：随机获取多个元素(会重复)
    public List<String> s_randomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    // Set：随机获取多个元素(不会重复)
    public Set<String> s_distinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    // Set：移除元素
    public Long s_remove(String key, Object... vars) {
        return redisTemplate.opsForSet().remove(key, vars);
    }

    // Set：随机弹出一个元素(set集合元素减少)
    public String s_pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    // Set：随机弹出count个元素
    public List<String> s_pop(String key, long count) {
        return redisTemplate.opsForSet().pop(key, count);
    }

    // Set：从一个Set集合移出元素到另一个集合
    public Boolean s_move(String fromKey, String var, String toKey) {
        return redisTemplate.opsForSet().move(fromKey, var, toKey);
    }

    // Set：元素个数
    public Long s_size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    // Set：判断元素是否属于set集合
    public Boolean s_isMember(String key, String var) {
        return redisTemplate.opsForSet().isMember(key, var);
    }

    // Set：两个set集合的交集
    public Set<String> s_intersect(String key1, String key2) {
        return redisTemplate.opsForSet().intersect(key1, key2);
    }

    public Set<String> s_intersect(String key1, Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(key1, keys);
    }

    // Set：两个set集合的交集，并存储在key3
    public Long s_intersectAndStore(String key1, String key2, String key3) {
        return redisTemplate.opsForSet().intersectAndStore(key1, key2, key3);
    }

    public Long s_intersectAndStore(String key1, Collection<String> key2, String key3) {
        return redisTemplate.opsForSet().intersectAndStore(key1, key2, key3);
    }

    // Set：两个set集合的合集
    public Set<String> s_union(String key1, String key2) {
        return redisTemplate.opsForSet().union(key1, key2);
    }

    // Set：多个set集合的合集
    public Set<String> s_union(String key1, Collection<String> keys) {
        return redisTemplate.opsForSet().union(key1, keys);
    }

    // Set：两个set集合的合集并存储到key3
    public Long s_unionAndStore(String key1, String key2, String key3) {
        return redisTemplate.opsForSet().unionAndStore(key1, key2, key3);
    }

    // Set：多个set集合的合集并存储到key3
    public Long s_unionAndStore(String key1, Collection<String> keys, String key3) {
        return redisTemplate.opsForSet().unionAndStore(key1, keys, key3);
    }

    // Set：两个set集合的差集
    public Set<String> s_difference(String key1, String key2) {
        return redisTemplate.opsForSet().difference(key1, key2);
    }

    // Set：多个set集合的差集
    public Set<String> s_difference(String key1, Collection<String> keys) {
        return redisTemplate.opsForSet().difference(key1, keys);
    }

    // Set：两个set集合的差集并存储到key3
    public Long s_differenceAndStore(String key1, String key2, String key3) {
        return redisTemplate.opsForSet().differenceAndStore(key1, key2, key3);
    }

    // Set：多个集合的差集并存储到key3
    public Long s_differenceAndStore(String key1, Collection<String> keys, String key3) {
        return redisTemplate.opsForSet().differenceAndStore(key1, keys, key3);
    }

    // ZSet：根据value匹配规则获取
    // ScanOptions.scanOptions().match("A*").build()
    public Cursor<String> s_scan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    // ===============================================================================
    // ZSet：添加单个元素
    public Boolean z_add(String key, String var, double score) {
        return redisTemplate.opsForZSet().add(key, var, score);
    }

    // ZSet：添加多个元素
    public Long z_add(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    // ZSet：移除元素
    public Long z_remove(String key, Object... vars) {
        return redisTemplate.opsForZSet().remove(key, vars);
    }

    // ZSet：对指定value的分数进行增减
    public Double z_incrementScore(String key, String var, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, var, score);
    }

    // ZSet：获取下标(score正序:从小到大)
    public Long z_rank(String key, Object var) {
        return redisTemplate.opsForZSet().rank(key, var);
    }

    // ZSet：获取下标(score倒序：从大到小)
    public Long z_reverseRank(String key, Object var) {
        return redisTemplate.opsForZSet().reverseRank(key, var);
    }

    // ZSet：获取下标区间[start,end]的元素value值(score正序)
    public Set<String> z_range(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    // ZSet：获取下标区间[start,end]的元素value值和score分数(score正序)
    public Set<ZSetOperations.TypedTuple<String>> z_rangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    // ZSet：获取分数区间[min，max]的元素value值(score正序)
    public Set<String> z_rangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    // ZSet：先获取分数区间[min，max]的元素，再偏移(跳过)offset个元素，最后只拿取count个元素(只有value值、score正序)
    public Set<String> z_rangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    // ZSet：获取分数区间[min，max]的元素value值和score分数(score正序)
    public Set<ZSetOperations.TypedTuple<String>> z_rangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    // ZSet：先获取分数区间[min，max]的元素，再偏移(跳过)offset个元素，最后只拿取count个元素(value值和score分数、score正序)
    public Set<ZSetOperations.TypedTuple<String>> z_rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    // ZSet：获取下标区间[start,end]的元素value值(score倒序)
    public Set<String> z_reverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    // ZSet：获取下标区间[start,end]的元素value值和score分数(score倒序)
    public Set<ZSetOperations.TypedTuple<String>> z_reverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    // ZSet：获取分数区间[min，max]的元素value值(score倒序)
    public Set<String> z_reverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    // ZSet：先获取分数区间[min，max]的元素，再偏移(跳过)offset个元素，最后只拿取count个元素(只有value值、score倒序)
    public Set<String> z_reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    // ZSet：获取分数区间[min，max]的元素value值和score分数(score倒序)
    public Set<ZSetOperations.TypedTuple<String>> z_reverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    // ZSet：先获取分数区间[min，max]的元素，再偏移(跳过)offset个元素，最后只拿取count个元素(value值和score分数、score倒序)
    public Set<ZSetOperations.TypedTuple<String>> z_reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    // ZSet：分数在[min,max]的个数
    public Long z_count(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    // ZSet：元素总数
    public Long z_size(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    // ZSet：获取value值的分数
    public Double z_score(String key, Object var) {
        return redisTemplate.opsForZSet().score(key, var);
    }

    // ZSet：删除下标在[start,end]区间的元素
    public Long z_removeRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    // ZSet：删除分数在[min,max]区间的元素
    public Long z_removeRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    // ZSet：key1和key2的合集并存储到key3
    public Long z_unionAndStore(String key, String key2, String key3) {
        return redisTemplate.opsForZSet().unionAndStore(key, key2, key3);
    }

    // ZSet：key1跟多个key的合集并存储到key3
    public Long z_unionAndStore(String key, Collection<String> keys, String key3) {
        return redisTemplate.opsForZSet().unionAndStore(key, keys, key3);
    }

    /**
     * ZSet：key1跟多个key的合集并存储到key3
     * Aggregate参数有：MIN(相同元素的score取最小值的)、MAX(最大值的)、RedisZSetCommands.Aggregate.SUN(累加的)
     */
    public Long z_unionAndStore(String key, Collection<String> keys, String key3, RedisZSetCommands.Aggregate agg) {
        return redisTemplate.opsForZSet().unionAndStore(key, keys, key3, agg);
    }

    // ZSet：key1和key2的交集并存储到key3
    public Long z_intersectAndStore(String key, String key2, String key3) {
        return redisTemplate.opsForZSet().intersectAndStore(key, key2, key3);
    }

    // ZSet：key1和多个key的交集并存储到key3
    public Long z_intersectAndStore(String key, Collection<String> keys, String key3) {
        return redisTemplate.opsForZSet().intersectAndStore(key, keys, key3);
    }

    // ZSet：key1和多个key的交集并存储到key3
    public Long z_intersectAndStore(String key, Collection<String> keys, String key3, RedisZSetCommands.Aggregate agg) {
        return redisTemplate.opsForZSet().intersectAndStore(key, keys, key3, agg);
    }

    // ZSet：根据value匹配规则获取value和score
    // ScanOptions.scanOptions().match("A*").build()
    public Cursor<ZSetOperations.TypedTuple<String>> z_scan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

    // ZSet：前提条件，必须score相同，获取选定范围的元素
    // RedisZSetCommands.Range.range().gt("AB").lte("AE")
    public Set<String> z_rangeByLex(String key, RedisZSetCommands.Range range) {
        return redisTemplate.opsForZSet().rangeByLex(key, range);
    }

    // RedisZSetCommands.Limit.limit().offset(1).count(1) 跳过前面几个，最后拿取几个
    public Set<String> z_rangeByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        return redisTemplate.opsForZSet().rangeByLex(key, range, limit);
    }

    // ===============================================================================
    // Geo：添加元素
    public Long g_add(String k, Point p, String m) {
        return redisTemplate.opsForGeo().add(k, p, m);
    }

    // Geo：添加多个元素
    public Long g_add(String k, RedisGeoCommands.GeoLocation<String> vars) {
        return redisTemplate.opsForGeo().add(k, vars);
    }

    // Geo：添加多个元素
    public Long g_add(String k, Map<String, Point> vars) {
        return redisTemplate.opsForGeo().add(k, vars);
    }

    // Geo：两地距离(Metric,单位米、千米等)
    public Distance g_distance(String k, String var1, String var2, Metric metric) {
        return redisTemplate.opsForGeo().distance(k, var1, var2, metric);
    }

    // Geo：经纬度
    public List<Point> g_position(String k, String... vars) {
        return redisTemplate.opsForGeo().position(k, vars);
    }


}
