package love.yefeng.page;

import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import love.yefeng.page.annotation.AutoPage;
import love.yefeng.page.config.PageProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

@Component
@Aspect
@Slf4j
public class PagePoint {

    @Resource
    PageProperties properties;
    @SneakyThrows
    @Around("@annotation(love.yefeng.page.annotation.AutoPage)")
    public Object page(ProceedingJoinPoint pjp) {
        Object res=null;
        Method method = getMethod(pjp);
        AutoPage annotation = method.getAnnotation(AutoPage.class);

        String pageNumKey= properties.getPageNumKey();
        String pageSizeKey= properties.getPageSizeKey();

        Boolean enalbe = properties.getEnalbe();
        if(!enalbe|| !annotation.enableManage()){
            res=pjp.proceed();
            return res;
        }
        PageParam pageParam = getPageParam(pjp, method, annotation, properties);
        if(pageParam==null){
            res=pjp.proceed();
            return res;
        }
        final boolean pageFlag;
        pageFlag=pageParam.pageNum!=null&&pageParam.pageSize!=null;
        try {
            if (pageFlag) {
                PageHelper.startPage(pageParam.pageNum, pageParam.pageSize);
            }
            res=pjp.proceed();
        }catch (Throwable e){
            throw new RuntimeException(e);
        }finally {
            if (pageFlag){
                PageHelper.clearPage();
            }
        }
        return res;
    }


    private static PageParam getPageParam(ProceedingJoinPoint pjp,Method method,AutoPage annotation,PageProperties properties){
        String pageNumKey= properties.getPageNumKey();
        String pageSizeKey= properties.getPageSizeKey();

        if(!annotation.pageNumKey().isEmpty()&&!annotation.pageNumKey().equals(pageNumKey)){
            pageNumKey= annotation.pageNumKey();
        }
        if (!annotation.pageSizeKey().isEmpty()&&!annotation.pageSizeKey().equals(pageSizeKey)){
            pageSizeKey= annotation.pageSizeKey();
        }

        Integer defaultPageSize = Integer.valueOf(annotation.pageSize());
        Parameter[] params = method.getParameters();
        Object[] args = pjp.getArgs();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        Integer pageNum = null;
        Integer pageSize = null;
        //1 从参数中获取分页参数
        for (int i = 0; i < params.length; i++) {
            Parameter param = params[i];
            if (param.getType().equals(Integer.class) && param.getName().equals(pageNumKey)) {
                pageNum = (Integer) args[i];
            }
            if (param.getType().equals(Integer.class) && param.getName().equals(pageSizeKey)) {
                pageSize = (Integer) args[i];
            }
        }
        if(pageNum==null||pageSize==null){
            //2 尝试从请求中获取分页参数
            pageNum = Optional.ofNullable(request.getParameter(pageNumKey)).map(Integer::valueOf).orElse(null);
            pageSize = Optional.ofNullable(request.getParameter(pageSizeKey)).map(Integer::valueOf).orElse(null);
        }
        if(pageNum==null||pageSize==null){
            return null;
        }
        return new PageParam(pageNum,pageSize);
    }


    private static class PageParam{
        Integer pageNum;
        Integer pageSize;
        public PageParam(Integer pageNum, Integer pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
        }
    }
    private static Method getMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = pjp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        return method;
    }


}
