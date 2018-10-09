package com.riyeyuedu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author haojie tang
 * @date 2018/9/27 10:55
 */
@Aspect
@Component
public class AdminAspect {
    private final static Logger logger = LoggerFactory.getLogger(AdminAspect.class);


    @Pointcut("execution(public * com.riyeyuedu.controller.AdminController.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBeafore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //分割线
        logger.info("================================================================================");

        //url
        logger.info("url={}", request.getRequestURI());
        //method
        logger.info("method={}", request.getMethod());
        //ip
        logger.info("ip={}", request.getRemoteAddr());
        //method
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //param
        logger.info("args={}", joinPoint.getArgs());
        //分割线
        logger.info("========================================");
    }

    @After("log()")
    public void doAfter() {
        //分割线
        logger.info("========================================");
    }

    @AfterReturning(returning="obj", pointcut = "log()")
    public void doAfterReturnig(Object obj){
        logger.info("reponse={}", obj);
        logger.info("================================================================================");
    }
}
