package org.developtemplate.common.web;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.developtemplate.common.annotation.ResultNotPack;
import org.developtemplate.common.convention.result.Results;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 全局返回值处理器
 *
 * @author jiafu.li
 * @date 2024/11/8
 */
@RestControllerAdvice("org.developtemplate.controller")
@Slf4j
public class GlobalResultHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * 在请求体写入之前执行的逻辑
     *
     * @param o                  要写入的请求体对象
     * @param methodParameter    方法参数信息
     * @param mediaType          媒体类型
     * @param aClass             响应体对象的类
     * @param serverHttpRequest  服务器HTTP请求对象
     * @param serverHttpResponse 服务器HTTP响应对象
     * @return 返回处理后的请求体对象
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 检查方法级别的注解，以决定是否跳过包装
        ResultNotPack methodAnnotation = methodParameter.getMethodAnnotation(ResultNotPack.class);
        // 检查类级别的注解，以决定是否跳过包装
        Annotation classAnnotation = aClass.getAnnotation(ResultNotPack.class);
        // 如果方法或类上有 ResultNotPack 注解，则直接返回原始对象，不进行额外包装
        if (Objects.nonNull(methodAnnotation) || Objects.nonNull(classAnnotation)) {
            return o;
        }
        // 如果对象为空，则返回成功的响应结果
        if (Objects.isNull(o)) {
            return Results.success();
            // 如果对象是 Results 类型，则直接返回，无需额外处理
        } else if (o instanceof Results) {
            return o;
            // 如果对象是字符串类型，则将其作为成功消息包装并序列化为JSON字符串返回
        } else if (o instanceof String) {
            return JSON.toJSONString(Results.success(o));
        }
        // 对于其他类型对象，将其包装为成功响应结果并返回
        return Results.success(o);
    }

}
