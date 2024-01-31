package plugins.HttpAndSocks;

import Plugin.Plugin;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.HttpAndSocks.Config.Config;
import plugins.HttpAndSocks.GUI.Root.RootPage;
import plugins.HttpAndSocks.util.Cmd;
import util.FileIO;
import plugins.HttpAndSocks.util.JacksonYaml;
import plugins.HttpAndSocks.util.MessageBox;
import plugins.HttpAndSocks.util.Save;


public class Main implements Plugin {
    @Override
    public String getName() {
        return "HttpAndSocks";
    }

    @Override
    public Node getContent(Stage primaryStage) {
        // 创建插件的内容
        VBox content = new VBox();
        // 顶部
        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(25);

        // 加载之前保存的配置
        loadConfig();

        // 添加 tab
        Tab tabAdd = new Tab("+");
        tabAdd.setClosable(false);
        tabPane.getTabs().addAll(tabAdd);

        // tabAdd 的触发事件
        tabAdd.setOnSelectionChanged(event -> {
            if (tabAdd.isSelected()) {
                addNewTab(primaryStage, tabPane);
            }
        });

        if (!Config.mapConfig.isEmpty()) {
            // 初始化之前的配置文件
            Config.mapConfig.forEach((key, value) -> {
                addNewTab(primaryStage, tabPane, key);
            });
        } else {
            addNewTab(primaryStage, tabPane);
        }

        // 关闭主程序前触发的事件
//        primaryStage.setOnCloseRequest(event -> {
//            // 遍历并结束所有进程
//            Config.mapProcess.forEach((key, value) -> {
//                System.out.printf("正在关闭服务：%s\n", key);
//                value.destroy();
//            });
//        });

        content.getChildren().addAll(tabPane);

        return content;
    }

    int index = 0;

    Boolean found;

    private void addNewTab(Stage primaryStage, TabPane tabPane) {
        String name = String.format("    %d    ", index++);
        found = false;
        // 为了去除重复名字
        Config.mapConfig.forEach((key, value) -> {
            if (name.equals(key)) {
                found = true;
            }
        });
        if (found) {
            addNewTab(primaryStage, tabPane);
            return;
        }
        addNewTab(primaryStage, tabPane, name);
    }

    // 保持打开
    Boolean status;

    private void addNewTab(Stage primaryStage, TabPane tabPane, String name) {
        Tab tab1 = new Tab(name);
        tab1.setContent(new RootPage(tab1.getText()).show(primaryStage));
        tab1.setOnCloseRequest(event -> {
            status = false;
            if (Config.mapProcess.containsKey(tab1.getText())) {
                // 阻止默认的关闭行为
                event.consume();
                MessageBox.showConfirmation("提示", "是否关闭代理通道？", resp -> {
                    if (resp == ButtonType.OK) {
                        Cmd.kill(tab1.getText());
                        tabPane.getTabs().remove(tab1); // 手动从TabPane中移除选项卡
                    } else {
                        tabPane.getSelectionModel().select(tab1); // 选择当前选项卡，保持打开状态
                        status = true;
                    }
                    if (!status) {
                        Config.mapConfig.remove(tab1.getText());
                        Save.quickSave();
                        MessageBox.sendSystemInfo("关闭提示", String.format("页面%s的配置已从配置文件中删除\n(ㅇㅅㅇ❀)", tab1.getText()));
                    }
                });
            } else{
                Config.mapConfig.remove(tab1.getText());
                Save.quickSave();
                MessageBox.sendSystemInfo("关闭提示", String.format("页面%s的配置已从配置文件中删除\n(ㅇㅅㅇ❀)", tab1.getText()));
            }

        });

        tabPane.getTabs().add(tab1);
        tabPane.getSelectionModel().select(tab1); // 选中新添加的标签页
    }

    private void loadConfig() {
        if (FileIO.isFile(Config.configPath)) {
            String content = FileIO.readFile(Config.configPath);
            Config.modelsConfig = JacksonYaml.unSerialization(content);
//            if (Config.modelsConfig != null && !Config.modelsConfig.getPageConfigs().isEmpty()) {
            if (Config.modelsConfig != null) {
                Config.mapConfig = Config.modelsConfig.getPageConfigs();
            }
        }
    }
}
