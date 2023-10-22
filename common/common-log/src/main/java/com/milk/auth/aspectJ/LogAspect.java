package com.milk.auth.aspectJ;

import com.alibaba.fastjson.JSON;
import com.milk.auth.annotation.Log;
import com.milk.auth.service.AsyncOperLogService;
import com.milk.common.IpUtils;
import com.milk.common.RequestUtils;
import com.milk.common.TokenUtils;
import com.milk.model.pojo.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 11:08
 */

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private AsyncOperLogService asyncOperLogService;


    @AfterReturning(pointcut = "@annotation(contrLog)" ,returning = "jsonResult")
    public void doAfterReturn(JoinPoint joinPoint, Log contrLog,Object jsonResult){

        handleLog(joinPoint,contrLog,jsonResult,null);

    }

    @AfterThrowing(pointcut = "@annotation(contrLog)",throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint,Log contrLog,Exception e){
        handleLog(joinPoint,contrLog,null,e);
    }

    protected void handleLog(JoinPoint joinPoint, Log contrLog, Object jsonResult,Exception e) {

        try {
            HttpServletRequest request = RequestUtils.getRequest();
            String token = request.getHeader("token");
            String username = TokenUtils.getUsername(token);

            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setStatus(1);
            sysOperLog.setOperIp(IpUtils.getIpAddress(request));
            sysOperLog.setOperUrl(request.getRequestURI());
            sysOperLog.setOperName(username);

            if (e!=null){
                sysOperLog.setErrorMsg(e.getMessage());
                sysOperLog.setStatus(0);
            }

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            sysOperLog.setMethod(className+"."+methodName+"()");

            sysOperLog.setRequestMethod(request.getMethod());

            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, contrLog, sysOperLog, jsonResult);

            asyncOperLogService.saveOperLog(sysOperLog);


        }catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }

    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().name());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().name());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && !StringUtils.isEmpty(jsonResult)) {
            operLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(params);
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
