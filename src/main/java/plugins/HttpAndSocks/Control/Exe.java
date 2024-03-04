package plugins.HttpAndSocks.Control;

import plugins.HttpAndSocks.Config.Config;
import plugins.HttpAndSocks.error.MyCustomException;
import plugins.HttpAndSocks.util.Cmd;
import plugins.HttpAndSocks.util.MessageBox;
import util.Download;
import Config.GlobalConfig;
import util.FileIO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Exe {

    // 识别对应系统的HAS
    public String scanHAS(){
        // 获取系统信息和架构信息
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        String enscanName = "";
        if (os.contains("mac")) {
            if (arch.contains("x86_64")) {
                enscanName = "HAS-darwin-amd64";
                downloadHAS(enscanName);
            } else if (arch.contains("arm64")) {
                enscanName = "HAS-darwin-arm64";
                downloadHAS(enscanName);
            }
        } else if (os.contains("win")) {
            if (arch.contains("amd64")) {
                enscanName = "HAS-windows-amd64.exe";
                downloadHAS(enscanName);
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            if (arch.contains("amd64")) {
                enscanName = "HAS-linux-amd64";
                downloadHAS(enscanName);
            } else if (arch.contains("386")) {
                enscanName = "HAS-linux-386";
                downloadHAS(enscanName);
            }

        } else {
            MessageBox.showErrorAlert("error", "对不起，暂不支持您的系统架构！");
        }
        return enscanName;
    }

    // 根据对应系统，下载 HAS
    public void downloadHAS(String enscanName) {
        if (!new File(Config.execPath + enscanName).isFile()) {
            Map<String, String> downloadMap = new HashMap<>();
            downloadMap = Config.githubSrcMap;
            MessageBox.sendSystemInfo("插件暂未下载", "正在下载插件，请等待至下载完成...\n(っ˘зʕ•̫͡•ʔ");

            // 创建文件夹
            FileIO.createFolders(Config.execPath);

            String urlTmp = "";
            if (!GlobalConfig.configTypeNow.getSourceType().getProxyDownload().equals("关闭")) {
                urlTmp = GlobalConfig.configTypeNow.getSourceType().getProxyDownload();
            }

            if (!Download.DownloadFile(urlTmp + downloadMap.get(enscanName),
                    Config.execPath + enscanName)) {
                MessageBox.showErrorAlert("error", "文件下载失败，可能是超时导致，请确认网络链接是否有效，或者在配置中切换下载源地址");
                return;
            }
            if (!System.getProperty("os.name").contains("win")) {
                try {
                    Cmd.run("chmod +x " + Config.execPath + enscanName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        Cmd.run(Config.execPath + enscanName + " -h");
    }
}
