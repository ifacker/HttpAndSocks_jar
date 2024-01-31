package plugins.HttpAndSocks.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import plugins.HttpAndSocks.Models.Config;

public class JacksonYaml {
    public static String serialization(Config yamlConfig){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            String yamlString = objectMapper.writeValueAsString(yamlConfig);
//            System.out.println(yamlString);
            return yamlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Config unSerialization(String yamlString){
        yamlString = yamlString.replaceAll("\t", "    ");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            Config yamlConfig = objectMapper.readValue(yamlString, Config.class);
            return yamlConfig;
            // 使用yourObject来处理您的数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
