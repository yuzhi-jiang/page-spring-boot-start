package love.yefeng.page.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@ConfigurationProperties(prefix = "pagehelper-plus")
public class PageProperties {

    public static final String DEFAULT_PAGE_NUM_KEY="pageNum";
    public static final String DEFAULT_PAGE_SIZE_KEY="pageSize";

    /**
     * 是否启用
     */
    Boolean enalbe=true;

    String pageNumKey= DEFAULT_PAGE_NUM_KEY;
    String pageSizeKey= DEFAULT_PAGE_SIZE_KEY;

    public Boolean getEnalbe() {
        return enalbe;
    }

    public void setEnalbe(Boolean enalbe) {
        this.enalbe = enalbe;
    }

    public void setPageNumKey(String pageNumKey) {
        this.pageNumKey = pageNumKey;
    }

    public void setPageSizeKey(String pageSizeKey) {
        this.pageSizeKey = pageSizeKey;
    }

    public String getPageNumKey() {
        return pageNumKey;
    }

    public String getPageSizeKey() {
        return pageSizeKey;
    }
}
