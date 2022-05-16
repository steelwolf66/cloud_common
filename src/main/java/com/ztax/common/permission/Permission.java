package com.ztax.common.permission;

import java.lang.annotation.*;

/**
 * 权限注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Permission {

    PermissionTypeEnum permissionType() default PermissionTypeEnum.OPERATION_USER;

    /**
     * 允许配置多个权限，与的关系
     * eg: iam:user:delete
     *
     * @return
     */
    String[] value() default "";

    String column() default "";
}
