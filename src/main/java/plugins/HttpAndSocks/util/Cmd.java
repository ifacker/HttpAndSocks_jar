package plugins.HttpAndSocks.util;

import plugins.HttpAndSocks.Config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Cmd {

    /**
     * 直接运行，没有进行后台运行，所以会在执行的时候卡住
     * @param command 输入的命令
     * @param tabTitle 传入的tabtitle
     */
    public static void run(String command, String tabTitle) {
        try {
            // 创建ProcessBuilder对象并设置要执行的命令
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

            // 重定向错误输出流到标准输出流
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = null;
            process = processBuilder.start();
            if (!tabTitle.isEmpty()){
                Config.mapProcess.put(tabTitle, process);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
//                    appendOutputToTextArea(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            if (exitCode == 1){
                MessageBox.sendSystemInfo("警告", "启动异常，可能是端口是否被占用了\n˃ʍ˂");
            }
            System.out.println("命令执行完毕，退出码：" + exitCode);
        } catch (IOException | InterruptedException e) {
//                appendOutputToTextArea("输入的命令有误，请重试！");
            e.printStackTrace();
        }
    }

    public static void run(String command) {
        run(command, "");
    }

    /**
     * 后台运行，不会卡住
     * @param command 要执行的命令
     * @param tabTitle 输入的tabtitle
     */
    public static void bgRun(String command, String tabTitle) {
        new Thread(()->{
            run(command, tabTitle);
        }).start();
    }


    /**
     * 结束指定的命令行线程
     * @param tabTitle 输入需要结束的ID，tabTitle
     */
    public static void kill(String tabTitle) {
        Config.mapProcess.get(tabTitle).destroy();
        Config.mapProcess.remove(tabTitle);
    }
}
