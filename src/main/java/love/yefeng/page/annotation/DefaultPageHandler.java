package love.yefeng.page.annotation;



import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultPageHandler implements PageHandler<PageInfo> {


    @Override
    public PageInfo solve(Object data1) {

        PageInfo pageInfo = null;
        try {
            List data = (List) data1;
            pageInfo = new PageInfo<>(data);
        } catch (Exception e) {
            throw new RuntimeException("the data is null or the data is not List");
        }
        return pageInfo;
    }
}