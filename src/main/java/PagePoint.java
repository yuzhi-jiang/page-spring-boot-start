import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Aspect
@Slf4j
public class PageInterceptor {
    @SneakyThrows
    @Before("@annotation(com.yefeng.netdisk.myPageUtil.annotation.Page)")
    public void page() {
        System.out.println("进入了注解");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Integer pageNum = Optional.ofNullable(request.getParameter("pageNum")).map(Integer::valueOf).orElse(1);
        Integer pageSize = Optional.ofNullable(request.getParameter("pageSize")).map(Integer::valueOf).orElse(10);

        PageHelper.startPage(pageNum, pageSize);
    }
}
