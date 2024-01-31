package plugins.HttpAndSocks.util;

import plugins.HttpAndSocks.Config.Config;
import util.FileIO;

public class Save {

    /**
     * 快速保存
     */
    public static void quickSave(){
        Config.modelsConfig.setPageConfigs(Config.mapConfig);
        String content = JacksonYaml.serialization(Config.modelsConfig);
        FileIO.writeFile(Config.configPath, content);
    }
}
