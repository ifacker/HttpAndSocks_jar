package plugins.HttpAndSocks.GUI.Root;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.HttpAndSocks.Config.Config;
import plugins.HttpAndSocks.Control.Exe;
import plugins.HttpAndSocks.Models.PageConfig;
import plugins.HttpAndSocks.util.Cmd;
import plugins.HttpAndSocks.util.MessageBox;
import plugins.HttpAndSocks.util.Save;

public class RootPage {
    // 局部变量
    Label labelVersion; // 现实版本信息
    Button buttonRun;   // 运行按钮
    Button buttonStop; // 停止按钮
    Button buttonSave; // 保存按钮
    Label labelLocal;
    Label labelRemote;
    TextField textFieldLocalPort;
    TextField textFieldLocalUserName;
    TextField textFieldLocalPassword;
    ComboBox<String> comboBoxRemote;
    CheckBox checkBoxLocalGlobal;
    Node nodeRemotePage;
    ComboBox<String> comboBoxRemoteEncode;
    TextField textFieldRemoteURI;
    TextField textFieldRemoteUserName;
    TextField textFieldRemotePassword;
    String tabTitle;
    String localHost = "localhost";

    private Integer run() throws Exception {
        String exe = new Exe().scanHAS();

        String localUri = String.format("%s:%s", localHost, textFieldLocalPort.getText());
        if (!textFieldLocalUserName.getText().isEmpty() && !textFieldLocalPassword.getText().isEmpty()) {
            localUri = String.format("%s:%s@%s", textFieldLocalUserName.getText(), textFieldLocalPassword.getText(), localUri);
        }

        String remoteUP = "";
        if (comboBoxRemote.getValue().equals("ss") && !textFieldRemotePassword.getText().isEmpty()) {
            remoteUP = String.format("%s:%s@", comboBoxRemoteEncode.getValue(), textFieldRemotePassword.getText());
        } else if (!textFieldRemoteUserName.getText().isEmpty() && !textFieldRemotePassword.getText().isEmpty()) {
            remoteUP = String.format("%s:%s@", textFieldRemoteUserName.getText(), textFieldRemotePassword.getText());
        }


        String command = String.format("%s%s -L=%s -F=%s://%s%s", Config.execPath, exe, localUri, comboBoxRemote.getValue(), remoteUP, textFieldRemoteURI.getText());

        MessageBox.sendSystemInfo("提示", String.format("页面:%s\n服务启动中...\n(˵¯͒〰¯͒˵)", tabTitle));
        // 后台运行需要执行的命令
//        Cmd.bgRun(command, tabTitle);
        Cmd cmd = new Cmd();
        cmd.setCommand(command);
        cmd.setTabTitle(tabTitle);
        return cmd.call();
    }

    private void stop() {
        new Thread(() -> {
            Cmd.kill(tabTitle);
            MessageBox.sendSystemInfo("执行状态", "已停止运行\n(˃ ⌑ ˂ഃ )");
        }).start();
    }

    private Node ss() {
        VBox root = new VBox(10);
        HBox sp = new HBox(10);
        root.setAlignment(Pos.CENTER);
        sp.setAlignment(Pos.CENTER);

        comboBoxRemoteEncode.getItems().addAll(
                "aes-128-cfb",
                "aes-192-cfb",
                "aes-256-cfb",
                "aes-128-ctr",
                "aes-192-ctr",
                "aes-256-ctr",
                "aes-128-gcm",
                "aes-192-gcm",
                "aes-256-gcm",
                "bf-cfb",
                "cast5-cfb",
                "des-cfb",
                "chacha20",
                "chacha20-ietf",
                "chacha20-ietf-poly1305",
                "salsa20"
        );
        textFieldRemoteURI.setPromptText("192.168.1.2:8080");
        textFieldRemotePassword.setPromptText("Password");

        textFieldRemoteURI.setMaxWidth(310);
        comboBoxRemoteEncode.setMaxWidth(150);
        textFieldRemotePassword.setMaxWidth(150);

        sp.getChildren().addAll(comboBoxRemoteEncode, textFieldRemotePassword);

        root.getChildren().addAll(textFieldRemoteURI, sp);

        return root;
    }

    private Node socks5AndHttp() {
        VBox root = new VBox(10);
        HBox up = new HBox(10);
        root.setAlignment(Pos.CENTER);
        up.setAlignment(Pos.CENTER);

        textFieldRemoteURI.setPromptText("192.168.1.2:8080");
        textFieldRemoteUserName.setPromptText("UserName");
        textFieldRemotePassword.setPromptText("Password");

        textFieldRemoteURI.setMaxWidth(310);
        textFieldRemoteUserName.setMaxWidth(150);
        textFieldRemotePassword.setMaxWidth(150);

        up.getChildren().addAll(textFieldRemoteUserName, textFieldRemotePassword);

        root.getChildren().addAll(textFieldRemoteURI, up);

        return root;
    }

    private Node remotePage(String scheme) {
        VBox root = new VBox(10);

        Node node;

        if (scheme.equals("ss")) {
            node = ss();
        } else {
            node = socks5AndHttp();
        }

        root.getChildren().addAll(node);
        return root;
    }

