package no.nav.fo.consumer.fillager;

import java.io.*;

public class Fillager {
    public long getLastModified(String path) {
        return (new File(path)).lastModified();
    }

    public InputStream getFileAsStream(String path) throws IOException {
        return new FileInputStream(path);
    }
}
