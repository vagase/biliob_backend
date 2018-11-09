package com.jannchie.biliob.service;

import com.jannchie.biliob.exception.UserAlreadyFavoriteAuthorException;
import com.jannchie.biliob.exception.UserAlreadyFavoriteVideoException;
import com.jannchie.biliob.model.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author jannchie
 */
@Service
public interface UserService {

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 用户
     */
    User createUser(User user);

    /**
     * 获取密码
     *
     * @param name 用户名
     * @return 密码
     */
    String getPassword(String name);

    /**
     * 获取角色
     *
     * @param username 用户名
     * @return 角色名
     */
    String getRole(String username);

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    User getUserInfo();

    /**
     * 添加作者关注
     * @param mid 作者id
     * @return 用户
     * @throws UserAlreadyFavoriteAuthorException 用户已关注作者异常
     */
    User addFavoriteAuthor(@Valid Long mid) throws UserAlreadyFavoriteAuthorException;

    /**
     * 添加视频关注
     * @param aid 视频id
     * @return 用户
     * @throws UserAlreadyFavoriteVideoException 用户已关注视频异常
     */
    User addFavoriteVideo(@Valid Long aid) throws UserAlreadyFavoriteVideoException;
}