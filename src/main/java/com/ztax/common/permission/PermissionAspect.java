package com.ztax.common.permission;


import com.ztax.common.exception.BizException;
import com.ztax.common.utils.HttpUtils;
import com.ztax.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 权限注解切面
 * 权限校验
 */
@Aspect
@Component
@Order(2)
@Slf4j
public class PermissionAspect {

    @Before("@annotation(Permission)")
    private void before(JoinPoint joinPoint) {
        HttpServletRequest request = HttpUtils.getRequest();
        StringBuffer requestURL = request.getRequestURL();
        log.debug("current request URL:{}", requestURL);

        //暂时不需要判断权限类型，只需校验特殊权限
        //获取方法名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method produceMethod = methodSignature.getMethod();

        RequestMapping requestMappingAnnotation = produceMethod.getAnnotation(RequestMapping.class);
        String[] requestMappings = requestMappingAnnotation.value();
        log.debug("current request mappings :{}", requestMappings);

        //get required permissions
        Permission permissionAnnotation = produceMethod.getAnnotation(Permission.class);
        PermissionTypeEnum permissionTypeEnum = permissionAnnotation.permissionType();
        //根据不同的权限类型，进行处理
        switch (permissionTypeEnum){
            //公司的数据权限
            case DATA_COMPANY:
                //todo 获取请求参数，并对请求参数进行处理（拼接参数）
                break;
            //用户的操作权限
            case OPERATION_USER:
                //获取权限注解上，配置的所需要的权限
                String[] requiredPermissions = permissionAnnotation.value();

                boolean pass = this.validatePermission(requiredPermissions);
                if (!pass) {
                    log.error("operator doesn't have required permissions:{}", requiredPermissions);
                    throw new BizException("权限不足");
                }
                break;
        }

    }

    /**
     * 校验权限
     * 将【注解所在方法需要的权限】和【当前用户拥有的权限】进行对比
     * @param requiedPermissions
     * @return
     */
    public boolean validatePermission(String[] requiedPermissions) {
        if (ObjectUtils.isBlank(requiedPermissions)) {
            log.debug("no permission required of the permission annotation");
            return Boolean.TRUE;
        }

        //get current operator info


        //get current operator permission


        //对比权限
        return true;
    }

}
