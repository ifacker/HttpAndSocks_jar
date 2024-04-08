package plugins.HttpAndSocks.Config;

import plugins.HttpAndSocks.Models.PageConfig;

import java.util.HashMap;
import java.util.Map;

public class Config {
    public static  String version = "v1.1.1";
    public static String localhostdef = "localhost";
    public static String portLocaldef = "20000";


    // 可执行文件路径
    public static String execPath = "plugins/HttpAndSocks/exec/";
    // 配置文件存放路径
    public static String configPath = "plugins/HttpAndSocks/config/config.yaml";

    public static plugins.HttpAndSocks.Models.Config modelsConfig = new plugins.HttpAndSocks.Models.Config();
    public static Map<String, PageConfig> mapConfig = new HashMap<>();
    public static Map<String, Process> mapProcess = new HashMap<>();

    public static Map<String, String> githubSrcMap;
    static {
        githubSrcMap = new HashMap<>();
        githubSrcMap.put("HAS-darwin-amd64", "https://raw.githubusercontent.com/ifacker/ToolsKingPluginLib/master/HttpAndSocks/plugin/HAS-darwin-amd64");
        githubSrcMap.put("HAS-darwin-arm64", "https://raw.githubusercontent.com/ifacker/ToolsKingPluginLib/master/HttpAndSocks/plugin/HAS-darwin-arm64");
        githubSrcMap.put("HAS-linux-386", "https://raw.githubusercontent.com/ifacker/ToolsKingPluginLib/master/HttpAndSocks/plugin/HAS-linux-386");
        githubSrcMap.put("HAS-linux-amd64", "https://raw.githubusercontent.com/ifacker/ToolsKingPluginLib/master/HttpAndSocks/plugin/HAS-linux-amd64");
        githubSrcMap.put("HAS-windows-amd64.exe","https://raw.githubusercontent.com/ifacker/ToolsKingPluginLib/master/HttpAndSocks/plugin/HAS-windows-amd64.exe" );
    }

}
