package Type;


import java.io.Serializable;

public class ConfigType implements Serializable {




    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }


    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }


    private SourceType sourceType = new SourceType();

    private ProxyType proxyType = new ProxyType();
}

