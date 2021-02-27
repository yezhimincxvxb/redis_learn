package com.yzm.redis_learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.redis_learn.entity.User;
import com.yzm.redis_learn.mapper.UserMapper;
import com.yzm.redis_learn.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-02-25
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Cacheable(key = "#root.methodName")
    public List<User> selectAll() {
        List<User> users = baseMapper.selectList(null);
        users.forEach(System.out::println);
        return users;
    }

}
