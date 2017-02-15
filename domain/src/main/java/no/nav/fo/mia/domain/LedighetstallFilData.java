package no.nav.fo.mia.domain;

public class LedighetstallFilData {
    private String path;
    private Long lastModified = 0L;

    public LedighetstallFilData(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }
}
