package love.yefeng.page;

import love.yefeng.page.config.EnablePageAutoConfig;
import love.yefeng.page.config.PageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PageProperties.class)
@ConditionalOnClass(PageProperties.class)
@ConditionalOnBean(annotation = EnablePageAutoConfig.class)
public class PageAutoConfiguration {

    public PageAutoConfiguration() {
        System.out.println("start PageAutoConfiguration");
    }

    @Bean
    @ConditionalOnMissingBean
    public PageProperties pageProperties(PageProperties properties) {
        return properties;
    }
}
