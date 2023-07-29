package love.yefeng.page.annotation;

import java.lang.annotation.*;

import static love.yefeng.page.config.PageProperties.DEFAULT_PAGE_NUM_KEY;
import static love.yefeng.page.config.PageProperties.DEFAULT_PAGE_SIZE_KEY;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoPage {
    boolean enableManage() default true;

    String pageNumKey() default "";
    String pageSizeKey() default "";

    String pageSize() default "10";

    Class<? extends PageHandler<?>> clazz() default DefaultPageHandler.class;
}
