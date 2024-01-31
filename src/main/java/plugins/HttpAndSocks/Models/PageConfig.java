package plugins.HttpAndSocks.Models;

public class PageConfig {
    private String localhost;   // 本地IP
    private String localport;   // 本地端口
    private String localUserName; // 本地用户名
    private String localPassword; // 本地密码
    private String remoteScheme; // 协议
    private String remoteUri;   // 远程 uri，包括IP和端口
    private String remoteUserName;  // 远程用户名
    private String remotePassword;  // 远程密码

    public String getLocalhost() {
        return localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    public String getLocalport() {
        return localport;
    }

    public void setLocalport(String localport) {
        this.localport = localport;
    }

    public String getLocalUserName() {
        return localUserName;
    }

    public void setLocalUserName(String localUserName) {
        this.localUserName = localUserName;
    }

    public String getLocalPassword() {
        return localPassword;
    }

    public void setLocalPassword(String localPassword) {
        this.localPassword = localPassword;
    }

    public String getRemoteScheme() {
        return remoteScheme;
    }

    public void setRemoteScheme(String remoteScheme) {
        this.remoteScheme = remoteScheme;
    }

    public String getRemoteUri() {
        return remoteUri;
    }

    public void setRemoteUri(String remoteUri) {
        this.remoteUri = remoteUri;
    }

    public String getRemoteUserName() {
        return remoteUserName;
    }

    public void setRemoteUserName(String remoteUserName) {
        this.remoteUserName = remoteUserName;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }
}
