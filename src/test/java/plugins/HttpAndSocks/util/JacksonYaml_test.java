package plugins.HttpAndSocks.util;

import plugins.HttpAndSocks.Models.Config;
import plugins.HttpAndSocks.Models.PageConfig;

import java.util.HashMap;
import java.util.Map;

public class JacksonYaml_test {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test1(){
        Config config = new Config();

        Map<String, PageConfig> maps = new HashMap<>();

        PageConfig pageConfig = new PageConfig();
        pageConfig.setLocalhost("localhost");
        pageConfig.setLocalport("20000");
        pageConfig.setRemoteScheme("ss");
        pageConfig.setRemoteUri("192.168.1.2:8080");
        pageConfig.setRemoteUserName("abc");
        pageConfig.setRemotePassword("def");

        maps.put("1", pageConfig);

        config.setPageConfigs(maps);

        String result = JacksonYaml.serialization(config);
        System.out.println(result);
    }

    private static void test2(){
        String content = "---\npageConfigs:\n" +
                "  \"1\":\n" +
                "    localhost: \"localhost\"\n" +
                "    localport: \"20000\"\n" +
                "    localUserName: null\n" +
                "    localPassword: null\n" +
                "    remoteScheme: \"ss\"\n" +
                "    remoteUri: \"192.168.1.2:8080\"\n" +
                "    remoteUserName: \"abc\"\n" +
                "    remotePassword: \"def\"";

        Config config = JacksonYaml.unSerialization(content);
        System.out.println();
    }
}
