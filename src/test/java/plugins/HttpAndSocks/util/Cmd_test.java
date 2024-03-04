package plugins.HttpAndSocks.util;

import plugins.HttpAndSocks.Config.Config;

public class Cmd_test {
    public static void main(String[] args) throws InterruptedException {
//        Cmd.bgRun("gost -L :@localhost:7899 -F ss://aes-256-cfb:shidiaocnm@125.118.67.111:56969", "1");
        Thread.sleep(3000);
        Config.mapProcess.get("1").destroy();
    }
}
