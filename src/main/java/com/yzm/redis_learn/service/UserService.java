package com.yzm.redis_learn.service;

import com.yzm.redis_learn.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-02-25
 */
public interface UserService extends IService<User> {

    List<User> selectAll();

}