    // 中心的内容
    private Node center(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        labelLocal = new Label("本地监听端口（混合端口 http/socks5）：");
        textFieldLocalPort.setMaxWidth(75);
        HBox hBoxUP = new HBox(10);
        hBoxUP.setAlignment(Pos.CENTER);
        textFieldLocalUserName.setPromptText("UserName");
        textFieldLocalUserName.setMaxWidth(150);
        textFieldLocalPassword.setPromptText("Password");
        textFieldLocalPassword.setMaxWidth(150);
        hBoxUP.getChildren().addAll(textFieldLocalUserName, textFieldLocalPassword);

        // 分割线
        Separator separator = new Separator();

        HBox hBoxRemote = new HBox(10);
        hBoxRemote.setAlignment(Pos.CENTER);
        labelRemote = new Label("远程监听：");
        comboBoxRemote.getItems().addAll("socks5", "http", "ss");
        comboBoxRemote.setOnAction(event -> {
            Platform.runLater(() -> {
                nodeRemotePage = remotePage(comboBoxRemote.getValue());
                root.getChildren().clear();
                root.getChildren().addAll(labelLocal, textFieldLocalPort, hBoxUP, checkBoxLocalGlobal, separator, hBoxRemote, nodeRemotePage);
                buttonSave.requestFocus();
            });
        });
        hBoxRemote.getChildren().addAll(labelRemote, comboBoxRemote);

        nodeRemotePage = remotePage(comboBoxRemote.getValue());

        root.getChildren().addAll(labelLocal, textFieldLocalPort, hBoxUP, checkBoxLocalGlobal, separator, hBoxRemote, nodeRemotePage);

        return root;
    }

    // 底部的内容
    private Node bottom(Stage primaryStage, String name) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setPadding(new Insets(100, 10, 10, 10));

        // 显示版本信息
        labelVersion = new Label(String.format("version: %s", Config.version));

        buttonRun = new Button("运行");
        buttonStop = new Button("停止");
        buttonStop.setDisable(true);
        buttonSave = new Button("保存配置");

        buttonRun.setMinWidth(100);
        buttonStop.setMinWidth(100);
        buttonSave.setMinWidth(100);

        buttonRun.setOnAction(event -> {
            buttonRun.setDisable(true);
            buttonStop.setDisable(false);
            new Thread(() -> {
                try {
                    run();
                } catch (Exception e) {
                    buttonStop.setDisable(true);
                    buttonRun.setDisable(false);
                    throw new RuntimeException(e);
                }
            }).start();
        });
        buttonStop.setOnAction(event -> {
            buttonStop.setDisable(true);
            buttonRun.setDisable(false);
            stop();
        });
        buttonSave.setOnAction(event -> {
            PageConfig pageConfig = new PageConfig();
            if (checkBoxLocalGlobal.isSelected()) {
                localHost = "0.0.0.0";
            } else {
                localHost = "localhost";
            }
            pageConfig.setLocalhost(localHost);
            pageConfig.setLocalport(textFieldLocalPort.getText());
            pageConfig.setLocalUserName(textFieldLocalUserName.getText());
            pageConfig.setLocalPassword(textFieldLocalPassword.getText());
            pageConfig.setRemoteScheme(comboBoxRemote.getValue());
            pageConfig.setRemoteUri(textFieldRemoteURI.getText());
            if (comboBoxRemote.getValue().equals("ss")) {
                pageConfig.setRemoteUserName(comboBoxRemoteEncode.getValue());
            } else {
                pageConfig.setRemoteUserName(textFieldRemoteUserName.getText());
            }
            pageConfig.setRemotePassword(textFieldRemotePassword.getText());
            Config.mapConfig.put(name, pageConfig);
            Save.quickSave();
            MessageBox.sendSystemInfo("保存提示", String.format("页面%s已更新\n(*´I`*)", name));
        });

        hBox.getChildren().addAll(buttonRun, buttonStop, buttonSave);

        VBox root = new VBox(10);
        root.setPadding(new Insets(0, 0, 10, 10));
        root.setAlignment(Pos.BOTTOM_LEFT);
        root.getChildren().addAll(hBox, labelVersion);

        return root;
    }


    /**
     * 初始化
     */
    public RootPage(String tabTitle) {
        this.tabTitle = tabTitle;
        textFieldLocalPort = new TextField();
        textFieldLocalUserName = new TextField();
        textFieldLocalPassword = new TextField();
        checkBoxLocalGlobal = new CheckBox("允许局域网内其他设备连接");
        comboBoxRemote = new ComboBox<>();
        comboBoxRemoteEncode = new ComboBox<>();
        textFieldRemoteURI = new TextField();
        textFieldRemoteUserName = new TextField();
        textFieldRemotePassword = new TextField();

        if (Config.mapConfig.isEmpty() || !Config.mapConfig.containsKey(tabTitle)) {
            textFieldLocalPort.setText(Config.portLocaldef);
            comboBoxRemote.setValue("socks5");
            comboBoxRemoteEncode.setValue("aes-128-cfb");
        } else {
            textFieldLocalPort.setText(Config.mapConfig.get(tabTitle).getLocalport());
            textFieldLocalUserName.setText(Config.mapConfig.get(tabTitle).getLocalUserName());
            textFieldLocalPassword.setText(Config.mapConfig.get(tabTitle).getLocalPassword());
            checkBoxLocalGlobal.setSelected(Config.mapConfig.get(tabTitle).getLocalhost().equals("0.0.0.0"));
            comboBoxRemote.setValue(Config.mapConfig.get(tabTitle).getRemoteScheme());
            textFieldRemoteURI.setText(Config.mapConfig.get(tabTitle).getRemoteUri());
            if (comboBoxRemote.getValue().equals("ss")) {
                comboBoxRemoteEncode.setValue(Config.mapConfig.get(tabTitle).getRemoteUserName());
            } else {
                textFieldRemoteUserName.setText(Config.mapConfig.get(tabTitle).getRemoteUserName());
            }
            textFieldRemotePassword.setText(Config.mapConfig.get(tabTitle).getRemotePassword());
        }
    }

    public Node show(Stage primaryStage) {

        // 创建 layout
        BorderPane root = new BorderPane();

        // center
        root.setCenter(center(primaryStage));

        // bottom
        root.setBottom(bottom(primaryStage, tabTitle));

        return root;
    }
}
