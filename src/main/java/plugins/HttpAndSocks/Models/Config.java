package plugins.HttpAndSocks.Models;

import java.util.Map;

public class Config {
    private Map<String, PageConfig> pageConfigs;

    public Map<String, PageConfig> getPageConfigs() {
        return pageConfigs;
    }

    public void setPageConfigs(Map<String, PageConfig> pageConfigs) {
        this.pageConfigs = pageConfigs;
    }
}
