package com.ztax.common.aspect;

import com.ztax.common.exception.BizException;
import com.ztax.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@Slf4j
public class DelAspect {

    @Pointcut("@annotation(com.ztax.common.aspect.DelFill)")
    public void delFillPonitCut() {
    }

    @Before("delFillPonitCut()")
    public void handleExportLimit(JoinPoint point) {
        Object[] args = point.getArgs();
        Object targetObject = args[0];

        if (targetObject instanceof Map) {
            Map map = (Map) targetObject;
            map.put("delFlg", Boolean.TRUE);
        } else {
            try {
                ObjectUtils.setFieldValue(targetObject, "delFlg",Boolean.TRUE);
            } catch (Exception e) {
                log.error("produce delFlg exception", e);
                throw new BizException("处理删除字段异常");
            }

        }
    }
}
