package org.developtemplate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.developtemplate.common.annotation.SystemEventLog;
import org.developtemplate.common.convention.result.Result;
import org.developtemplate.common.convention.result.Results;
import org.developtemplate.common.enums.SystemLogTypeEnum;
import org.developtemplate.domain.dto.request.UserLoginRequestDTO;
import org.developtemplate.domain.dto.request.UserRegisterRequestDTO;
import org.developtemplate.domain.dto.request.UserUpdateRequestDTO;
import org.developtemplate.domain.dto.response.UserInfoResponseDTO;
import org.developtemplate.domain.dto.response.UserLoginResponseDTO;
import org.developtemplate.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/2
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 注册用户
     *
     * @param userRegisterRequestDTO 用户注册请求参数
     */
    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO){
        userService.registerUser(userRegisterRequestDTO);
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     */
    @GetMapping("/check")
    public Boolean checkUsername(@RequestParam("username") String username) {
        return userService.checkUsername(username);
    }

    /**
     * 登录用户
     *
     * @param userLoginRequestDTO 用户登录请求参数
     */
    @SystemEventLog(SystemLogTypeEnum.DEFAULT)
    @PostMapping("/login")
    public UserLoginResponseDTO loginUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        return userService.loginUser(userLoginRequestDTO);
    }

    /**
     * 查询用户
     * @param username 用户名
     */
    @GetMapping("/{username}")
    public UserInfoResponseDTO getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * 修改用户
     * @param requestParam 用户更新请求参数
     */
    @SystemEventLog(SystemLogTypeEnum.USER_UPDATE)
    @PutMapping
    public void updateUser(@RequestBody UserUpdateRequestDTO requestParam) {
        userService.updateUser(requestParam);
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/check-login")
    public Boolean checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        return userService.checkLogin(username, token);
    }

    /**
     * 用户退出登录
     */
    @DeleteMapping("/logout")
    public void logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        userService.logoutUser(username, token);
    }

}
